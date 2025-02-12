package com.example.electricity_consumption.repository;

import com.example.electricity_consumption.model.MeteringPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeteringPointRepository extends JpaRepository<MeteringPoint, Long> {
    List<MeteringPoint> findByCustomerId(Long id);
}
