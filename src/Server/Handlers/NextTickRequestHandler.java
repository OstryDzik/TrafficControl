package Server.Handlers;

import Model.Simulation;
import Server.Responses.OkResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class NextTickRequestHandler extends AbstractRequestHandler
{
    public NextTickRequestHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockSimulation();
        Simulation simulation = server.getSimulation();
        simulation.advanceTime();
        server.setSimulation(simulation);
//        OkResponse okResponse = new OkResponse(clientSocket);
//        okResponse.send();

        server.unlockSimulation();
        sendResponse(new OkResponse());
    }
}
