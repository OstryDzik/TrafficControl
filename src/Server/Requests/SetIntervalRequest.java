package Server.Requests;

import Model.Interval;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class SetIntervalRequest extends AbstractRequest {
    public SetIntervalRequest(Socket clientSocket, Interval interval) {
        super(clientSocket);
        this.interval = interval;
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(interval);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Interval interval;
}
