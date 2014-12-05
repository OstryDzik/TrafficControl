package Simulation;

import java.awt.Point;
import java.util.ArrayList;

import Model.Car;
import Model.Direction;
import Model.LightsInfo;
import Model.LightsInfo.LightsState;

public class Crossing
{
    private ArrayList<Integer> xArray;
    private ArrayList<Integer> yArray;
    private LightsState lightsState;
    private int verticalTraffic;
    private int horizontalTraffic;
    
    public Crossing(int x1, int x2, int y1, int y2)
    {
        xArray = new ArrayList<Integer>();
        yArray = new ArrayList<Integer>();
        xArray.add(x1);
        xArray.add(x2);
        yArray.add(y1);
        yArray.add(y2);
        lightsState = LightsState.HORIZONTALLY_GREEN;
        verticalTraffic = 0;
        horizontalTraffic = 0;
    }
    
    boolean thisCrossing(Point p)
    {
        if(xArray.contains(p.x) && yArray.contains(p.y))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    boolean isRedOrOrange(Direction direction)
    {
        if(direction == Direction.UP || direction == Direction.DOWN)
        {
            if(lightsState == LightsState.HORIZONTALLY_RED)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            if(lightsState == LightsState.HORIZONTALLY_GREEN)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
    
    public int getCarCount(final  Car table[][])
    {
        int count = 0;
        for(int x : xArray)
        {
            for(int y : yArray)
            {
                if(table[x][y]!= null)
                    count++;
            }
        }
        return count;
    }
    
    public void tickTraffic(Direction direction)
    {
        if(direction == Direction.UP || direction == Direction.DOWN)
        {
            verticalTraffic++;
        }
        else
        {
            horizontalTraffic++;
        }
    }
    
    void setLightsState(LightsState lightsState)
    {
        this.lightsState = lightsState;
    }
}
