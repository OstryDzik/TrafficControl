package Simulation;

import GraphicalInterfaceModule.GraphicalInterfaceMain;

public class SimulationRun 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		SimulationConnection.getInstance().connectToServer(GraphicalInterfaceMain.DEFAULT_IP);

	}

}
