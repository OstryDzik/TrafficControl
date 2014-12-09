package Server.Handlers;

import Model.Car;
import Model.CarsInfo;
import Server.Responses.OkResponse;
import Server.Responses.TrafficResponse;
import Server.Server;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class AddCarRequestHandler extends AbstractRequestHandler
{
    public AddCarRequestHandler(Car car, Socket socket)
    {
        super(socket);
        this.car = car;
    }


    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockCarsInfo();
        CarsInfo cInfo = server.getCarsInfo();
        cInfo.addCar(car);
        server.setCarsInfo(cInfo);

        server.unlockCarsInfo();
        sendResponse(new OkResponse());
    }

    private Car car;
}
