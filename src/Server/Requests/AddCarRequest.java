package Server.Requests;

import Model.Car;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class AddCarRequest extends AbstractRequest {
    public AddCarRequest(Socket clientSocket, Car car) {
        super(clientSocket);
        self.car = car;
    }

    public void send() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(car);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Car car;
}
