package Server.Handlers;

import Model.*;
import Server.Requests.*;
import Server.Responses.AbstractResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class AbstractRequestHandler
{

    public AbstractRequestHandler(Socket clientSocket)
    {
        super();
        this.clientSocket = clientSocket;
    }

    public boolean isConnectionAlive()
    {
        return clientSocket != null && clientSocket.isConnected()
                && !clientSocket.isClosed();
    }

    public abstract void handle();

    public static AbstractRequestHandler createHandler(Socket clientSocket)
    {
        if (clientSocket == null || !clientSocket.isConnected() || clientSocket.isClosed())
            return null;

        try
        {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            Object readObject = in.readObject();
            if (readObject instanceof String)
            {
                String readStirng = (String)readObject;
                if(readStirng.equals("GETLIGHTS"))
                    return new GetLightsRequestHandler(clientSocket);
                if(readStirng.equals("GETTRAFFIC"))
                    return new GetTrafficRequestHandler(clientSocket);
                if(readStirng.equals("GETSIMULATIONSTATE"))
                    return new GetSimulationStateHandler(clientSocket);
                if(readStirng.equals("NEXTTICK"))
                    return new NextTickRequestHandler(clientSocket);
                if(readStirng.equals("GETINTENSITY"))
                    return new GetIntensityHandler(clientSocket);
            }
            if (readObject instanceof Car)
                return new AddCarRequestHandler((Car)readObject, clientSocket);
            if (readObject instanceof CarsInfo)
                return new SetTrafficRequestHandler((CarsInfo)readObject,clientSocket);
            if (readObject instanceof Module)
                return new KillModuleRequestHandler((Module)readObject, clientSocket);
            if (readObject instanceof LightsInfo)
                return new SetLightsRequestHandler((LightsInfo)readObject, clientSocket);
            if (readObject instanceof Interval)
                return new SetIntervalRequestHandler((Interval)readObject, clientSocket);
            if (readObject instanceof IntensityInfo)
                return new SetIntensityInfoHandler((IntensityInfo)readObject, clientSocket);
            if (readObject instanceof Simulation.SimulationState)
                return  new SetSimulationModeRequestHandler((Simulation.SimulationState)readObject, clientSocket);
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Client disconnected");
            return null;
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    protected void sendResponse(AbstractResponse response)
    {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            writer.writeObject(response);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected Socket clientSocket;
}