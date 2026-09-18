package formula;

public class Ingredient {
    private final String name;
    private final double protein;
    private final double lipids;
    private final double costPerKg;
    private final boolean available;

    public Ingredient(String name, double protein, double lipids, double costPerKg, boolean available) {
        this.name = name;
        this.protein = protein;
        this.lipids = lipids;
        this.costPerKg = costPerKg;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public double getProtein() {
        return protein;
    }

    public double getLipids() {
        return lipids;
    }

    public double getCostPerKg() {
        return costPerKg;
    }

    public boolean isAvailable() {
        return available;
    }
}
