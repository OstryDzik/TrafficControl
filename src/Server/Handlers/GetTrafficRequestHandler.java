package Server.Handlers;

import Model.CarsInfo;
import Server.Responses.OkResponse;
import Server.Responses.TrafficResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class GetTrafficRequestHandler extends AbstractRequestHandler
{
    public GetTrafficRequestHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockCarsInfo();
        CarsInfo cInfo = server.getCarsInfo();
        TrafficResponse response = new TrafficResponse(clientSocket, cInfo);
        response.send();
        server.unlockCarsInfo();
    }
}
