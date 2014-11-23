package Server.Requests;

import Model.CarsInfo;
import Model.LightsInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class SetLightsRequest extends AbstractRequest {
    public SetLightsRequest(Socket clientSocket, LightsInfo lightsInfo)
    {
        super(clientSocket);
        this.lightsInfo = lightsInfo;
    }

    @Override
    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(lightsInfo);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private LightsInfo lightsInfo;
}
