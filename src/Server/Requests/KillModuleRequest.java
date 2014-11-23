package Server.Requests;

import Model.Module;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class KillModuleRequest extends AbstractRequest {

    public KillModuleRequest(Socket clientSocket, Module module) {
        super(clientSocket);
        self.module = module;
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(module);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Module module;
}
