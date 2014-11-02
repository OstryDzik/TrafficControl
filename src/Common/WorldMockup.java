package Common;

/**
 * Created by Filip on 2014-11-01.
 */
public class WorldMockup
{
    private LightsMockup lightsMockup;
    private CarsMockup carsMockup;

    public LightsMockup getLightsMockup()
    {
        return lightsMockup;
    }

    public CarsMockup getCarsMockup()
    {
        return carsMockup;
    }

    public void setLightsMockup(LightsMockup lightsMockup)
    {
        this.lightsMockup = lightsMockup;
    }

    public void setCarsMockup(CarsMockup carsMockup)
    {
        this.carsMockup = carsMockup;
    }
}
