package line;

public class BasicNutritionalProfile implements NutritionalProfile {
    private final String lineName;
    private final double targetProtein;
    private final double targetLipids;
    private final double pelletDiameterMm;
    private final double capacityFactor;

    public BasicNutritionalProfile(
            String lineName,
            double targetProtein,
            double targetLipids,
            double pelletDiameterMm,
            double capacityFactor
    ) {
        this.lineName = lineName;
        this.targetProtein = targetProtein;
        this.targetLipids = targetLipids;
        this.pelletDiameterMm = pelletDiameterMm;
        this.capacityFactor = capacityFactor;
    }

    @Override
    public String getLineName() {
        return lineName;
    }

    @Override
    public double getTargetProtein() {
        return targetProtein;
    }

    @Override
    public double getTargetLipids() {
        return targetLipids;
    }

    @Override
    public double getPelletDiameterMm() {
        return pelletDiameterMm;
    }

    @Override
    public double getCapacityFactor() {
        return capacityFactor;
    }
}
