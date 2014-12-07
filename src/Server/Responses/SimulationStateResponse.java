package Server.Responses;


import Model.Simulation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Filip on 2014-11-23.
 */
public class SimulationStateResponse extends AbstractResponse {
    public SimulationStateResponse(Simulation simulation) {
        this.simulation=simulation;
    }


    public Simulation getSimulation(){return simulation;}

    private Simulation simulation;
}
