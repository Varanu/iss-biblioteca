package Domain;

import java.io.Serializable;

public class Carte implements Serializable {
    private int id_c;
    private String titlu;
    private String autor;
    private String editura;
    private int an_aparitie;
    private int nr_exemplare;


    public Carte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare) {
        this.id_c = id_c;
        this.titlu = titlu;
        this.autor = autor;
        this.editura = editura;
        this.an_aparitie = an_aparitie;
        this.nr_exemplare = nr_exemplare;
    }

    public Carte(String titlu, String autor) {
        this.titlu = titlu;
        this.autor = autor;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public int getAn_aparitie() {
        return an_aparitie;
    }

    public void setAn_aparitie(int an_aparitie) {
        this.an_aparitie = an_aparitie;
    }

    public int getNr_exemplare() {
        return nr_exemplare;
    }

    public void setNr_exemplare(int nr_exemplare) {
        this.nr_exemplare = nr_exemplare;
    }

    @Override
    public String toString() {
        return "\nCarte{" +
                "titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", editura='" + editura + '\'' +
                ", an_aparitie=" + an_aparitie +
                ", nr_exemplare=" + nr_exemplare +
                '}';
    }
}
