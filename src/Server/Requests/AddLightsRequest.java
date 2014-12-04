package Server.Requests;

import Model.LightsInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Adrian on 2014-12-03.
 */
public class AddLightsRequest extends AbstractRequest {
    private final LightsInfo lightsInfo;

    public AddLightsRequest(Socket clientSocket, LightsInfo lightsInfo) {
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
}
