package Repository;

import Domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarteRepository implements ICarteRepository {
    private JdbcUtils jdbcUtils;

    private static final Logger Logger = LogManager.getLogger();
    public CarteRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }

    private boolean cautaCarte(int id_c, String titlu, String autor, String editura, int an_aparitie){
        List<Carte> carti = getAllCarti();
        for (Carte carte : carti) {
            if(carte.getId_c() == id_c){
                return true;
            }
            else{
                if(carte.getTitlu().equals(titlu) && carte.getAutor().equals(autor) && carte.getEditura().equals(editura) && carte.getAn_aparitie() == an_aparitie){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare){
        Logger.info("Repo addCarte");
        Connection con = jdbcUtils.getConnection();
        if(!cautaCarte(id_c, titlu, autor, editura, an_aparitie)) {
            try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Carte(id_c, titlu, autor, editura, an_aparitie, nr_exemplare) VALUES(?,?,?,?,?,?)")) {
                preStmt.setInt(1, id_c);
                preStmt.setString(2, titlu);
                preStmt.setString(3, autor);
                preStmt.setString(4, editura);
                preStmt.setInt(5, an_aparitie);
                preStmt.setInt(6, nr_exemplare);
                con.setAutoCommit(false);
                int result = preStmt.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
                Logger.traceExit("successfully added Carte");
                return true;
            } catch (SQLException e) {
                Logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        Logger.traceExit("failed to add Carte");
        return false;

    }

    @Override
    public boolean modifyCarte(int id_c, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare){
        Logger.info("Repo modifyCarte");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("UPDATE Carte SET titlu = ?, autor = ?, editura = ?, an_aparitie = ?, nr_exemplare = ? WHERE id_c = ?")) {
            preStmt.setString(1, titlu);
            preStmt.setString(2, autor);
            preStmt.setString(3, editura);
            preStmt.setInt(4, an_aparitie);
            preStmt.setInt(5, nr_exemplare);
            preStmt.setInt(6, id_c);
            con.setAutoCommit(false);
            int result = preStmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            Logger.traceExit("successfully modified Carte");
            return true;
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("failed to modify Carte");
        return false;
    }

    @Override
    public boolean deleteCarte(int id_c){
        Logger.info("Repo deleteCarte");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("DELETE FROM Carte WHERE id_c = ?")) {
            preStmt.setInt(1, id_c);
            con.setAutoCommit(false);
            int result = preStmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            Logger.traceExit("successfully deleted Carte");
            return true;
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("failed to delete Carte");
        return false;
    }

    @Override
    public List<Carte> getAllCarti(){
        Logger.info("Repo getAllCarti");
        List<Carte> carti = new ArrayList<>();
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Carte")) {
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()) {
                    int id_c = result.getInt("id_c"), an_aparitie = result.getInt("an_aparitie"), nr_exemplare = result.getInt("nr_exemplare");
                    String titlu = result.getString("titlu"), autor = result.getString("autor"), editura = result.getString("editura");
                    Carte ca = new Carte(id_c, titlu, autor, editura, an_aparitie, nr_exemplare);
                    carti.add(ca);
                }
            }
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }

        Logger.traceExit(jdbcUtils);
        return carti;
    }

    private void updateCarte(Carte carte, int ex_imprumutate){
        Logger.info("Repo updateCarte");
        Connection con = jdbcUtils.getConnection();
        int nou = carte.getNr_exemplare() - ex_imprumutate;
        try(PreparedStatement preStmt = con.prepareStatement("UPDATE Carte SET nr_exemplare = ? WHERE id_c = ?")) {
            preStmt.setInt(1, nou);
            preStmt.setInt(2, carte.getId_c());
            con.setAutoCommit(false);
            int result = preStmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("updateCarte");
    }

    private void updateCarteImprumut(int id_c, int ex_restituite){
        Logger.info("Repo updateCarteImprumut");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("UPDATE Carte SET nr_exemplare = nr_exemplare + ? WHERE id_c = ?")) {
            preStmt.setInt(1, ex_restituite);
            preStmt.setInt(2, id_c);
            con.setAutoCommit(false);
            int result = preStmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("updateCarteImprumut");
    }

    private Carte getCarteID(String titlu, String autor, String editura, int an_aparitie, int nr_exemplare) {
        Logger.info("Repo getCarteID");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT id_c FROM Carte WHERE titlu = ? AND autor = ? AND editura = ? AND an_aparitie = ? AND nr_exemplare = ?")) {
            preStmt.setString(1, titlu);
            preStmt.setString(2, autor);
            preStmt.setString(3, editura);
            preStmt.setInt(4, an_aparitie);
            preStmt.setInt(5, nr_exemplare);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()) {
                    int id_c = result.getInt("id_c");
                    Carte c = new Carte(id_c, titlu, autor, editura, an_aparitie, nr_exemplare);
                    return c;
                }
            }
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }

        Logger.traceExit("Carte deja imprumutata");
        return null;
    }

    private int getCarteIDFromImprumuturi(int id_u, String sql_imprumut, String sql_restituire, int nr_exemplare){
        Logger.info("Repo getCarteIDFromImprumuturi");
        Connection con = jdbcUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("SELECT id_c FROM Imprumuturi WHERE id_u = ? AND data_imprumut = ? AND data_restituire = ? AND nr_exemplare = ?")) {
            preStmt.setInt(1, id_u);
            preStmt.setString(2, sql_imprumut);
            preStmt.setString(3, sql_restituire);
            preStmt.setInt(4, nr_exemplare);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()) {
                    return result.getInt("id_c");
                }
            }
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("Cartea nu exista");
        return 0;
    }

    private java.sql.Date getDataImprumut(int id_u, int id_c){
        Logger.info("Repo getCarteID");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT data_imprumut FROM Imprumuturi WHERE id_u = ? AND id_c = ?")) {
            preStmt.setInt(1, id_u);
            preStmt.setInt(2, id_c);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()) {
                    return result.getDate("data_imprumut");
                }
            }
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }

        Logger.traceExit("Cartea nu este imprumutata");
        return null;
    }

    private void updateImprumuturiNrExemplare(int id_u, int id_c, int ex_imprumutate){
        Logger.info("Repo updateImprumuturiNrExemplare");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("UPDATE Imprumuturi SET nr_exemplare = nr_exemplare + ? WHERE id_u = ? AND id_c = ?")) {
            preStmt.setInt(1, ex_imprumutate);
            preStmt.setInt(2, id_u);
            preStmt.setInt(3, id_c);
            con.setAutoCommit(false);
            int result = preStmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("updateImprumuturiNrExemplare");
    }

    private void updateRestituiriNrExemplare(int id_u, int id_c, int ex_restuite){
        Logger.info("Repo updateRestituiriNrExemplare");
        Connection con = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("UPDATE Imprumuturi SET nr_exemplare = nr_exemplare - ? WHERE id_u = ? AND id_c = ?")) {
            preStmt.setInt(1, ex_restuite);
            preStmt.setInt(2, id_u);
            preStmt.setInt(3, id_c);
            con.setAutoCommit(false);
            int result = preStmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.error(e);
            System.out.println("Error DB " + e);
        }
        Logger.traceExit("updateRestituiriNrExemplare");
    }


    @Override
    public boolean imprumutaCarte(int id_u, String titlu, String autor, String editura, int an_aparitie, int nr_exemplare, int ex_imprumutate){
        Logger.info("Repo imprumutaCarte");

        LocalDateTime data_imprum =  LocalDateTime.now();
        ZonedDateTime zonedUTC = data_imprum.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Europe/Bucharest"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formatedData_imprum = zonedIST.format(formatter);

        ZonedDateTime data_restitu = zonedIST.plusDays(10);
        String formatedData_restitu = data_restitu.format(formatter);

        java.sql.Date sql_data_imprum = java.sql.Date.valueOf(formatedData_imprum);
        java.sql.Date sql_data_restitu = java.sql.Date.valueOf(formatedData_restitu);

        Carte carte = getCarteID(titlu, autor, editura, an_aparitie, nr_exemplare);
        java.sql.Date getDataImpr = getDataImprumut(id_u, carte.getId_c());
        Connection con = jdbcUtils.getConnection();
        if(ex_imprumutate <= nr_exemplare) {
            if (getDataImpr == null || getDataImpr.compareTo(sql_data_imprum) > 0) {
                try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Imprumuturi(nr_exemplare, data_imprumut, data_restituire, id_u, id_c) VALUES (?,?,?,?,?)")) {
                    preStmt.setInt(1, ex_imprumutate);
                    preStmt.setDate(2, sql_data_imprum);
                    preStmt.setDate(3, sql_data_restitu);
                    preStmt.setInt(4, id_u);
                    preStmt.setInt(5, carte.getId_c());
                    con.setAutoCommit(false);
                    int res = preStmt.executeUpdate();
                    con.commit();
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    Logger.error(e);
                    System.out.println("Error DB " + e);
                }
            } else {
                updateImprumuturiNrExemplare(id_u, carte.getId_c(), ex_imprumutate);
            }
            updateCarte(carte, ex_imprumutate);
            return true;
        }
        Logger.traceExit("imprumutaCarte");
        return false;
    }

    @Override
    public boolean restituieCarte(int id_u, String titlu, String autor, String data_imprumut, String data_restituire, int nr_exemplare, int ex_restituite) {
        Logger.info("Repo restituieCarte");
        Connection con = jdbcUtils.getConnection();

        int id_c = getCarteIDFromImprumuturi(id_u, data_imprumut, data_restituire, nr_exemplare);
        if(ex_restituite <= nr_exemplare) {
            if (nr_exemplare == 1 || ex_restituite == nr_exemplare) {
                try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Imprumuturi WHERE id_u = ? AND data_imprumut = ? AND data_restituire = ? AND nr_exemplare = ? AND id_c = ?")) {
                    preStmt.setInt(1, id_u);
                    preStmt.setString(2, data_imprumut);
                    preStmt.setString(3, data_restituire);
                    preStmt.setInt(4, nr_exemplare);
                    preStmt.setInt(5, id_c);
                    con.setAutoCommit(false);
                    int result = preStmt.executeUpdate();
                    con.commit();
                    con.setAutoCommit(true);
                    Logger.traceExit("successfully deleted imprumut");
                } catch (SQLException e) {
                    Logger.error(e);
                    System.out.println("Error DB " + e);
                    return false;
                }
            } else {
                updateRestituiriNrExemplare(id_u, id_c, ex_restituite);
            }
            updateCarteImprumut(id_c, ex_restituite);
        }
        Logger.traceExit("restituieCarte");
        return true;
    }
}
