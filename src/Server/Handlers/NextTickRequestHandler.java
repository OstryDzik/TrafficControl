package Server.Handlers;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class NextTickRequestHandler extends AbstractRequestHandler
{
    public NextTickRequestHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {

    }
}
