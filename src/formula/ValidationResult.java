package formula;

public class ValidationResult {
    private final boolean valid;
    private final String status;
    private final String message;
    private final double protein;
    private final double lipids;
    private final double proteinDeviation;
    private final double lipidDeviation;
    private final double costPerTon;

    public ValidationResult(
            boolean valid,
            String status,
            String message,
            double protein,
            double lipids,
            double proteinDeviation,
            double lipidDeviation,
            double costPerTon
    ) {
        this.valid = valid;
        this.status = status;
        this.message = message;
        this.protein = protein;
        this.lipids = lipids;
        this.proteinDeviation = proteinDeviation;
        this.lipidDeviation = lipidDeviation;
        this.costPerTon = costPerTon;
    }

    public boolean isValid() {
        return valid;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public double getProtein() {
        return protein;
    }

    public double getLipids() {
        return lipids;
    }

    public double getProteinDeviation() {
        return proteinDeviation;
    }

    public double getLipidDeviation() {
        return lipidDeviation;
    }

    public double getCostPerTon() {
        return costPerTon;
    }
}
