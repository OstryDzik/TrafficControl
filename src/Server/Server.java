package Server;


import Common.Mutex;
import Model.CarsInfo;
import Model.IntensityInfo;
import Model.LightsInfo;
import Model.Simulation;
import Server.Handlers.AbstractRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static final int DEFAULT_CARS_PORT = 8000;
    public static final int DEFAULT_LIGHTS_PORT = 8002;
    public static final int DEFAULT_GUI_PORT = 8003;
    public static final int DEFAULT_INTENSITY_PORT = 8004;

    public class ServerListener implements Runnable {

        public ServerListener(int port) {
            super();
            try {
                this.serverSocket = new ServerSocket(port);
                carsInfoMutex = new Mutex();
                lightsInfoMutex = new Mutex();
                simulationMutex = new Mutex();
                intensityInfoMutex = new Mutex();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    socket = serverSocket.accept();
                    System.out.println("New connection");
                    //Logger.log("New connection " + serverSocket.getLocalPort());
                    while (socket.isConnected() && !socket.isClosed()) {
                        AbstractRequestHandler handler = AbstractRequestHandler.createHandler(socket);
                        if (handler != null) {
                            handler.handle();
                        } else {
                            socket.close();
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private Socket socket;
        private ServerSocket serverSocket;
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
            instance.lightsInfo = new LightsInfo();
            for (int i = 0; i < 9; ++i) {
                instance.lightsInfo.setState(i, LightsInfo.LightsState.HORIZONTALLY_GREEN);
            }
        }
        return instance;
    }

    public void start() {

        simulation = new Simulation();
        carsServerListener = new ServerListener(DEFAULT_CARS_PORT);
        Thread carsThread = new Thread(carsServerListener);
        carsThread.start();

        lightsServerListener = new ServerListener(DEFAULT_LIGHTS_PORT);
        Thread lightsThread = new Thread(lightsServerListener);
        lightsThread.start();


        guiServerListener = new ServerListener(DEFAULT_GUI_PORT);
        Thread guiThread = new Thread(guiServerListener);
        guiThread.start();

        intensityListener = new ServerListener(DEFAULT_INTENSITY_PORT);
        Thread intensityThread = new Thread(intensityListener);
        intensityThread.start();
    }

    public LightsInfo getLightsInfo() {
        return lightsInfo;
    }

    public CarsInfo getCarsInfo() {
        return carsInfo;
    }

    public Simulation getSimulation()
    {
        return simulation;
    }

    public void setSimulation(Simulation simulation)
    {
        this.simulation = simulation;
    }

    public void setCarsInfo(CarsInfo carsInfo) {
        this.carsInfo = carsInfo;
    }

    public void setLightsInfo(LightsInfo lightsInfo) {
        this.lightsInfo = lightsInfo;
    }

    public IntensityInfo getIntensityInfo() {
        return intensityInfo;
    }

    public void setIntensityInfo(IntensityInfo intensityInfo) {
        this.intensityInfo = intensityInfo;
    }

    public void lockCarsInfo() {
        carsInfoMutex.Acquire();
    }

    public void unlockCarsInfo() {
        carsInfoMutex.Release();
    }
    public void lockLightsInfo() {
        lightsInfoMutex.Acquire();
    }

    public void unlockLightsInfo() {
        lightsInfoMutex.Release();
    }
    public void lockSimulation() {
        simulationMutex.Acquire();
    }

    public void unlockSimulation() {
        simulationMutex.Release();
    }

    public void lockIntensityInfo() { intensityInfoMutex.Acquire();}
    public void unlockIntensityInfo() { intensityInfoMutex.Release();}

    private Mutex carsInfoMutex;
    private Mutex lightsInfoMutex;
    private Mutex simulationMutex;
    private Mutex intensityInfoMutex;

    private ServerListener carsServerListener = null;
    private ServerListener lightsServerListener = null;
    private ServerListener guiServerListener = null;
    private ServerListener intensityListener = null;

    private LightsInfo lightsInfo = null;
    private CarsInfo carsInfo = null;

    private IntensityInfo intensityInfo = null;

    private Simulation simulation = null;

    private static Server instance = null;
}
