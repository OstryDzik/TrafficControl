package Simulation;

import GraphicalInterfaceModule.GraphicalInterfaceMain;

public class SimulationRun 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		SimulationConnection.getInstance().connectToServer(GraphicalInterfaceMain.DEFAULT_IP);

        //To jest do testow		
//		WorldMap wm = new WorldMap();
//	    wm.print();
//	    for (int i = 0; i < 25; i++)
//        {
//            wm.nextMove();
//            wm.print();
//        }
	}

}
