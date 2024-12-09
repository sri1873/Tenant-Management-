package com.tenant.management.rental.implementation.strategy;

import com.tenant.management.rental.entities.ScoreCalculationStrategy;

//Author : K S SRI KUMAR
//Id : 24177474
public class RetiredScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(int creditScore) {
        if (creditScore > 700) {
            return 15.0; // Higher discount for retirees with good credit
        }
        return 7.0; // Default discount
    }
}