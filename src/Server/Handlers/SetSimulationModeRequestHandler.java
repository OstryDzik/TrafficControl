package Server.Handlers;


import Model.Interval;
import Model.Simulation;
import Server.Responses.OkResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-12-09.
 */
public class SetSimulationModeRequestHandler extends AbstractRequestHandler
{

    private Simulation simulation;

    public SetSimulationModeRequestHandler(Simulation simulation, Socket clientSocket)
    {
        super(clientSocket);
        this.simulation = simulation;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockSimulation();
        Simulation simulation = server.getSimulation();
        simulation.setState(this.simulation.getSimulationState());
        Interval interval = new Interval(simulation.getInterval().getMin(), simulation.getInterval().getMax());
        simulation.setInterval(interval);
        server.setSimulation(simulation);
        sendResponse(new OkResponse());
        server.unlockSimulation();
    }
}
