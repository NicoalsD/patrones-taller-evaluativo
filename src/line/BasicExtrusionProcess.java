package line;

public class BasicExtrusionProcess implements ExtrusionProcess {
    private final int temperatureC;
    private final int pressureBar;
    private final int conditioningSeconds;
    private final String buoyancyType;

    public BasicExtrusionProcess(int temperatureC, int pressureBar, int conditioningSeconds, String buoyancyType) {
        this.temperatureC = temperatureC;
        this.pressureBar = pressureBar;
        this.conditioningSeconds = conditioningSeconds;
        this.buoyancyType = buoyancyType;
    }

    @Override
    public int getTemperatureC() {
        return temperatureC;
    }

    @Override
    public int getPressureBar() {
        return pressureBar;
    }

    @Override
    public int getConditioningSeconds() {
        return conditioningSeconds;
    }

    @Override
    public String getBuoyancyType() {
        return buoyancyType;
    }
}
