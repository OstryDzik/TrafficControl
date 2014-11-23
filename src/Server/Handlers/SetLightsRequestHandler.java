package Server.Handlers;

import Model.LightsInfo;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SetLightsRequestHandler extends AbstractRequestHandler
{
    public SetLightsRequestHandler(LightsInfo lightsInfo, Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {

    }
}
