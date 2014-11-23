package Model;

/**
 * Created by Filip on 2014-11-23.
 */
public class SimulationInfo
{
    public final Simulation.SimulationState simulationState;
    public final int timePassed;
    public final Interval interval;

    public SimulationInfo(Simulation simulation)
    {
        simulationState = simulation.getSimulationState();
        timePassed = simulation.getTimePassed();
        interval = simulation.getInterval();
    }
}
