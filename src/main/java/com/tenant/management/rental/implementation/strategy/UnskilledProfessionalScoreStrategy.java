package com.tenant.management.rental.implementation.strategy;

import com.tenant.management.rental.entities.ScoreCalculationStrategy;

//Author : K S SRI KUMAR
//Id : 24177474
public class UnskilledProfessionalScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(int creditScore) {
        if (creditScore > 700) {
            return 8.0;
        }
        return 3.0;
    }
}
