package Server.Responses;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public abstract class AbstractResponse {
    public AbstractResponse(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public abstract void send();

    protected Socket clientSocket;
}
