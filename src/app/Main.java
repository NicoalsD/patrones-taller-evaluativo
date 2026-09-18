package app;

import formula.Formula;
import formula.FormulaIngredient;
import formula.Ingredient;
import formula.ValidationResult;
import line.LineFactory;
import line.GrowOutTroutFactory;
import order.ProductionOrder;
import service.PlantService;
import service.ProductionSchedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        PlantService service = new PlantService();
        LineFactory troutLine = new GrowOutTroutFactory();

        System.out.println("=== AQUACULTURE FEED PLANT ===");
        System.out.println("LINE: " + troutLine.createNutritionalProfile().getLineName()
                + " | target protein " + formatTwoDecimals(troutLine.createNutritionalProfile().getTargetProtein())
                + "% lipids " + formatTwoDecimals(troutLine.createNutritionalProfile().getTargetLipids())
                + "% | pellet " + formatTwoDecimals(troutLine.createNutritionalProfile().getPelletDiameterMm()) + " mm");
        System.out.println("Extrusion " + troutLine.createExtrusionProcess().getTemperatureC() + " C / "
                + troutLine.createExtrusionProcess().getPressureBar() + " bar / "
                + troutLine.createExtrusionProcess().getConditioningSeconds() + " s ("
                + troutLine.createExtrusionProcess().getBuoyancyType() + ") | "
                + troutLine.createPackaging().getDescription());
        System.out.println();

        Formula master = service.createMasterTroutFormula();
        ValidationResult masterValidation = service.validateFormula(master, troutLine.createNutritionalProfile());

        System.out.println("MASTER FORMULA " + master.getCode());
        for (FormulaIngredient item : master.getIngredients()) {
            System.out.println(item.getIngredient().getName() + " " + formatTwoDecimals(item.getPercentage()) + "%");
        }
        System.out.println("SUM " + formatTwoDecimals(service.sumPercentages(master)) + "% -> protein "
                + formatTwoDecimals(masterValidation.getProtein()) + "% lipids "
                + formatTwoDecimals(masterValidation.getLipids()) + "% -> " + masterValidation.getStatus()
                + " (dev " + formatSignedDeviation(masterValidation.getProteinDeviation()) + " / "
                + formatSignedDeviation(masterValidation.getLipidDeviation()) + ")");
        System.out.println("Cost: $" + formatThousands(masterValidation.getCostPerTon()) + " / ton");
        System.out.println();

        Formula approvedVariant = master.deepClone("V-01", "fish meal price increase");
        approvedVariant.updatePercentage("Fish meal", 30.00);
        approvedVariant.updatePercentage("Soy protein concentrate", 35.00);
        approvedVariant.updatePercentage("Corn meal", 10.00);
        approvedVariant.updatePercentage("Rice bran", 6.00);
        approvedVariant.updatePercentage("Fish oil", 14.00);
        approvedVariant.updatePercentage("Vitamin-mineral premix", 5.00);
        ValidationResult approvedValidation = service.validateFormula(approvedVariant, troutLine.createNutritionalProfile());

        Formula rejectedVariant = master.deepClone("V-02", "wheat gluten test");
        rejectedVariant.updatePercentage("Fish meal", 25.00);
        rejectedVariant.updatePercentage("Soy protein concentrate", 20.00);
        rejectedVariant.updatePercentage("Corn meal", 15.00);
        rejectedVariant.updatePercentage("Rice bran", 10.00);
        rejectedVariant.updatePercentage("Fish oil", 25.00);
        rejectedVariant.updatePercentage("Vitamin-mineral premix", 5.00);
        rejectedVariant.replaceIngredient("Fish meal", new Ingredient("Wheat gluten", 78.0, 1.5, 9100, false), 25.00);
        ValidationResult rejectedValidation = service.validateFormula(rejectedVariant, troutLine.createNutritionalProfile());

        System.out.println("VARIANT " + approvedVariant.getVariantCode() + " (reason: " + approvedVariant.getAdjustmentReason() + ") -> "
                + approvedValidation.getStatus() + " protein " + formatTwoDecimals(approvedValidation.getProtein())
                + " lipids " + formatTwoDecimals(approvedValidation.getLipids()));
        System.out.println("VARIANT " + rejectedVariant.getVariantCode() + " (reason: " + rejectedVariant.getAdjustmentReason() + ") -> "
                + rejectedValidation.getStatus() + " " + rejectedValidation.getMessage());
        System.out.println();

        System.out.println("Master verification TROUT-M01 -> Fish meal "
                + formatTwoDecimals(master.getIngredientPercentage("Fish meal")) + "% (unchanged)");
        System.out.println();

        ProductionOrder order = new ProductionOrder.Builder()
                .orderNumber("OP-2026-0311")
                .scheduledDate(LocalDate.of(2026, 3, 11))
                .line("GROW_OUT_TROUT")
                .formula(approvedVariant)
                .tons(60.0)
                .destinationSilo("S-04")
                .traceabilityBatch("LT-2026-0311")
                .specialAdditives(List.of("Astaxanthin pigment"))
                .assignedClient("Northern Aquaculture")
                .shift("Afternoon")
                .observations("Batch for premium market")
                .priority("High")
                .qualityLead("Eng. Karen Silva")
                .build();

        System.out.println("PRODUCTION ORDER " + order.getOrderNumber() + " | " + formatTwoDecimals(order.getTons()) + " t | variant "
                + order.getFormula().getVariantCode() + " | silo " + order.getDestinationSilo());
        System.out.println("RAW MATERIAL REQUIREMENT");
        List<FormulaIngredient> requirements = service.calculateRawMaterialRequirement(order.getFormula(), order.getTons());
        for (FormulaIngredient item : requirements) {
            System.out.println(item.getIngredient().getName() + " " + formatTwoDecimals(item.getPercentage()) + " kg");
        }
        System.out.println();

        double totalOrderCost = service.calculateTotalOrderCost(order.getFormula(), order.getTons());
        System.out.println("Total order cost: $" + formatThousands(totalOrderCost));

        ProductionSchedule schedule = service.calculateSchedule(
                order.getTons(),
                troutLine.createPackaging(),
                troutLine.createNutritionalProfile(),
                troutLine.createExtrusionProcess()
        );
        System.out.println("Actual throughput: " + formatTwoDecimals(schedule.getActualThroughput()) + " t/h -> "
                + formatTwoDecimals(schedule.getExtrusionHours()) + " h of extrusion -> "
                + schedule.getRequiredShifts() + " shifts");
        System.out.println("Bags to produce: " + schedule.getBagsToProduce()
                + " bags of " + formatTwoDecimals(troutLine.createPackaging().getBagWeightKg()) + " kg");

        try {
            new ProductionOrder.Builder()
                    .orderNumber("OP-2026-0312")
                    .scheduledDate(LocalDate.of(2026, 3, 12))
                    .line("GROW_OUT_TROUT")
                    .formula(approvedVariant)
                    .tons(250.0)
                    .destinationSilo("S-05")
                    .traceabilityBatch("LT-2026-0312")
                    .qualityLead("Eng. Ana Torres")
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("[CONTROLLED ERROR] " + e.getMessage());
        }

        try {
            new ProductionOrder.Builder()
                    .orderNumber("OP-2026-0313")
                    .scheduledDate(LocalDate.of(2026, 3, 13))
                    .line("GROW_OUT_TROUT")
                    .formula(approvedVariant)
                    .tons(60.0)
                    .destinationSilo("S-06")
                    .traceabilityBatch("LT-2026-0313")
                    .specialAdditives(List.of("Probiotic"))
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("[CONTROLLED ERROR] " + e.getMessage());
        }

        System.out.println();
        System.out.println("VARIANT COMPARISON");
        System.out.println("VARIANT PROTEIN LIPIDS COST/TON DIFFERENCE");
        List<Formula> variants = new ArrayList<>();
        variants.add(master);
        variants.add(approvedVariant);
        variants.add(rejectedVariant);
        service.printVariantComparison(master, variants);
    }

    private static String formatTwoDecimals(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    private static String formatThousands(double value) {
        return String.format(Locale.US, "%,.0f", value);
    }

    private static String formatSignedDeviation(double value) {
        if (value > 0) {
            return "+" + formatTwoDecimals(value);
        }
        if (value < 0) {
            return "-" + formatTwoDecimals(Math.abs(value));
        }
        return "0.00";
    }
}
