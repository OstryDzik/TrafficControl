package Server.Handlers;

import Model.Interval;
import Model.Simulation;
import Server.Responses.OkResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SetIntervalRequestHandler extends AbstractRequestHandler
{
    public SetIntervalRequestHandler(Interval interval, Socket clientSocket)
    {
        super(clientSocket);
        this.interval = interval;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockSimulation();
        Simulation simulation = server.getSimulation();
        simulation.setInterval(interval);
        server.setSimulation(simulation);
        OkResponse okResponse = new OkResponse(clientSocket);
        okResponse.send();
        server.unlockSimulation();
    }

    private Interval interval;
}
