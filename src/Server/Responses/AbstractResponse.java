package Server.Responses;

import Server.Requests.AbstractRequest;

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
