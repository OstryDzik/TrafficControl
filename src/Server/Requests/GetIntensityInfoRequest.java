package Server.Requests;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Adrian on 2014-12-03.
 */
public class GetIntensityInfoRequest extends AbstractRequest {

    public GetIntensityInfoRequest(Socket clientSocket){
        super(clientSocket);
    }

    @Override
    public void send()
    {
        try
        {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject("GETINTENSITY");
            writer.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
