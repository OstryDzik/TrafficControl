package Lights;

import Common.Mutex;
import GraphicalInterfaceModule.InterfaceFrame;
import Model.Car;
import Model.IntensityInfo;
import Model.LightsInfo;
import Model.Module;
import Server.Requests.AddLightsRequest;
import Server.Requests.GetIntensityInfoRequest;
import Server.Responses.IntensityInfoResponse;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Adrian on 2014-11-24.
 */
public class LightConnection {
    public static int DEFAULT_TIMEOUT = 350; // w ms
    public static final int LIGHTS_NUMBER = 9;
    /** Instancja */
    private static LightConnection instance;
    /** socket, przez ktory klient bedzie komunikowal sie z serwerem */
    private Socket clientSocket;
    /** Watek odbierajacy zdarzenia od kontrolera i wywolujacy odpowiednie akcje */
    private Thread connectionThread;
    /** Określa czy wątek readera jest aktywny */
    private AtomicBoolean connectionThreadActive = new AtomicBoolean(false);
    /** Adres IP serwera */
    private String address;
    /** kontroler przelaczania swiatel **/
    private LightsController lightsController;
    /** informacje o ruchu **/
    private IntensityInfo currentIntensityInfo;
    /** informacje o swiatlach **/
    private LightsInfo lightsInfo;

    public static LinkedBlockingQueue<Module> modulesToKill = new LinkedBlockingQueue<Module>();
    public static LinkedBlockingQueue<Car> carsToAdd = new LinkedBlockingQueue<Car>();

    private Mutex mutex = new Mutex();

    public void connectToServer(final String address)
    {
        this.address = address;
        if (connectionThread == null || !connectionThread.isAlive())
        {
            connectionThreadActive.set(true);
            // zapobiegamy ponownemu łączeniu
            connectionThread = new Thread(new ConnectionTask());
            connectionThread.start();
        }
    }

