package Simulation;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;




import Model.Car;
//import Common.Mutex;
import Model.CarsInfo;
import Server.Server;
import Server.Requests.SetTrafficRequest;

public class SimulationConnection 
{
	public static int DEFAULT_TIMEOUT = 350;
	private static SimulationConnection instance;
	private Socket clientSocket;
	private CarsInfo carsInfo;
	private Thread connectionThread;
	private AtomicBoolean connectionThreadActive = new AtomicBoolean(false);
	private String address;
	
	//private Mutex mutex = new Mutex();
	
	public void connectToServer(final String address)
	{
		this.address = address;
		if (connectionThread == null || !connectionThread.isAlive())
		{
			connectionThreadActive.set(true);
			//zapobiegamy ponownemu laczeniu
			connectionThread = new Thread(new ConnectionTask());
			connectionThread.start();
		}
	}
	
	public void disconnectFromServer()
	{
		connectionThreadActive.set(false);
		try
		{
			clientSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static SimulationConnection getInstance()
	{
		if (instance == null)
			instance = new SimulationConnection();
		return instance;
	}
	
	private SimulationConnection()
	{
	}
	
	private class ConnectionTask implements Runnable
	{
		public void run()
		{
			    
				try
                {
                    clientSocket = new Socket(address, Server.DEFAULT_CARS_PORT);
                    clientSocket.setSoTimeout(DEFAULT_TIMEOUT);
                    while (connectionThreadActive.get())
                    {
                            setTraffic();
                            Thread.sleep(250);
                    }
                    connectionThreadActive.set(false);
                } catch (IOException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
				
		}
	
	private void setTraffic() throws IOException
	{
	        ArrayList<Car> cars = new ArrayList<>();
	        cars.add(new Car(2,2));
	        CarsInfo info = new CarsInfo(cars);
			SetTrafficRequest request = new SetTrafficRequest(clientSocket, info);
			request.send();
			// re
	}
	}
}
