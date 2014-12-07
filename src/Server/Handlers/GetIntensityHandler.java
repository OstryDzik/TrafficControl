package Server.Handlers;

import Model.IntensityInfo;
import Model.LightsInfo;
import Server.Responses.IntensityInfoResponse;
import Server.Responses.LightsResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by ThinkPad on 2014-12-07.
 */
public class GetIntensityHandler extends AbstractRequestHandler
{
    public GetIntensityHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockIntensityInfo();
        IntensityInfo iInfo = server.getIntensityInfo();
        IntensityInfoResponse response = new IntensityInfoResponse(iInfo);
        sendResponse(response);
        server.unlockIntensityInfo();
    }
}
