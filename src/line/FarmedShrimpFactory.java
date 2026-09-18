package line;

public class FarmedShrimpFactory extends LineFactory {
    @Override
    public NutritionalProfile createNutritionalProfile() {
        return new BasicNutritionalProfile("FARMED_SHRIMP", 35.0, 8.0, 2.0, 0.70);
    }

    @Override
    public ExtrusionProcess createExtrusionProcess() {
        return new BasicExtrusionProcess(110, 18, 300, "sinking and stable for 2 hours");
    }

    @Override
    public Packaging createPackaging() {
        return new BasicPackaging("25 kg aluminized multilayer bag", 25.0, 4);
    }
}
