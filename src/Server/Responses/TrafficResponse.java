package Server.Responses;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.CarsInfo;

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
    
    public CarsInfo getCarsInfo()
	{
		return carsInfo;
	}

	private CarsInfo carsInfo;
}
