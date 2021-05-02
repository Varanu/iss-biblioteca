package Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Angajat")
public class Angajat implements Serializable {
    @Id
    @Column(name = "id_a")
    private int id_a;

    @Column(name = "userid")
    private String user;

    @Column
    private String pass;

    public Angajat(){

    }

    public Angajat(int id_a, String user, String pass) {
        this.id_a = id_a;
        this.user = user;
        this.pass = pass;
    }

    public int getId_a() {
        return id_a;
    }

    public void setId_a(int id_a) {
        this.id_a = id_a;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "\nDomain.Angajat {" +
                "id = " + id_a +
                ", User = " + user +
                ", Pass = '" + pass + '\'' +
                '}';
    }
}
