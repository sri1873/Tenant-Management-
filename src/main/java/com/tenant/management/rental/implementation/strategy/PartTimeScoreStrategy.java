package com.tenant.management.rental.implementation.strategy;

import com.tenant.management.rental.entities.ScoreCalculationStrategy;

//Author : K S SRI KUMAR
//Id : 24177474
public class PartTimeScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(int creditScore) {
        if (creditScore > 650) {
            return 5.0;
        }
        return 2.0; // Minimal discount

    }
}