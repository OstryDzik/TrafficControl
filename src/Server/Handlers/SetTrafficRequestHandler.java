package Server.Handlers;

import Model.CarsInfo;
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
        Server server = Server.GetInstance();
        server.lockCarsInfo();
        server.setCarsInfo(carsInfo);
        OkResponse okResponse = new OkResponse(clientSocket);
        okResponse.send();
        server.unlockCarsInfo();

    }
}
