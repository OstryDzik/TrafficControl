package Server.Handlers;

import Model.CarsInfo;
import Model.Simulation;
import Server.Responses.SimulationStateResponse;
import Server.Responses.TrafficResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class GetSimulationStateHandler extends AbstractRequestHandler
{
    public GetSimulationStateHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockSimulation();
        Simulation sInfo = server.getSimulation();
        SimulationStateResponse response = new SimulationStateResponse(clientSocket, sInfo);
        response.send();
        server.unlockSimulation();
    }
}
