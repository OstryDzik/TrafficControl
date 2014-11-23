package Server.Handlers;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class GetSimulationStateHandler extends AbstractRequestHandler
{
    public GetSimulationStateHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {

    }
}