    public void disconnectFromServer()
    {
        connectionThreadActive.set(false);
        try
        {
            clientSocket.close();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static LightConnection getInstance()
    {
        if (instance == null)
            instance = new LightConnection();
        return instance;
    }

    public LightsController getLightsController() {
        return lightsController;
    }

    public void setLightsController(LightsController lightsController) {
        this.lightsController = lightsController;
    }

    LightConnection()
    {
        lightsController = new LightsController(LIGHTS_NUMBER);
    }

    private class ConnectionTask implements Runnable
    {
        /**
         * Watek odczytujacy zdarzenia pochodzace z kontrolera
         */
        public void run()
        {
            try
            {
                clientSocket = new Socket(address, Server.DEFAULT_LIGHTS_PORT);
                clientSocket.setSoTimeout(DEFAULT_TIMEOUT);
//                InterfaceFrame.writeToConsole("# Polaczono");
                System.out.println("Start komunikacji");
                //TODO delete
                int[][] a = new int[9][9];
                IntensityInfo mockIntensityInfo = new IntensityInfo(a);

                while (connectionThreadActive.get())
                {
                    askIntesityInfo();
                    lightsInfo = lightsController.updateLights(mockIntensityInfo);
                    // symulacja
//                    if (InterfaceFrame.isSimAuto() || InterfaceFrame.step.get())
//                    {
//                        InterfaceFrame.writeToConsole("------------------");
//
//                        // kolejny ruch
//                        nextTick();
//                        InterfaceFrame.step.set(false);
//
//                        // awaryjne odłączenie
//                        if (!modulesToKill.isEmpty())
//                        {
//                            // za każdym razem odłączamy jeden moduł (jeśli było wiele)
//                            killModule(modulesToKill.take());
//                        }
//
//                        // dodanie samochodu
//                        if (!carsToAdd.isEmpty())
//                        {
//                            // za każdym razem odłączamy jeden moduł (jeśli było wiele)
//                            addCar(carsToAdd.take());
//                        }
//
//                        // pytanie o światła
//                        askLights();
//
//                        // pytanie o samochody
//                        askTraffic();
//
//                    }

                    // odstęp czasowy
                    try
                    {
                        Thread.sleep(250);
                    } catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                System.out.println("Stop komunikacji");
            } catch (UnknownHostException e)
            {
                InterfaceFrame.writeToConsole("! Unknown host exception");
                e.printStackTrace();
            } catch (SocketException e)
            {
                System.out.println("! Błąd socketu: " + e.getMessage());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                // in case
                clientSocket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("# Rozłączono");
            connectionThreadActive.set(false);
        }

        private void addLights(LightsInfo lights) throws IOException
        {
            try
            {
                AddLightsRequest request = new AddLightsRequest(clientSocket, lights);
                request.send();
                Object okResponse = readFromSocket();
//                if (okResponse instanceof LightsResponse)
//                {
//                    InterfaceFrame.writeToConsole("# Odebrano potwierdzenie dodania samochodu");
//                }
            } catch (SocketTimeoutException exc)
            {
                // TODO przejście modułu w stan domyślny
                System.out.println("Nie udało się zmienić swiateł");
            }
        }

//
//        private void killModule(Module module) throws IOException
//        {
//            try
//            {
//                KillModuleRequest request = new KillModuleRequest(clientSocket, module);
//                request.send();
//                Object okResponse = readFromSocket();
//                if (okResponse instanceof LightsResponse)
//                {
//                    InterfaceFrame.writeToConsole("# Odebrano potwierdzenie odłączenia modułu");
//                }
//            } catch (SocketTimeoutException exc)
//            {
//                // TODO przejście modułu w stan domyślny
//                InterfaceFrame.writeToConsole("! Nie udało się odłączyć modułu");
//            }
//        }
//
//        private void nextTick() throws IOException
//        {
//            try
//            {
//                NextTickRequest request = new NextTickRequest(clientSocket);
//                request.send();
//                Object response = readFromSocket();
//                if (response instanceof LightsResponse)
//                {
//                    InterfaceFrame.writeToConsole("# Odebrano potwierdzenie symulacji");
//                }
//            } catch (SocketTimeoutException exc)
//            {
//                // TODO przejście modułu w stan domyślny
//                InterfaceFrame.writeToConsole("! Symulacja nie odpowiada");
//            }
//        }
//
//        private void askLights() throws IOException
//        {
//            try
//            {
//                GetLightsRequest request = new GetLightsRequest(clientSocket);
//                request.send();
//                Object response = readFromSocket();
//                if (response instanceof LightsResponse)
//                {
//                    if (((LightsResponse) response).getLightsInfo() != null)
//                    {
//                        mutex.Acquire();
//                        InterfaceFrame.writeToConsole("# Odebrano informacje o światłach");
//                        WorldPanel.getInstance().updateUnknown(((LightsResponse) response).getLightsInfo());
//                        mutex.Release();
//                    }
//                }
//            } catch (SocketTimeoutException exc)
//            {
//                // TODO przejście modułu w stan domyślny
//                InterfaceFrame.writeToConsole("! brak odpowiedzi ze stanem świateł");
//            }
//        }
//
        private void askIntesityInfo() throws IOException
        {
            try
            {
                GetIntensityInfoRequest request = new GetIntensityInfoRequest(clientSocket);
                request.send();
                Object response = readFromSocket();
                if (response instanceof IntensityInfo)
                {
                    if (((IntensityInfoResponse) response).getIntensityInfo() != null)
                    {
                        currentIntensityInfo = ((IntensityInfoResponse) response).getIntensityInfo();
                    }
                }
            } catch (SocketTimeoutException exc)
            {
                // TODO przejście modułu w stan domyślny
                InterfaceFrame.writeToConsole("! brak odpowiedzi ze stanem ruchu");
            }
        }

        private Object readFromSocket() throws IOException, SocketTimeoutException
        {
            Object result = null;
            try
            {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                result = ois.readObject();
            } catch (ClassNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }
    }
}
