package formula;

public class FormulaIngredient {
    private Ingredient ingredient;
    private double percentage;

    public FormulaIngredient(Ingredient ingredient, double percentage) {
        this.ingredient = ingredient;
        this.percentage = percentage;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
