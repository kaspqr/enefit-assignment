package com.example.electricity_consumption.model;

import jakarta.persistence.*;

@Entity
@Table(name = "metering_points")
public class MeteringPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String address;

    public MeteringPoint() {}

    public MeteringPoint(Customer customer, String address) {
        this.customer = customer;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
