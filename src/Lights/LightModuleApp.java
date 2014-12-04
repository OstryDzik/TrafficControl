package Lights;

/**
 * Created by Adrian on 2014-11-24.
 */
public class LightModuleApp {
    public static final String ADDRESS = "127.0.0.1";
    public static void main(String[] args) {
        LightsController lightsController = new LightsController(9);
        LightConnection lightConnection = new LightConnection();
        lightConnection.setLightsController(lightsController);
        lightConnection.connectToServer(ADDRESS);
        System.out.println("koniec");

    }
}
