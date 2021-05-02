package Repository;

import Domain.Carte;
import Domain.Utilizator;

import java.util.List;

public interface ICarteRepository {
    boolean addCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare);
    boolean modifyCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare);
    boolean deleteCarte(int id_c);
    List<Carte> getAllCarti();
    boolean imprumutaCarte(int id_u, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare, int ex_imprumutate);
    boolean restituieCarte(int id_u, String titlu, String autor, String data_imprumut, String data_restituire, int nr_exemplare, int ex_restituite);
}
