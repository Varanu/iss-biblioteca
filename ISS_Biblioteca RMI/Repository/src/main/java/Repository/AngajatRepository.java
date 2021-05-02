package Repository;

import Domain.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AngajatRepository implements IAngajatRepository {
    JdbcUtils jdbcUtils;

    public AngajatRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Angajat findAngajatByUserandPass(String username, String password) {
        logger.info("Repo findAngajatByUserandPass");
        Connection con = jdbcUtils.getConnection();
        List<Angajat> tasks = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Angajat")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id_a = result.getInt("id_a");
                    String userid = result.getString("userid");
                    String pass = result.getString("pass");

                    if (username.equals(userid) && password.equals(pass)) {
                        Angajat angajat = new Angajat(id_a, userid, pass);
                        logger.traceExit(angajat);
                        return angajat;
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

    @Override
    public Angajat findAngajatByName(String name) {
        logger.info("Repo findAngajatByName");
        Connection con = jdbcUtils.getConnection();
        List<Angajat> tasks = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Angajat")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id_a = result.getInt("id_a");
                    String userid = result.getString("userid");
                    String pass = result.getString("pass");

                    if (userid.equals(name)) {
                        Angajat angajat = new Angajat(id_a, userid, pass);
                        return angajat;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(tasks);
        return null;
    }

    @Override
    public List<Angajat> findAllAngajat() {
        logger.info("Repo findAllAngajat");
        Connection con = jdbcUtils.getConnection();
        List<Angajat> angajats = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Angajat")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id_a = result.getInt("id_a");
                    String userid = result.getString("userid");
                    String pass = result.getString("pass");
                    Angajat angajat = new Angajat(id_a, userid, pass);
                    angajats.add(angajat);
                }
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        //logger.traceExit(tasks);
        return angajats;
    }
}

