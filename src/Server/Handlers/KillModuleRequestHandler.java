package Server.Handlers;

import Model.Module;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class KillModuleRequestHandler extends AbstractRequestHandler
{
    public KillModuleRequestHandler(Module module, Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public void handle()
    {

    }
}
