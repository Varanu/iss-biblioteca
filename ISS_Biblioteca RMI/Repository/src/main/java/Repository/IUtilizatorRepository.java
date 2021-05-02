package Repository;

import Domain.*;
import Service.BibliotecaException;

import java.util.List;

public interface IUtilizatorRepository {
    Utilizator findUtilizatorByUserandPass(String username, String password);
    Utilizator registerUtilizator(String username, String parola, String nume, String prenume, String adresa, String cnp);
    List<Imprumuturi> getCartiUtilizator(Utilizator utilizator);
}
