package Domain;

import java.io.Serializable;
import java.util.List;

public class Utilizator implements Serializable {
    private int id_u;
    private String username;
    private String parola;
    private String nume;
    private String prenume;
    private String adresa;
    private String cnp;
    private List<Carte> carti;

    public Utilizator(int id_u, String username, String parola, String nume, String prenume, String adresa, String cnp) {
        this.id_u = id_u;
        this.username = username;
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.cnp = cnp;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public List<Carte> getCarti() {
        return carti;
    }

    public void setCarti(List<Carte> carti) {
        this.carti = carti;
    }

    @Override
    public String toString() {
        return "\nUtilizator{" +
                "id_u=" + id_u +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", cnp='" + cnp + '\'' +
                ", carti=" + carti +
                '}';
    }
}

