package Service;

import Domain.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IBibliotecaObserver extends Remote {
    void notifyClient(List<Carte> carti) throws BibliotecaException, RemoteException;
}
