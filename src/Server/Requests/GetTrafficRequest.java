package Server.Requests;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class GetTrafficRequest extends AbstractRequest
{
    public GetTrafficRequest(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void send()
    {
        try
        {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject("GETTRAFFIC");
            writer.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
