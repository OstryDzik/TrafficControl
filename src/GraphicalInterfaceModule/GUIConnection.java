package GraphicalInterfaceModule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import Common.Mutex;
import Model.Car;
import Model.Module;
import Server.Server;
import Server.Requests.AddCarRequest;
import Server.Requests.GetLightsRequest;
import Server.Requests.GetTrafficRequest;
import Server.Requests.KillModuleRequest;
import Server.Requests.NextTickRequest;
import Server.Requests.SetSimulationModeRequest;
import Server.Responses.LightsResponse;
import Server.Responses.OkResponse;
import Server.Responses.TrafficResponse;

/**
 * @author Marcin Jeleński
 */
public class GUIConnection
{
    public static int DEFAULT_TIMEOUT = 350; // w ms
    /** Instancja */
    private static GUIConnection instance;
    /** socket, przez ktory klient bedzie komunikowal sie z serwerem */
    private Socket clientSocket;
    /** Watek odbierajacy zdarzenia od kontrolera i wywolujacy odpowiednie akcje */
    private Thread connectionThread;
    /** Określa czy wątek readera jest aktywny */
    private AtomicBoolean connectionThreadActive = new AtomicBoolean(false);
    /** Adres IP serwera */
    private String address;

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

    public static GUIConnection getInstance()
    {
        if (instance == null)
            instance = new GUIConnection();
        return instance;
    }

    private GUIConnection()
    {
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
                clientSocket = new Socket(address, Server.DEFAULT_GUI_PORT);
                clientSocket.setSoTimeout(DEFAULT_TIMEOUT);
                InterfaceFrame.writeToConsole("# Polaczono");

                while (connectionThreadActive.get())
                {
                    // symulacja
                    InterfaceFrame.writeToConsole("------------------");

                    // kolejny ruch
                    if (!InterfaceFrame.simAuto && InterfaceFrame.step.get())
                    {
                        nextTick();
                        InterfaceFrame.step.set(false);
                    }

                    // awaryjne odłączenie
                    if (!modulesToKill.isEmpty())
                    {
                        // za każdym razem odłączamy jeden moduł (jeśli było wiele)
                        killModule(modulesToKill.take());
                    }

                    // dodanie samochodu
                    if (!carsToAdd.isEmpty())
                    {
                        // za każdym razem odłączamy jeden moduł (jeśli było wiele)
                        addCar(carsToAdd.take());
                    }

                    // pytanie o światła
                    askLights();

                    // pytanie o samochody
                    askTraffic();

                    // aktualizacja zmiany trybu symulacji
                    updateAutoState();

                    // odstęp czasowy
                    try
                    {
                        Thread.sleep(500);
                    } catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (UnknownHostException e)
            {
                InterfaceFrame.writeToConsole("! Unknown host exception");
                e.printStackTrace();
            } catch (SocketException e)
            {
                InterfaceFrame.writeToConsole("! Błąd socketu: " + e.getMessage());
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
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
            InterfaceFrame.writeToConsole("# Rozłączono");
            connectionThreadActive.set(false);
        }

        private void addCar(Car car) throws IOException
        {
            try
            {
                AddCarRequest request = new AddCarRequest(clientSocket, car);
                request.send();
                Object okResponse = readFromSocket();
                if (okResponse instanceof OkResponse)
                {
                    InterfaceFrame.writeToConsole("# Odebrano potwierdzenie dodania samochodu");
                }
            } catch (SocketTimeoutException exc)
            {
                // TODO przejście modułu w stan domyślny
                InterfaceFrame.writeToConsole("! Nie udało się dodać samochodu");
            }
        }

        private void killModule(Module module) throws IOException
        {
            try
            {
                KillModuleRequest request = new KillModuleRequest(clientSocket, module);
                request.send();
                Object okResponse = readFromSocket();
                if (okResponse instanceof OkResponse)
                {
                    InterfaceFrame.writeToConsole("# Odebrano potwierdzenie odłączenia modułu");
                }
            } catch (SocketTimeoutException exc)
            {
                // TODO przejście modułu w stan domyślny
                InterfaceFrame.writeToConsole("! Nie udało się odłączyć modułu");
            }
        }

        /**
         * Aktualizuje tryb auto manual, jeśli jest taka potrzeba
         * 
         * @throws IOException
         */
        private void updateAutoState() throws IOException
        {
            if (auto != InterfaceFrame.simAuto)
            {
                // odświeżamy tylko jeśli jest zmiana
                try
                {
                    SetSimulationModeRequest request = new SetSimulationModeRequest(clientSocket, InterfaceFrame.simAuto);
                    request.send();
                    Object response = readFromSocket();
                    if (response instanceof String && response.equals("OK") || response instanceof OkResponse)
                    {
                        InterfaceFrame.writeToConsole("# Odebrano potwierdzenie zmiany trybu symulacji");
                        auto = InterfaceFrame.simAuto;
                    }
                } catch (SocketTimeoutException exc)
                {
                    // TODO przejście modułu w stan domyślny
                    InterfaceFrame.writeToConsole("! Serwer nie odpowiada na zmianę trybu symulacji");
                }
            }
        }

        private void nextTick() throws IOException
        {
            try
            {
                NextTickRequest request = new NextTickRequest(clientSocket);
                request.send();
                Object response = readFromSocket();
                if (response instanceof String && response.equals("OK") || response instanceof OkResponse)
                {
                    InterfaceFrame.writeToConsole("# Odebrano potwierdzenie symulacji o następnym kroku");
                }
            } catch (SocketTimeoutException exc)
            {
                // TODO przejście modułu w stan domyślny
                InterfaceFrame.writeToConsole("! Symulacja nie odpowiada");
            }
        }

        private void askLights() throws IOException
        {
            try
            {
                GetLightsRequest request = new GetLightsRequest(clientSocket);
                request.send();
                Object response = readFromSocket();
                if (response instanceof LightsResponse)
                {
                    if (((LightsResponse) response).getLightsInfo() != null)
                    {
                        mutex.Acquire();
                        InterfaceFrame.writeToConsole("# Odebrano informacje o światłach");
                        WorldPanel.getInstance().updateUnknown(((LightsResponse) response).getLightsInfo());
                        mutex.Release();
                    }
                }
            } catch (SocketTimeoutException exc)
            {
                // TODO przejście modułu w stan domyślny
                InterfaceFrame.writeToConsole("! brak odpowiedzi ze stanem świateł");
            }
        }

        private void askTraffic() throws IOException
        {
            try
            {
                GetTrafficRequest request = new GetTrafficRequest(clientSocket);
                request.send();
                Object response = readFromSocket();
                if (response instanceof TrafficResponse)
                {
                    if (((TrafficResponse) response).getCarsInfo() != null)
                    {
                        mutex.Acquire();
                        InterfaceFrame.writeToConsole("Odebrano informacje o samochodach");
                        WorldPanel.getInstance().updateUnknown(((TrafficResponse) response).getCarsInfo());
                        mutex.Release();
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

        private boolean auto = false;
    }
}
