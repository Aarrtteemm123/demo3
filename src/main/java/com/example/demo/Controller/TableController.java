package com.example.demo.Controller;

import com.example.demo.DAO.ClubDAO;
import com.example.demo.DAO.SportDAO;
import com.example.demo.DAO.TrainerDAO;
import com.example.demo.Model.Club;
import com.example.demo.Model.Sport;
import com.example.demo.Model.Trainer;
import com.example.demo.tableForm.ClubForm;
import com.example.demo.tableForm.HistoryForm;
import com.example.demo.tableForm.SportsmenForm;

import java.sql.*;

public class TableController {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public TableController(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public boolean checkSaveDataInTableClub(ClubForm clubForm) throws SQLException {
        boolean flId, flName;
        flId = checkIdInTable("club", clubForm.getId());
        flName = checkNameInTable("club", clubForm.getName());
        return flId && flName;
    }

    public boolean checkDateToPattern(String date) {
        return date.matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$");
    }

    public boolean checkIdInTable(String nameTable, int id) throws SQLException {
        String query = "SELECT * FROM " + nameTable + ";";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (id <= 0 || rs.getInt(1) == id)
                return false;
        }
        return true;
    }

    public boolean checkAddressInTable(String nameTable, String address) throws SQLException {
        String query = "SELECT * FROM " + nameTable + ";";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (rs.getString(3).equals(address))
                return false;
        }
        return true;
    }

    public boolean checkNameInTable(String nameTable, String name) throws SQLException {
        String query = "SELECT * FROM " + nameTable + ";";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (rs.getString(2).equals(name))
                return false;
        }
        return true;
    }

    public boolean checkSaveDataInTableHistory(HistoryForm historyForm) throws SQLException {
        boolean flId, flSport, flDate, flAddress, flFirstClub, flSecondClub, flThirdClub, flPersonalKey;
        flId = checkIdInTable("organizes_and_history_sports_competitions", historyForm.getId());
        flSport = checkSport(historyForm.getSport());
        flDate = checkDateToPattern(historyForm.getDate());
        flAddress = checkAddressInAllTable(historyForm.getAddress());
        flFirstClub = checkClub(historyForm.getFirstPlaceClubName());
        flSecondClub = checkClub(historyForm.getSecondPlaceClubName());
        flThirdClub = checkClub(historyForm.getThirdPlaceClubName());
        flPersonalKey = checkPersonalKey("organizes_and_history_sports_competitions", historyForm.getNameOrganizer(), historyForm.getPersonalKey());
        return flId && flSport && flDate && flAddress && flFirstClub && flSecondClub && flThirdClub && flPersonalKey;
    }

    public boolean checkAddressInAllTable(String address) throws SQLException {
        String[] tables = {"board_games", "court", "ice_rink", "manege", "sport_gym"
                , "stadium", "swimming_pool", "track"};
        for (int i = 0; i < tables.length; i++) {
            String query = "SELECT * FROM " + tables[i] + " WHERE address=\'" + address + "\';";
            rs = stmt.executeQuery(query);
            if (checkSize(rs))
                return true;
        }
        return false;
    }

    public boolean checkUpdateAddress(String table, String address, int id) throws SQLException {
        String query = "SELECT * FROM " + table + ";";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (rs.getString(3).equals(address) && rs.getInt(1) != id)
                return false;
        }
        return true;
    }

    public boolean checkSportById(int id) throws SQLException {
        String query = "SELECT * FROM sport" + " WHERE idsport=" + id + ";";
        rs = stmt.executeQuery(query);
        return checkSize(rs);
    }

    public boolean checkPersonalKey(String nameTable, String name, int key) throws SQLException {
        String query = "SELECT * FROM " + nameTable + " WHERE personalKey=" + key + ";";
        rs = stmt.executeQuery(query);
        if (checkSize(rs)) {
            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getString(2).equals(name))
                    return true;
                return false;
            }
        }
        return true;
    }


    public boolean checkSaveDataInTableSportsmen(SportsmenForm sportsmenForm) throws SQLException {
        boolean flId, flSportCategory, flSport, flTrainerId, flNameClub, flSCT, flPersonalKey;
        flId = checkIdInTable("sportsmen", sportsmenForm.getId());
        flSportCategory = checkSportCategory(sportsmenForm);
        flSport = checkSport(sportsmenForm.getSport());
        flNameClub = checkClub(sportsmenForm.getClubName());
        flTrainerId = checkTrainerById(sportsmenForm);
        flPersonalKey = checkPersonalKey("sportsmen", sportsmenForm.getName(), sportsmenForm.getPersonalKey());

        ClubDAO clubDAO = new ClubDAO(url, user, password);
        SportDAO sportDAO = new SportDAO(url, user, password);
        TrainerDAO trainerDAO = new TrainerDAO(url, user, password);
        Club club = clubDAO.getClubByName(sportsmenForm.getClubName());
        Trainer trainer = trainerDAO.getTrainerById(sportsmenForm.getTrainerId());
        Sport sport = sportDAO.getSportById(club.getSportId());
        flSCT = sport.getId() == club.getSportId() && (club.getSportId() == trainer.getSportId()) && sport.getName() != null && sport.getName().equals(sportsmenForm.getSport());
        return flId && flSportCategory && flSport && flNameClub && flTrainerId && flSCT && flPersonalKey;
    }

    public boolean checkSportCategory(SportsmenForm sportsmenForm) {
        int result = sportsmenForm.getIntSportCategory();
        return result != 0;
    }

    public boolean checkSport(String name) throws SQLException {
        String query = "SELECT * FROM sport;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (rs.getString(2).equals(name))
                return true;
        }
        return false;
    }

    public boolean checkClub(String name) throws SQLException {
        String query = "SELECT * FROM club;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (rs.getString(2).equals(name))
                return true;
        }
        return false;
    }

    public boolean checkTrainerById(SportsmenForm sportsmenForm) throws SQLException {
        String query = "SELECT * FROM trainer WHERE idtrainer=" + sportsmenForm.getTrainerId() + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        if (checkSize(rs)) {
            rs.beforeFirst();
            rs.next();
            return rs.getString(2).equals(sportsmenForm.getTrainerName());
        }
        return false;
    }

    public boolean checkSize(ResultSet resultSet) throws SQLException {
        int size = 0;
        if (resultSet != null) {
            resultSet.last();    // moves cursor to the last row
            size = resultSet.getRow(); // get row id
        }
        return size != 0;
    }

}
