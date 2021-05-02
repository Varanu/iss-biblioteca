package Repository;

import Domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UtilizatorRepository implements IUtilizatorRepository{
    private JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();
    public UtilizatorRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }


    private boolean isAngajat(String username) {
        logger.info("Repo isAngajat");
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Angajat")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id_a = result.getInt("id_a");
                    String userid = result.getString("userid");
                    String pass = result.getString("pass");

                    if (userid.equals(username)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
        return false;
    }

    @Override
    public Utilizator findUtilizatorByUserandPass(String username, String password) {
        logger.info("Repo findUtilizatorByUserandPass");
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Utilizator")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id_u = result.getInt("id_u");
                    String usr = result.getString("username"),
                            pass = result.getString("parola"),
                            nume = result.getString("nume"),
                            prenume = result.getString("prenume"),
                            adresa = result.getString("adresa"),
                            cnp = result.getString("cnp");
                    if (username.equals(usr) && password.equals(pass)) {
                        logger.traceExit();
                        return new Utilizator(id_u, usr, pass, nume, prenume, adresa, cnp);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
        return null;
    }

    private boolean validateUtilizator(String username) {
        logger.info("Repo validateUtilizator");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Utilizator")) {
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()) {
                    String usrname = result.getString("username");
                    if(usrname.equals(username))
                        return true;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(jdbcUtils);
        return false;
    }

    private int getSizeUtilizatori() {
        logger.info("Repo getSizeUtilizatori");
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT COUNT(*) AS SIZE FROM Utilizator")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(jdbcUtils);
        return 0;
    }

    @Override
    public Utilizator registerUtilizator(String username, String parola, String nume, String prenume, String adresa, String cnp){
        if(!validateUtilizator(username) && !isAngajat(username)) {
            Connection con = jdbcUtils.getConnection();
            logger.info("Repo registerUtilizator");
            int id_u = getSizeUtilizatori() + 1;
            Utilizator utilizator = new Utilizator(id_u, username, parola, nume, prenume, adresa, cnp);
            try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Utilizator(id_u, username, parola, nume, prenume, adresa, cnp) VALUES (?,?,?,?,?,?,?)")) {
                preStmt.setInt(1, id_u);
                preStmt.setString(2, username);
                preStmt.setString(3, parola);
                preStmt.setString(4, nume);
                preStmt.setString(5, prenume);
                preStmt.setString(6, adresa);
                preStmt.setString(7, cnp);
                con.setAutoCommit(false);
                int result = preStmt.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
                return utilizator;
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        logger.traceExit("registerUtilizator returned null -> utilizator existent pentru username: " + username);
        return null;
    }

    @Override
    public List<Imprumuturi> getCartiUtilizator(Utilizator utilizator) {
        logger.info("Repo getCartiUtilizator");
        Connection con = jdbcUtils.getConnection();
        List<Imprumuturi> imprumuturi = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT C.titlu, C.autor, I.data_imprumut, I.data_restituire, I.nr_exemplare FROM Imprumuturi I INNER JOIN Carte C ON I.id_c = C.id_c WHERE id_u = ?")) {
            preStmt.setInt(1, utilizator.getId_u());
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int nr_exemplare = result.getInt("nr_exemplare");
                    String titlu = result.getString("titlu"),
                            autor = result.getString("autor");
                    Date data_imprumut = result.getDate("data_imprumut"),
                            data_restituire = result.getDate("data_restituire");
                    Carte c = new Carte(titlu, autor);
                    Imprumuturi impr = new Imprumuturi(nr_exemplare, data_imprumut.toString(), data_restituire.toString(), c);
                    imprumuturi.add(impr);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(jdbcUtils);
        return imprumuturi;
    }

}
