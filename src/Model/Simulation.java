package Model;

import java.io.Serializable;

/**
 * Created by Filip on 2014-11-23.
 */
public class Simulation implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3102194398126500125L;
	public Simulation()
    {
        simulationState = SimulationState.MANUAL;
        timePassed = 0;
        interval = new Interval();
    }

    public void setState(SimulationState newState)
    {
        if (newState != simulationState)
        {
            simulationState = newState;
            timePassed = 0;
        }
    }

    public void advanceTime()
    {
        if (simulationState == SimulationState.MANUAL)
        {
            timePassed++;
        }
    }

    public void setInterval(Interval newInterval)
    {
        interval = newInterval;
    }

    public Interval getInterval()
    {
        return interval;
    }

    public SimulationState getSimulationState()
    {
        return simulationState;
    }

    public int getTimePassed()
    {
        return timePassed;
    }

    public boolean isSimulationOn()
    {
        return isSimulationOn;
    }

    public void setSimulationOn(boolean isSimulationOn)
    {
        this.isSimulationOn = isSimulationOn;
    }

    public boolean isLightsModuleOn()
    {
        return isLightsModuleOn;
    }

    public void setLightsModuleOn(boolean isLightsModuleOn)
    {
        this.isLightsModuleOn = isLightsModuleOn;
    }

    public boolean isGuiOn()
    {
        return isGuiOn;
    }

    public void setGuiOn(boolean isGuiOn)
    {
        this.isGuiOn = isGuiOn;
    }

    public enum SimulationState{MANUAL, AUTO};

    private SimulationState simulationState = SimulationState.MANUAL;
    private int timePassed;
    private Interval interval;

    private boolean isSimulationOn;
    private boolean isLightsModuleOn;
    private boolean isGuiOn;

}
