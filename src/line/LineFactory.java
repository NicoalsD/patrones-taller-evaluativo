package line;

public abstract class LineFactory {
    public abstract NutritionalProfile createNutritionalProfile();
    public abstract ExtrusionProcess createExtrusionProcess();
    public abstract Packaging createPackaging();
}
