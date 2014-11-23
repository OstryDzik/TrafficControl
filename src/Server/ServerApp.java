package Server;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class ServerApp {
    public static void main(String args[]) {
        Server server = Server.getInstance();

        server.start();
    }
}
