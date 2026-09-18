package line;

public class GrowOutTroutFactory extends LineFactory {
    @Override
    public NutritionalProfile createNutritionalProfile() {
        return new BasicNutritionalProfile("GROW_OUT_TROUT", 42.0, 18.0, 3.5, 0.85);
    }

    @Override
    public ExtrusionProcess createExtrusionProcess() {
        return new BasicExtrusionProcess(135, 32, 240, "semi-sinking");
    }

    @Override
    public Packaging createPackaging() {
        return new BasicPackaging("25 kg barrier bag", 25.0, 5);
    }
}
