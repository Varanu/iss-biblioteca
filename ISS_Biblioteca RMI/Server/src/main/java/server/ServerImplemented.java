package server;

import Domain.*;
import Repository.*;
import Service.*;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImplemented implements Service {

    private Map<String, IBibliotecaObserver> loggedUsers;
    private IAngajatRepository angajatRepository;
    private ICarteRepository carteRepository;
    private IUtilizatorRepository utilizatorRepository;

    public ServerImplemented(IAngajatRepository angajatRepository, ICarteRepository carteRepository, IUtilizatorRepository utilizatorRepository) {
        this.angajatRepository = angajatRepository;
        this.carteRepository = carteRepository;
        this.utilizatorRepository = utilizatorRepository;
        loggedUsers = new ConcurrentHashMap<>();

    }

    private void notifyEvent(List<Carte> carti) throws BibliotecaException {
        Collection<IBibliotecaObserver> list = this.loggedUsers.values();
        list.forEach(x -> {
            try {
                x.notifyClient(carti);
            } catch (RemoteException | BibliotecaException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized Angajat login(String userName, String password, IBibliotecaObserver client) throws BibliotecaException {
        Angajat loggedUser = angajatRepository.findAngajatByUserandPass(userName, password);
        if (loggedUser == null) {
            throw new BibliotecaException("Username or password is invalid");
        } else {
            if (loggedUsers.get(loggedUser.getUser()) != null) {
                throw new BibliotecaException("User already logged in.");
            } else {
                loggedUsers.put(loggedUser.getUser(), client);
                System.out.println("in servImpl am returnat userul " + loggedUser.toString() + ", din map " + loggedUsers.keySet().size());
                return loggedUser;
            }
        }
    }

    public synchronized Utilizator login_utilizator(String userName, String password, IBibliotecaObserver client) throws BibliotecaException {
        Utilizator loggedUser = utilizatorRepository.findUtilizatorByUserandPass(userName, password);
        if (loggedUser == null) {
            throw new BibliotecaException("Username or password is invalid");
        } else {
            if (loggedUsers.get(loggedUser.getUsername()) != null) {
                throw new BibliotecaException("User already logged in.");
            } else {
                loggedUsers.put(loggedUser.getUsername(), client);
                System.out.println("in servImpl am returnat userul " + loggedUser.toString() + ", din map " + loggedUsers.keySet().size());
                return loggedUser;
            }
        }
    }


    @Override
    public List<Carte> getAllCarti() {
        return carteRepository.getAllCarti();
    }

    @Override
    public Utilizator registerUtilizator(String username, String parola, String nume, String prenume, String adresa, String cnp){
        return utilizatorRepository.registerUtilizator(username, parola, nume, prenume, adresa, cnp);
    }

    @Override
    public void imprumutaCarte(int id_u, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare, int ex_imprumutate) throws BibliotecaException{
        boolean imprumut = carteRepository.imprumutaCarte(id_u, titlu, autor, editura, an_aparitie, nr_exemplare, ex_imprumutate);
        if(!imprumut){
            throw new BibliotecaException("Nu exista exemplare disponibile");
        }
        else {
            List<Carte> carti = carteRepository.getAllCarti();
            notifyEvent(carti);
        }
    }

    @Override
    public void restituieCarte(int id_u, String titlu, String autor, String data_imprumut, String data_restituire, int nr_exemplare, int ex_restituite) throws BibliotecaException{
        boolean restituire = carteRepository.restituieCarte(id_u, titlu, autor, data_imprumut, data_restituire, nr_exemplare, ex_restituite);
        if(!restituire){
            throw new BibliotecaException("Nu exista exemplare disponibile");
        }
        else {
            List<Carte> carti = carteRepository.getAllCarti();
            notifyEvent(carti);
        }
    }

    @Override
    public List<Imprumuturi> getImprumuturi(Utilizator utilizator){
        return utilizatorRepository.getCartiUtilizator(utilizator);
    }

    @Override
    public void adaugaCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare) throws BibliotecaException{
        boolean add = carteRepository.addCarte(id_c, titlu, autor, editura, an_aparitie, nr_exemplare);
        if(!add){
            throw new BibliotecaException("Cartea nu a fost adaugata");
        }
        else{
            List<Carte> carti = carteRepository.getAllCarti();
            notifyEvent(carti);
        }
    }

    @Override
    public void modificaCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare) throws BibliotecaException{
        boolean modify = carteRepository.modifyCarte(id_c, titlu, autor, editura, an_aparitie, nr_exemplare);
        if(!modify){
            throw new BibliotecaException("Cartea nu a fost modificata");
        }
        else{
            List<Carte> carti = carteRepository.getAllCarti();
            notifyEvent(carti);
        }
    }

    @Override
    public void stergeCarte(int id_c) throws BibliotecaException{
        boolean delete = carteRepository.deleteCarte(id_c);
        if(!delete){
            throw new BibliotecaException("Cartea nu a fost stearsa");
        }
        else{
            List<Carte> carti = carteRepository.getAllCarti();
            notifyEvent(carti);
        }
    }

    @Override
    public void logout(String username) {
        if (loggedUsers.get(username) != null)
            loggedUsers.remove(username);
    }
}
