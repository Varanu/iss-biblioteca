import Repository.*;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import Service.*;
import server.ServerImplemented;

public class StartRMIServer {
    public static void main(String[] args) {

        //Properties serverProps = JdbcUtils.getProperties();
        //Integer port = Integer.parseInt(serverProps.getProperty("port"));
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRMIServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        IAngajatRepository angajatRepository = new AngajatRepository(serverProps);
        ICarteRepository carteRepository = new CarteRepository(serverProps);
        IUtilizatorRepository utilizatorRepository = new UtilizatorRepository(serverProps);
        Service service = new ServerImplemented(angajatRepository, carteRepository, utilizatorRepository);
        try {
            String name = serverProps.getProperty("biblioteca.rmi.serverID","Biblioteca");
            Service stub =(Service) UnicastRemoteObject.exportObject(service, 0);
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("before binding");
            registry.rebind(name, stub);
            System.out.println("Biblioteca server bound");
        } catch (Exception e) {
            System.err.println("Biblioteca server exception:"+e);
            e.printStackTrace();
        }
    }
}
