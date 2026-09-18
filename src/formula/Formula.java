package formula;

import java.util.ArrayList;
import java.util.List;

public class Formula {
    private final String code;
    private final String name;
    private final List<FormulaIngredient> ingredients;
    private String variantCode;
    private String adjustmentReason;

    public Formula(String code, String name) {
        this.code = code;
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public Formula deepClone(String variantCode, String adjustmentReason) {
        Formula clone = new Formula(this.code, this.name);
        clone.variantCode = variantCode;
        clone.adjustmentReason = adjustmentReason;

        for (FormulaIngredient item : ingredients) {
            Ingredient source = item.getIngredient();
            Ingredient copied = new Ingredient(
                    source.getName(),
                    source.getProtein(),
                    source.getLipids(),
                    source.getCostPerKg(),
                    source.isAvailable()
            );
            clone.addIngredient(new FormulaIngredient(copied, item.getPercentage()));
        }

        return clone;
    }

    public void addIngredient(FormulaIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public void updatePercentage(String ingredientName, double newPercentage) {
        for (FormulaIngredient item : ingredients) {
            if (item.getIngredient().getName().equals(ingredientName)) {
                item.setPercentage(newPercentage);
                return;
            }
        }
        throw new IllegalArgumentException("Ingredient not found: " + ingredientName);
    }

    public void replaceIngredient(String originalName, Ingredient replacement, double newPercentage) {
        for (FormulaIngredient item : ingredients) {
            if (item.getIngredient().getName().equals(originalName)) {
                item.setIngredient(replacement);
                item.setPercentage(newPercentage);
                return;
            }
        }
        throw new IllegalArgumentException("Ingredient not found for replacement: " + originalName);
    }

    public double getIngredientPercentage(String ingredientName) {
        for (FormulaIngredient item : ingredients) {
            if (item.getIngredient().getName().equals(ingredientName)) {
                return item.getPercentage();
            }
        }
        return 0.0;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<FormulaIngredient> getIngredients() {
        return ingredients;
    }

    public String getVariantCode() {
        return variantCode;
    }

    public String getAdjustmentReason() {
        return adjustmentReason;
    }
}
