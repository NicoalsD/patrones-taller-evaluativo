package line;

public class GrowOutTilapiaFactory extends LineFactory {
    @Override
    public NutritionalProfile createNutritionalProfile() {
        return new BasicNutritionalProfile("GROW_OUT_TILAPIA", 28.0, 6.0, 4.5, 1.00);
    }

    @Override
    public ExtrusionProcess createExtrusionProcess() {
        return new BasicExtrusionProcess(125, 25, 180, "floating");
    }

    @Override
    public Packaging createPackaging() {
        return new BasicPackaging("40 kg polypropylene bag", 40.0, 6);
    }
}
