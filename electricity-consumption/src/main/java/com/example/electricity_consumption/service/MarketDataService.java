package com.example.electricity_consumption.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class MarketDataService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public MarketDataService(
            WebClient.Builder webClientBuilder,
            ObjectMapper objectMapper,
            @Value("${elering.base-url}") String baseUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.objectMapper = objectMapper;
    }

    private static final Logger log = LoggerFactory.getLogger(MarketDataService.class);

    public Mono<Map<Long, Double>> fetchElectricityPrice(long start, long end) {
        // Convert the start and end times to match elering API format
        Instant startInstant = Instant.ofEpochSecond(start);
        Instant endInstant = Instant.ofEpochSecond(end);

        String startFormatted = DateTimeFormatter.ISO_INSTANT.format(startInstant);
        String endFormatted = DateTimeFormatter.ISO_INSTANT.format(endInstant);

        String uri = UriComponentsBuilder.fromPath("/api/nps/price")
                .queryParam("start", startFormatted)
                .queryParam("end", endFormatted)
                .toUriString();

        log.info("Fetching electricity price with URI: {}", uri);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Received response: {}", response))  // Log the raw response
                .map(response -> parsePriceData(response));
    }

    private Map<Long, Double> parsePriceData(String jsonResponse) {
        log.info("Parsing price data: {}", jsonResponse);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            log.error("Received empty response");
            return Collections.emptyMap();
        }

        Map<Long, Double> priceMap = new HashMap<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode dataNode = rootNode.path("data");
            if (dataNode.isMissingNode()) {
                log.error("'data' field is missing in the response.");
                return Collections.emptyMap();
            }

            // Estonian prices only for the assignment
            JsonNode eePrices = dataNode.path("ee");
            if (eePrices.isMissingNode()) {
                log.error("'ee' field is missing in the 'data' section.");
                return Collections.emptyMap();
            }

            for (JsonNode entry : eePrices) {
                if (entry.has("timestamp") && entry.has("price")) {
                    long timestamp = entry.get("timestamp").asLong();
                    double price = entry.get("price").asDouble();
                    priceMap.put(timestamp, price);
                } else {
                    log.warn("Missing 'timestamp' or 'price' in entry: {}", entry);
                }
            }
        } catch (Exception e) {
            log.error("Error parsing price data", e);
            return Collections.emptyMap();
        }

        return priceMap;
    }
}
