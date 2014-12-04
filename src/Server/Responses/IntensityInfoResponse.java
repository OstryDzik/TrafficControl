package Server.Responses;

import Model.IntensityInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Adrian on 2014-12-03.
 */
public class IntensityInfoResponse extends AbstractResponse {
    private final IntensityInfo intensityInfo;

    public IntensityInfoResponse(Socket clientSocket, IntensityInfo intensityInfo) {
        super(clientSocket);
        this.intensityInfo = intensityInfo;
    }

    public IntensityInfo getIntensityInfo() {
        return intensityInfo;
    }

    @Override
    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(intensityInfo);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
