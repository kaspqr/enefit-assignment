package com.example.electricity_consumption.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumption")
public class Consumption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="metering_point_id", nullable = false)
  private MeteringPoint meteringPoint;

  @Column(nullable = false)
  private double amount;

  @Column(nullable = false)
  private String amountUnit;

  @Column(nullable = false)
  private LocalDateTime consumptionTime;

  public Consumption() {}

  public Consumption(MeteringPoint meteringPoint, double amount, String amountUnit, LocalDateTime consumptionTime) {
      this.meteringPoint = meteringPoint;
      this.amount = amount;
      this.amountUnit = amountUnit;
      this.consumptionTime = consumptionTime;
  }

  public Long getId() { return id; }

  public MeteringPoint getMeteringPoint() { return meteringPoint; }
  public void setMeteringPoint(MeteringPoint meteringPoint) { this.meteringPoint = meteringPoint; }

  public double getAmount() { return amount; }
  public void setAmount(double amount) { this.amount = amount; }

  public String getAmountUnit() { return amountUnit; }
  public void setAmountUnit(String amountUnit) { this.amountUnit = amountUnit; }

  public LocalDateTime getConsumptionTime() { return consumptionTime; }
  public void setConsumptionTime(LocalDateTime consumptionTime) { this.consumptionTime = consumptionTime; }
}
