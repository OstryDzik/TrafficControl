package Server.Requests;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class GetLightsRequest extends AbstractRequest {

    public GetLightsRequest(Socket clientSocket) {
        super(clientSocket);
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject("GETLIGHTS");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
