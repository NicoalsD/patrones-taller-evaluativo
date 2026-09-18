package line;

public class BasicPackaging implements Packaging {
    private final String description;
    private final double bagWeightKg;
    private final int shelfLifeMonths;

    public BasicPackaging(String description, double bagWeightKg, int shelfLifeMonths) {
        this.description = description;
        this.bagWeightKg = bagWeightKg;
        this.shelfLifeMonths = shelfLifeMonths;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getBagWeightKg() {
        return bagWeightKg;
    }

    @Override
    public int getShelfLifeMonths() {
        return shelfLifeMonths;
    }
}
