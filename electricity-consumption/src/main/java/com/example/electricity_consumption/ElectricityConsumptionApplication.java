package com.example.electricity_consumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.electricity_consumption.model")
public class ElectricityConsumptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricityConsumptionApplication.class, args);
	}

}
