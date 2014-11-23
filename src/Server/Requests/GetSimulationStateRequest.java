package Server.Requests;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class GetSimulationStateRequest extends AbstractRequest
{
    public GetSimulationStateRequest(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void send()
    {
        try
        {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject("GETSIMULATIONSTATE");
            writer.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
