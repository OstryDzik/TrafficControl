package Server.Responses;

import Model.Car;
import Model.CarsInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class TrafficResponse extends AbstractResponse {
    public TrafficResponse(Socket clientSocket, CarsInfo carsInfo) {
        super(clientSocket);
        this.carsInfo = carsInfo;
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(carsInfo);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CarsInfo carsInfo;
}
