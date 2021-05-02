package Domain;

import java.io.Serializable;
import java.util.List;

public class Imprumuturi implements Serializable {
    private int nr_exemplare;
    private String data_imprumut;
    private String data_restituire;
    private int id_u;
    private int id_c;
    private Carte carte;

    public Imprumuturi(int nr_exemplare, String data_imprumut, String data_restituire, int id_u, int id_c) {
        this.nr_exemplare = nr_exemplare;
        this.data_imprumut = data_imprumut;
        this.data_restituire = data_restituire;
        this.id_u = id_u;
        this.id_c = id_c;
    }

    public Imprumuturi(int nr_exemplare, String data_imprumut, String data_restituire, Carte carte) {
        this.nr_exemplare = nr_exemplare;
        this.data_imprumut = data_imprumut;
        this.data_restituire = data_restituire;
        this.carte = carte;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public int getNr_exemplare() {
        return nr_exemplare;
    }

    public void setNr_exemplare(int nr_exemplare) {
        this.nr_exemplare = nr_exemplare;
    }

    public String getData_imprumut() {
        return data_imprumut;
    }

    public void setData_imprumut(String data_imprumut) {
        this.data_imprumut = data_imprumut;
    }

    public String getData_restituire() {
        return data_restituire;
    }

    public void setData_restituire(String data_restituire) {
        this.data_restituire = data_restituire;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    @Override
    public String toString() {
        return "\nImprumuturi{" +
                "nr_exemplare=" + nr_exemplare +
                ", data_imprumut='" + data_imprumut + '\'' +
                ", data_restituire='" + data_restituire + '\'' +
                ", id_u=" + id_u +
                ", id_c=" + id_c +
                '}';
    }
}
