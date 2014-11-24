package Server.Handlers;

import Model.CarsInfo;
import Model.LightsInfo;
import Server.Responses.LightsResponse;
import Server.Responses.TrafficResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class GetLightsRequestHandler extends AbstractRequestHandler
{
    public GetLightsRequestHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockLightsInfo();
        LightsInfo lInfo = server.getLightsInfo();
        LightsResponse response = new LightsResponse(clientSocket, lInfo);
        response.send();
        server.unlockLightsInfo();
    }
}
