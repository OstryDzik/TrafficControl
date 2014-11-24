package Server.Handlers;

import Model.Module;
import Model.Simulation;
import Server.Responses.OkResponse;
import Server.Server;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class KillModuleRequestHandler extends AbstractRequestHandler
{
    public KillModuleRequestHandler(Module module, Socket clientSocket)
    {
        super(clientSocket);
        this.module = module;
    }

    @Override
    public void handle()
    {
        Server server = Server.getInstance();
        server.lockSimulation();
        Simulation simulation = server.getSimulation();

        switch (module.getType()) {
            case GUI:
                if (simulation.isGuiOn())
                    simulation.setGuiOn(false);
                else
                    simulation.setGuiOn(true);
                break;
            case LIGHTS:
                if (simulation.isLightsModuleOn())
                    simulation.setLightsModuleOn(false);
                else
                    simulation.setLightsModuleOn(true);
                break;
            case SIM:
                if (simulation.isSimulationOn())
                    simulation.setSimulationOn(false);
                else
                    simulation.setSimulationOn(true);
                break;
        }

        server.setSimulation(simulation);
        OkResponse okResponse = new OkResponse(clientSocket);
        okResponse.send();
        server.unlockSimulation();
    }

    private Module module;
}
