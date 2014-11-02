package Common;

import java.awt.peer.LightweightPeer;
import java.util.Random;

/**
 * Created by Filip on 2014-11-02.
 */
public class TempMockupManager
{
    private WorldMockup[] worldMockupList;
    private int mockupIndex;
    private static TempMockupManager instance;

    private TempMockupManager()
    {
        mockupIndex = 0;
        worldMockupList = new WorldMockup[3];
        Random rand = new Random();
        for (int i = 0; i < 3; i++)
        {
            worldMockupList[i] = getRandomMockup();
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

    public WorldMockup getNextMockup()
    {

        mockupIndex = (mockupIndex + 1)%3;
        return worldMockupList[mockupIndex] != null ? worldMockupList[mockupIndex] : getRandomMockup();
    }

    public WorldMockup getRandomMockup()
    {
        Random rand = new Random();
        LightsMockup tmpLights = new LightsMockup();
        for (int j = 0; j < 9; j++)
        {
            tmpLights.setState(j, LightsMockup.LightsState.values()[rand.nextInt(4)]);
        }
        WorldMockup tmpMockup = new WorldMockup();
        tmpMockup.setLightsMockup(tmpLights);
        return tmpMockup;
    }
}
