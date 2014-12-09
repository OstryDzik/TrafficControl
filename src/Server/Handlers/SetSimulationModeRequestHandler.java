package Server.Handlers;


import Model.Simulation;
import Server.Responses.OkResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-12-09.
 */
public class SetSimulationModeRequestHandler extends AbstractRequestHandler
{

    private Simulation.SimulationState state;

    public SetSimulationModeRequestHandler(Simulation.SimulationState state, Socket clientSocket)
    {
        super(clientSocket);
        this.state = state;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockSimulation();
        Simulation simulation = server.getSimulation();
        simulation.setState(state);
        server.setSimulation(simulation);
        sendResponse(new OkResponse());
        server.unlockSimulation();
    }
}
