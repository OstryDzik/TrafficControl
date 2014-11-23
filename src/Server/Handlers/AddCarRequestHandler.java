package Server.Handlers;

import Model.Car;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class AddCarRequestHandler extends AbstractRequestHandler
{
    public AddCarRequestHandler(Car car, Socket socket)
    {
        super(socket);
    }


    @Override
    public void handle()
    {

    }
}
