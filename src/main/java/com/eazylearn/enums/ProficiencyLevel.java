package com.eazylearn.enums;

public enum ProficiencyLevel { // todo: make table in the database

    LOW(0.15),
    AVERAGE(0.50),
    HIGH(0.85);

    private final double levelPoints;

    ProficiencyLevel(double levelPoints) {
        this.levelPoints = levelPoints;
    }

    public double getLevelPoints() {
        return levelPoints;
    }

    public static ProficiencyLevel resolveLevelByPoint(double point) { // TODO maybe replace that logic by math rounding
        if (point < AVERAGE.getLevelPoints()) {
            return LOW;
        } else if (point < HIGH.getLevelPoints()) {
            return AVERAGE;
        } else if (point >= HIGH.getLevelPoints()) {
            return HIGH;
        } else {
            // actually unreachable case
            return AVERAGE;
        }
    }

}