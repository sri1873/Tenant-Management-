package com.tenant.management.rental.implementation.strategy;

import com.tenant.management.rental.entities.ScoreCalculationStrategy;

//Author : K S SRI KUMAR
//Id : 24177474
public class ScoreCalculatorContext {
    private ScoreCalculationStrategy strategy;

    public void setStrategy(ScoreCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateScore(int creditScore) {
        if (strategy == null) {
            throw new IllegalStateException("No strategy set for calculation");
        }
        return strategy.calculateScore(creditScore);
    }
}

