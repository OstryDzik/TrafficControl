package Common;

/**
 * Created by Filip on 2014-11-01.
 */
public class LightsMockup
{
    public enum LightsState {HORIZONTALLY_GREEN,HORIZONTALLY_RED,HORIZONTALLY_ORANGE, VERTICALLY_ORAGNE}

    LightsState lightState[];

    public LightsMockup()
    {
        lightState = new LightsState[9];
    }

    public void setState(int which,LightsState what) {
        lightState[which]=what;
    }

    public LightsState[] getStates() {
        return lightState;
    }

}
