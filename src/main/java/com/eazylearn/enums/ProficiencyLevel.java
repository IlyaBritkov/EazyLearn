package com.eazylearn.enums;

public enum ProficiencyLevel {
    LOW(15),
    AVERAGE(50),
    HIGH(85);

    private final int levelPoints;

    ProficiencyLevel(int levelPoints) {
        this.levelPoints = levelPoints;
    }

    public int getLevelPoints() {
        return levelPoints;
    }

    public ProficiencyLevel resolveLevelByPoint(double point) { // TODO maybe replace that logic by math rounding
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