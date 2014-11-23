package Server.Responses;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class OkResponse extends AbstractResponse{
    public OkResponse(Socket clientSocket) {
        super(clientSocket);
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject("OK");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
