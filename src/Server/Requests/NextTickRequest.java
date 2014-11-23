package Server.Requests;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class NextTickRequest extends AbstractRequest {
    public NextTickRequest(Socket clientSocket) {
        super(clientSocket);
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject("NEXTTICK");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
