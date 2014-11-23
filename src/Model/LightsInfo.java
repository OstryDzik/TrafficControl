package Model;

/**
 * Created by Filip on 2014-11-01.
 */
public class LightsInfo{
    public enum LightsState {HORIZONTALLY_GREEN, HORIZONTALLY_RED, HORIZONTALLY_ORANGE, VERTICALLY_ORANGE}

    public LightsInfo()
    {
        lights = new LightsState[9];
    }

    public void setState(int junctionNumber, LightsState state) {
        lights[junctionNumber] = state;
    }

    public LightsState[] getStates() {
        return lights;
    }

    private LightsState lights[];
}
