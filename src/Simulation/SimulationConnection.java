package Simulation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;















import Model.Car;
//import Common.Mutex;
import Model.CarsInfo;
import Model.IntensityInfo;
import Model.LightsInfo;
import Model.Simulation;
import Model.Simulation.SimulationState;
import Server.Server;
import Server.Requests.GetLightsRequest;
import Server.Requests.GetSimulationStateRequest;
import Server.Requests.SetIntensityInfoRequest;
import Server.Requests.SetTrafficRequest;
import Server.Responses.LightsResponse;
import Server.Responses.SimulationStateResponse;


public class SimulationConnection 
{
	public static int DEFAULT_TIMEOUT = 350;
	private static SimulationConnection instance;
	private Socket clientSocket;
	private CarsInfo carsInfo;
	private IntensityInfo intensityInfo;
	private Thread connectionThread;
	private AtomicBoolean connectionThreadActive = new AtomicBoolean(false);
	private String address;
	private WorldMap wm = new WorldMap();
	private Simulation actState = new Simulation();
	private int previousTicks = 0;
	
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
                    	getLights();
                    	getSimulationState();
                    	
                    	if(actState.getSimulationState() == SimulationState.MANUAL)
                    	{
                    		if((actState.getTimePassed()-previousTicks)> 0)
                    		{
                    			wm.nextMove();
                    			wm.addRandomCars(actState.getInterval().getMin(),actState.getInterval().getMax());
                    			setTraffic();
                    			setIntensity();
                    		}
                    		previousTicks=actState.getTimePassed();
                    	}
                    	else
                    	{
                    		wm.nextMove();
                    		wm.addRandomCars(actState.getInterval().getMin(),actState.getInterval().getMax());
                    		setTraffic();
                    		setIntensity();
                    		Thread.sleep(1000);
                    	}
                          
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
	        
	        CarsInfo info = new CarsInfo(wm.getCars());
			SetTrafficRequest request = new SetTrafficRequest(clientSocket, info);
			request.send();
			Object okResponse = readFromSocket();
			// re
	}
	
	private void setIntensity() throws IOException
	{
		IntensityInfo info = new IntensityInfo(wm.getIntensityTable());
		SetIntensityInfoRequest request = new SetIntensityInfoRequest(clientSocket, info);
		request.send();
		Object okResponse = readFromSocket();
	}
	
	private void getLights() throws IOException
	{
		GetLightsRequest request = new GetLightsRequest(clientSocket);
		request.send();
		Object response = readFromSocket();
		if (response instanceof LightsResponse)
		{
			wm.setLights(((LightsResponse) response).getLightsInfo());
		}
	}
	
	private void getSimulationState() throws IOException
	{
		GetSimulationStateRequest request = new GetSimulationStateRequest(clientSocket);
		request.send();
		Object response = readFromSocket();
		if (response instanceof SimulationStateResponse)
		{
			System.out.println(((SimulationStateResponse) response).getSimulation().getSimulationState());
			actState = ((SimulationStateResponse) response).getSimulation();
		}
	}
	
	private void getAddCar() throws IOException
	{
		
	}
	
	private Object readFromSocket() throws IOException, SocketTimeoutException
    {
        Object result = null;
        try
        {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            result = ois.readObject();
        } catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
	}
}
