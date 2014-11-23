package Server.Handlers;

import Model.Interval;
import Server.Responses.OkResponse;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SetIntervalRequestHandler extends AbstractRequestHandler
{
    public SetIntervalRequestHandler(Interval interval, Socket clientSocket)
    {
        super(clientSocket);
        this.interval = interval;
    }

    @Override
    public void handle()
    {
        Server server = Server.GetInstance();
        server.lockInterval();
        server.setInterval(interval);
        OkResponse okResponse = new OkResponse(clientSocket);
        okResponse.send();
        server.unlockInterval();
    }

    private Interval interval;
}
