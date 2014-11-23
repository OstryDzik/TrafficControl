package Common;

import java.util.Random;

/**
 * Created by Filip on 2014-11-02.
 */
public class TempMockupManager
{
    private WorldInfo[] worldInfoList;
    private int mockupIndex;
    private static TempMockupManager instance;

    private TempMockupManager()
    {
        mockupIndex = 0;
        worldInfoList = new WorldInfo[3];
        Random rand = new Random();
        for (int i = 0; i < 3; i++)
        {
            worldInfoList[i] = getRandomMockup();
        }
    }

    public static TempMockupManager getInstance()
    {
        if (instance == null)
        {
            instance = new TempMockupManager();
            return instance;
        }
        else
            return instance;
    }

    public WorldInfo getNextMockup()
    {

        mockupIndex = (mockupIndex + 1)%3;
        return worldInfoList[mockupIndex] != null ? worldInfoList[mockupIndex] : getRandomMockup();
    }

    public WorldInfo getRandomMockup()
    {
        Random rand = new Random();
        LightsInfo tmpLights = new LightsInfo();
        for (int j = 0; j < 9; j++)
        {
            tmpLights.setState(j, LightsInfo.LightsState.values()[rand.nextInt(4)]);
        }
        WorldInfo tmpMockup = new WorldInfo();
        tmpMockup.setLightsMockup(tmpLights);
        return tmpMockup;
    }
}
