package Server.Requests;

import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public abstract class AbstractRequest
{
    public AbstractRequest(Socket clientSocket) {
        super();
        this.clientSocket = clientSocket;
    }

    public abstract void send();

    protected Socket clientSocket;
}
