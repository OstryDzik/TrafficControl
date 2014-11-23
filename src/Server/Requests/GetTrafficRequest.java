package Server.Requests;

import Model.CarsInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class GetTrafficRequest extends AbstractRequest{

    public GetTrafficRequest(Socket clientSocket, CarsInfo carsInfo)
    {
        super(clientSocket);
        this.carsInfo=carsInfo;
    }

    @Override
    public void send()
    {
        try
        {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject((Object)carsInfo);
            writer.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private CarsInfo carsInfo;
}
