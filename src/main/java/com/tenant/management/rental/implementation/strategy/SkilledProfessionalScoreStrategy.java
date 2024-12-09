package com.tenant.management.rental.implementation.strategy;

import com.tenant.management.rental.entities.ScoreCalculationStrategy;

//Author : K S SRI KUMAR
//Id : 24177474
public class SkilledProfessionalScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(int creditScore) {
        if (creditScore > 800) {
            return 20.0; // Best discount for skilled professionals
        }
        return 10.0; // Default discount
    }
}
