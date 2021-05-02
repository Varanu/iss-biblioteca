package Repository;

import Domain.Angajat;

import java.util.List;

public interface IAngajatRepository  {
    Angajat findAngajatByUserandPass(String username, String password);
    Angajat findAngajatByName(String name);
    List<Angajat> findAllAngajat();
}
