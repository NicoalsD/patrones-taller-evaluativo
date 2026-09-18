package service;

public class ProductionSchedule {
    private final double actualThroughput;
    private final double extrusionHours;
    private final int requiredShifts;
    private final int bagsToProduce;

    public ProductionSchedule(double actualThroughput, double extrusionHours, int requiredShifts, int bagsToProduce) {
        this.actualThroughput = actualThroughput;
        this.extrusionHours = extrusionHours;
        this.requiredShifts = requiredShifts;
        this.bagsToProduce = bagsToProduce;
    }

    public double getActualThroughput() {
        return actualThroughput;
    }

    public double getExtrusionHours() {
        return extrusionHours;
    }

    public int getRequiredShifts() {
        return requiredShifts;
    }

    public int getBagsToProduce() {
        return bagsToProduce;
    }
}
