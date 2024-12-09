package com.tenant.management.rental.implementation.strategy;

import com.tenant.management.rental.entities.ScoreCalculationStrategy;

//Author : K S SRI KUMAR
//Id : 24177474
public class StudentScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(int creditScore) {
        if (creditScore > 750) {
            return 10.0; // 10% discount for students with good credit
        }
        return 5.0; // Default discount
    }
}