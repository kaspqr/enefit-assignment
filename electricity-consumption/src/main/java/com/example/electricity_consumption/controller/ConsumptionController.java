package com.example.electricity_consumption.controller;

import com.example.electricity_consumption.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import com.example.electricity_consumption.model.Customer;
import com.example.electricity_consumption.model.MeteringPoint;
import com.example.electricity_consumption.model.Consumption;
import com.example.electricity_consumption.repository.CustomerRepository;
import com.example.electricity_consumption.repository.MeteringPointRepository;
import com.example.electricity_consumption.repository.ConsumptionRepository;
import com.example.electricity_consumption.security.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consumption")
@CrossOrigin(origins = {"http://localhost:5173", "localhost:5173"}, allowCredentials = "true")
public class ConsumptionController {

  private final CustomerRepository customerRepository;
  private final MeteringPointRepository meteringPointRepository;
  private final ConsumptionRepository consumptionRepository;
  private final MarketDataService marketDataService;

  @Autowired
  private JwtUtil jwtUtil;

  private static final Logger logger = LoggerFactory.getLogger(ConsumptionController.class);

  public ConsumptionController(CustomerRepository customerRepository,
                               MeteringPointRepository meteringPointRepository,
                               ConsumptionRepository consumptionRepository,
                               MarketDataService marketDataService) {
    this.customerRepository = customerRepository;
    this.meteringPointRepository = meteringPointRepository;
    this.consumptionRepository = consumptionRepository;
    this.marketDataService = marketDataService;
  }

  @GetMapping
  public ResponseEntity<?> getConsumption(
          @RequestParam int year,
          HttpServletRequest request) {

    String token = getJwtFromRequest(request);

    if (token == null || !validateToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    String username = jwtUtil.extractUsernameFromToken(token);

    if (username == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    Optional<Customer> customerOptional = customerRepository.findByUsername(username);

    if (customerOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    Customer customer = customerOptional.get();

    List<MeteringPoint> meteringPoints = meteringPointRepository.findByCustomerId(customer.getId());

    Map<String, Object> response = Map.of(
            "year", year,
            "metering_points", getMeteringPointsWithConsumption(meteringPoints, year)
    );

    return ResponseEntity.ok(response);
  }

  private static final Logger log = LoggerFactory.getLogger(MarketDataService.class);

  private List<Map<String, Object>> getMeteringPointsWithConsumption(List<MeteringPoint> meteringPoints, int year) {
    return meteringPoints.stream().map(meteringPoint -> {
      LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0, 0, 0);
      LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59, 999999);

      List<Consumption> consumptions = consumptionRepository.findByMeteringPointIdAndConsumptionTimeBetween(
              meteringPoint.getId(), startOfYear, endOfYear);

      double[] monthlyConsumption = new double[12];
      double[] monthlyCost = new double[12];

      for (Consumption consumption : consumptions) {
        int month = consumption.getConsumptionTime().getMonthValue() - 1; // 0-indexed for array
        monthlyConsumption[month] += consumption.getAmount();

        // Need to give elering API a range, so getting 1 second before and after
        // the beginning of the full hour
        long timestamp = consumption.getConsumptionTime()
                .atZone(ZoneId.of("Europe/Tallinn"))
                .toEpochSecond();

        long start = timestamp - (timestamp % 3600) - 1;
        long end = timestamp - (timestamp % 3600) + 1;

        // Fetch the price and calculate cost
        Double finalPrice = marketDataService.fetchElectricityPrice(start, end)
                .map(priceMap -> {
                  Double price = extractPriceFromResponse(priceMap, start + 1);
                  log.info("Extracted Price for timestamp {}: {}", start, price);
                  return price;
                })
                .doOnNext(price -> {
                  if (price != null) {
                    monthlyCost[month] += consumption.getAmount() * price;
                    log.info("Updated monthly cost for month {}: {}", month, monthlyCost[month]);
                  } else {
                    log.warn("Price is null, skipping cost calculation.");
                  }
                })
                .block();

        if (finalPrice != null) {
          monthlyCost[month] += consumption.getAmount() * finalPrice;
        }
      }

      double totalConsumption = Arrays.stream(monthlyConsumption).sum();
      double totalCost = Arrays.stream(monthlyCost).sum();

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("id", meteringPoint.getId());
      responseMap.put("address", meteringPoint.getAddress());
      responseMap.put("consumptions", monthlyConsumption);
      responseMap.put("prices", monthlyCost);
      responseMap.put("totalConsumption", totalConsumption);
      responseMap.put("totalCost", totalCost);

      return responseMap;
    }).collect(Collectors.toList());
  }

  private Double extractPriceFromResponse(Map<Long, Double> priceMap, long requestedTimestamp) {
    return priceMap.getOrDefault(requestedTimestamp, null);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  boolean validateToken(String token) {
    try {
      jwtUtil.validateAccessToken(token);
      return true;
    } catch (Exception e) {
      logger.error("Token validation failed", e);
      return false;
    }
  }
}
