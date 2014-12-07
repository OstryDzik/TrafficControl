package Server.Handlers;

import Model.LightsInfo;
import Server.Responses.OkResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SetLightsRequestHandler extends AbstractRequestHandler
{
    public SetLightsRequestHandler(LightsInfo lightsInfo, Socket clientSocket)
    {
        super(clientSocket);
        this.lightsInfo = lightsInfo;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockLightsInfo();
        server.setLightsInfo(lightsInfo);
        sendResponse(new OkResponse());
        server.unlockLightsInfo();
    }

    private LightsInfo lightsInfo;
}
