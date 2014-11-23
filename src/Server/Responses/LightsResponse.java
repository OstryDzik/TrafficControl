package Server.Responses;

import Model.LightsInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class LightsResponse extends AbstractResponse {
    public LightsResponse(Socket clientSocket, LightsInfo lightsInfo) {
        super(clientSocket);
        this.lightsInfo = lightsInfo;
    }

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
