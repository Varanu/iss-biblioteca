import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import Service.*;
import gui.*;

public class StartRMIClient {
    private static String defaultServer="localhost";
    public static void main(String[] args) {

        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRMIClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties "+e);
            return;
        }

        String name=clientProps.getProperty("biblioteca.rmi.serverID","Biblioteca");
        String serverIP=clientProps.getProperty("biblioteca.server.host",defaultServer);
        try {

            Registry registry = LocateRegistry.getRegistry(serverIP);
            Service server = (Service) registry.lookup(name);
            System.out.println("Obtained a reference to remote chat server");

            Login f = new Login(server);
            f.setVisible(true);

        } catch (Exception e) {
            System.err.println("Biblioteca Initialization  exception:"+e);
            e.printStackTrace();
        }

    }
}
