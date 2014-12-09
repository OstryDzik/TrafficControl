package Server.Handlers;

import Model.IntensityInfo;
import Model.Interval;
import Model.Simulation;
import Server.Responses.OkResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by ThinkPad on 2014-12-07.
 */
public class SetIntensityInfoHandler extends AbstractRequestHandler
{
    public SetIntensityInfoHandler(IntensityInfo intensityInfo, Socket clientSocket)
    {
        super(clientSocket);
        this.intensityInfo = intensityInfo;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockIntensityInfo();
        server.setIntensityInfo(intensityInfo);

        server.unlockIntensityInfo();
        sendResponse(new OkResponse());
    }

    private IntensityInfo intensityInfo;
}
