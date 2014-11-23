package Server.Handlers;

import Model.Interval;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SetIntervalRequestHandler extends AbstractRequestHandler
{
    public SetIntervalRequestHandler(Interval interval, Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {

    }
}
