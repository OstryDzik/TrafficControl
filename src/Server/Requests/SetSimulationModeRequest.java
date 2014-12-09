package Server.Requests;


import Model.Simulation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SetSimulationModeRequest extends AbstractRequest
{
    
    /**
     * @param clientSocket
     * @param auto  true dla trybu automatycznego, false w p.p.
     */
    public SetSimulationModeRequest(Socket clientSocket, boolean auto)
    {
        super(clientSocket);
        this.auto = auto;
    }

    @Override
    public void send()
    {
        try
        {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            if(auto == true)
            {
                writer.writeObject(Simulation.SimulationState.AUTO);
            }
            else
            {
                writer.writeObject(Simulation.SimulationState.MANUAL);
            }
            writer.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private boolean auto;
}