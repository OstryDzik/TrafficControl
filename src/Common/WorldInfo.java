package Common;

/**
 * Created by Filip on 2014-11-01.
 */
public class WorldInfo
{
    private LightsInfo lightsMockup;
    private CarsInfo carsInfo;

    public LightsInfo getLightsMockup()
    {
        return lightsMockup;
    }

    public CarsInfo getCarsInfo()
    {
        return carsInfo;
    }

    public void setLightsMockup(LightsInfo lightsMockup)
    {
        this.lightsMockup = lightsMockup;
    }

    public void setCarsInfo(CarsInfo carsInfo)
    {
        this.carsInfo = carsInfo;
    }
}
