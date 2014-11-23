package Model;

/**
 * Created by Filip on 2014-11-23.
 */
public class Simulation
{

    public Simulation()
    {
        simulationState = SimulationState.AUTO;
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

    public enum SimulationState{MANUAL, AUTO};

    private SimulationState simulationState;
    private int timePassed;
    private Interval interval;

}
