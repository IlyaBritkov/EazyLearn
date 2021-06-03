package eazy.learn.enums;

public enum ProficientLevel {
    LOW(15),
    AVERAGE(50),
    HIGH(80);

    private int levelPoints;

    ProficientLevel(int level) {
    }

    public int getLevelPoints() {
        return levelPoints;
    }

}