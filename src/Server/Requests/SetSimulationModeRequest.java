package Server.Requests;


import Model.Interval;
import Model.Simulation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SetSimulationModeRequest extends AbstractRequest
{
    
    /**
     * @param clientSocket
     * @param auto  true dla trybu automatycznego, false w p.p.
     */
    public SetSimulationModeRequest(Socket clientSocket, boolean auto, int intervalMin, int intervalMax)
    {
        super(clientSocket);
        this.auto = auto;
        this.min = intervalMin;
        this.max = intervalMax;
    }

    @Override
    public void send()
    {
        Simulation sim = new Simulation();
        Interval i = new Interval(this.min, this.max);
        sim.setInterval(i);
        sim.setState(auto ? Simulation.SimulationState.AUTO : Simulation.SimulationState.MANUAL);
        try
        {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(sim);
            writer.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private boolean auto;
    private int min;
    private int max;
}