package Simulation;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

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
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					clientSocket.setSoTimeout(DEFAULT_TIMEOUT);
				} catch (SocketException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while (connectionThreadActive.get())
				{
					try {
						setTraffic();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				connectionThreadActive.set(false);
		}
	
	private void setTraffic() throws IOException
	{
			SetTrafficRequest request = new SetTrafficRequest(clientSocket, carsInfo);
			request.send();
			// re
	}
	}
}
