package com.example.electricity_consumption.repository;

import com.example.electricity_consumption.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    List<Consumption> findByMeteringPointIdAndConsumptionTimeBetween(Long meteringPointId,
                                                  java.time.LocalDateTime startOfYear,
                                                  java.time.LocalDateTime endOfYear);
}
