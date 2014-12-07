package Server.Handlers;

import Model.CarsInfo;
import Server.Server;
import Server.Responses.OkResponse;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SetTrafficRequestHandler extends AbstractRequestHandler
{
    private CarsInfo carsInfo;

    public SetTrafficRequestHandler(CarsInfo carsInfo, Socket clientSocket)
    {
        super(clientSocket);
        this.carsInfo = carsInfo;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockCarsInfo();
        server.setCarsInfo(carsInfo);
        sendResponse(new OkResponse());
        server.unlockCarsInfo();

    }
}
