package com.tenant.management.subscription.entities;

import jakarta.persistence.*;

@Entity
@Inheritance
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the primary key
    private Long id; // Primary key

    private String name;
    private double price;

    // Constructor with parameters
    public SubscriptionPlan(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
