package Server.Requests;

import Model.IntensityInfo;
import Model.LightsInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-12-07.
 */
public class SetIntensityInfoRequest extends AbstractRequest {
    public SetIntensityInfoRequest(Socket clientSocket, IntensityInfo intensityInfo)
    {
        super(clientSocket);
        this.intensityInfo = intensityInfo;
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

    private IntensityInfo intensityInfo;
}
