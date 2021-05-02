package Service;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import Domain.*;

public interface Service extends Remote {

    Angajat login(String name, String password, IBibliotecaObserver client) throws BibliotecaException, RemoteException;

    Utilizator login_utilizator(String userName, String password, IBibliotecaObserver client) throws BibliotecaException, RemoteException;

    List<Carte> getAllCarti() throws BibliotecaException, RemoteException;

    Utilizator registerUtilizator(String username, String parola, String nume, String prenume, String adresa, String cnp) throws BibliotecaException, RemoteException;

    void imprumutaCarte(int id_u, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare, int ex_imprumutate) throws BibliotecaException, RemoteException;

    void restituieCarte(int id_u, String titlu, String autor, String data_imprumut, String data_restituire, int nr_exemplare, int ex_restituite) throws BibliotecaException, RemoteException;

    List<Imprumuturi> getImprumuturi(Utilizator utilizator) throws RemoteException;

    void adaugaCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare) throws BibliotecaException, RemoteException;

    void modificaCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare) throws BibliotecaException, RemoteException;

    void stergeCarte(int id_c) throws BibliotecaException, RemoteException;

    void logout(String username) throws BibliotecaException, RemoteException;
}
