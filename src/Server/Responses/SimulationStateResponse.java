package Server.Responses;


import Model.Simulation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SimulationStateResponse extends AbstractResponse {
    public SimulationStateResponse(Socket clientSocket, Simulation simulation) {
        super(clientSocket);
        this.simulation=simulation;
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(simulation);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Simulation simulation;
}
