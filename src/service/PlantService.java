package service;

import formula.Formula;
import formula.FormulaIngredient;
import formula.Ingredient;
import formula.ValidationResult;
import line.ExtrusionProcess;
import line.NutritionalProfile;
import line.Packaging;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class PlantService {

    public Formula createMasterTroutFormula() {
        Formula formula = new Formula("TROUT-M01", "Master trout formula");
        formula.addIngredient(new FormulaIngredient(new Ingredient("Fish meal", 62.0, 9.0, 7800, true), 40.00));
        formula.addIngredient(new FormulaIngredient(new Ingredient("Soy protein concentrate", 62.5, 2.0, 5400, true), 25.00));
        formula.addIngredient(new FormulaIngredient(new Ingredient("Corn meal", 8.5, 3.8, 1650, true), 10.00));
        formula.addIngredient(new FormulaIngredient(new Ingredient("Rice bran", 13.0, 14.0, 1200, true), 8.00));
        formula.addIngredient(new FormulaIngredient(new Ingredient("Fish oil", 0.0, 99.0, 11200, true), 12.00));
        formula.addIngredient(new FormulaIngredient(new Ingredient("Vitamin-mineral premix", 0.0, 0.0, 18500, true), 5.00));
        return formula;
    }

    public double sumPercentages(Formula formula) {
        double total = 0.0;
        for (FormulaIngredient item : formula.getIngredients()) {
            total += item.getPercentage();
        }
        return total;
    }

    public ValidationResult validateFormula(Formula formula, NutritionalProfile profile) {
        double sum = sumPercentages(formula);
        if (Math.abs(sum - 100.0) > 0.01) {
            return new ValidationResult(
                    false,
                    "REJECTED",
                    "FORMULA_REJECTED: inclusion sum does not meet 100% ± 0.01 | total=" + String.format(Locale.US, "%.2f", sum),
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0
            );
        }

        double proteinTotal = 0.0;
        double lipidTotal = 0.0;

        for (FormulaIngredient item : formula.getIngredients()) {
            Ingredient ingredient = item.getIngredient();
            if (!ingredient.isAvailable()) {
                return new ValidationResult(
                        false,
                        "REJECTED",
                        "UNAVAILABLE_INPUT: " + ingredient.getName(),
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                );
            }

            double fraction = item.getPercentage() / 100.0;
            proteinTotal += fraction * ingredient.getProtein();
            lipidTotal += fraction * ingredient.getLipids();
        }

        double roundedProtein = roundTwoDecimals(proteinTotal);
        double roundedLipids = roundTwoDecimals(lipidTotal);

        double proteinDeviation = roundedProtein - profile.getTargetProtein();
        double lipidDeviation = roundedLipids - profile.getTargetLipids();

        boolean approved = Math.abs(proteinDeviation) <= 1.0 && Math.abs(lipidDeviation) <= 1.0;
        String status = approved ? "APPROVED" : "OUT_OF_SPEC";
        String message = approved
                ? ""
                : "Deviation protein " + String.format(Locale.US, "%.2f", proteinDeviation)
                + " / lipids " + String.format(Locale.US, "%.2f", lipidDeviation);

        return new ValidationResult(
                approved,
                status,
                message,
                roundedProtein,
                roundedLipids,
                roundTwoDecimals(proteinDeviation),
                roundTwoDecimals(lipidDeviation),
                calculateCostPerTon(formula)
        );
    }

    public double calculateCostPerTon(Formula formula) {
        double total = 0.0;
        for (FormulaIngredient item : formula.getIngredients()) {
            total += (item.getPercentage() / 100.0) * item.getIngredient().getCostPerKg() * 1000.0;
        }
        return total;
    }

    public double calculateTotalOrderCost(Formula formula, double tons) {
        return calculateCostPerTon(formula) * tons;
    }

    public List<FormulaIngredient> calculateRawMaterialRequirement(Formula formula, double tons) {
        List<FormulaIngredient> requirements = new ArrayList<>();
        for (FormulaIngredient item : formula.getIngredients()) {
            double kg = (item.getPercentage() / 100.0) * tons * 1000.0;
            requirements.add(new FormulaIngredient(item.getIngredient(), kg));
        }

        requirements.sort(Comparator.comparingDouble(FormulaIngredient::getPercentage).reversed());
        return requirements;
    }

    public ProductionSchedule calculateSchedule(
            double tons,
            Packaging packaging,
            NutritionalProfile profile,
            ExtrusionProcess process
    ) {
        double actualThroughput = 4.0 * profile.getCapacityFactor();
        double extrusionHours = tons / actualThroughput;

        double shiftHoursWithoutStartup = 8.0 - (45.0 / 60.0);
        int requiredShifts = (int) Math.ceil(extrusionHours / shiftHoursWithoutStartup);
        int bagsToProduce = (int) Math.ceil((tons * 1000.0) / packaging.getBagWeightKg());

        return new ProductionSchedule(actualThroughput, extrusionHours, requiredShifts, bagsToProduce);
    }

    public void printVariantComparison(Formula master, List<Formula> variants) {
        double masterCost = calculateCostPerTon(master);

        System.out.println("MASTER " + String.format(Locale.US, "%.2f", calculateProtein(master)) + " "
                + String.format(Locale.US, "%.2f", calculateLipids(master)) + " $"
                + String.format(Locale.US, "%,.0f", masterCost) + " --");

        for (Formula variant : variants) {
            if (variant == master) {
                continue;
            }

            double cost = calculateCostPerTon(variant);
            double delta = ((cost - masterCost) / masterCost) * 100.0;
            String direction = delta <= 0 ? "savings" : "extra cost";

            System.out.println(variant.getVariantCode() + " "
                    + String.format(Locale.US, "%.2f", calculateProtein(variant)) + " "
                    + String.format(Locale.US, "%.2f", calculateLipids(variant)) + " $"
                    + String.format(Locale.US, "%,.0f", cost) + " "
                    + String.format(Locale.US, "%.2f", Math.abs(delta)) + "% (" + direction + ")");
        }
    }

    private double calculateProtein(Formula formula) {
        double total = 0.0;
        for (FormulaIngredient item : formula.getIngredients()) {
            total += (item.getPercentage() / 100.0) * item.getIngredient().getProtein();
        }
        return roundTwoDecimals(total);
    }

    private double calculateLipids(Formula formula) {
        double total = 0.0;
        for (FormulaIngredient item : formula.getIngredients()) {
            total += (item.getPercentage() / 100.0) * item.getIngredient().getLipids();
        }
        return roundTwoDecimals(total);
    }

    private double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
