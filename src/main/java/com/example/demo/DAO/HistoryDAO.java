package com.example.demo.DAO;

import com.example.demo.Model.History;
import com.example.demo.tableForm.HistoryForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public HistoryDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<History> getAllHistory() throws SQLException {
        List<History> historySportsCompetitions = new ArrayList<>();
        String query = "SELECT * FROM organizes_and_history_sports_competitions;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            History tempHistory = new History();
            tempHistory.setId(rs.getInt(1));
            tempHistory.setNameOrganizer(rs.getString(2));
            tempHistory.setPersonalKey(rs.getInt(3));
            tempHistory.setSportId(rs.getInt(4));
            tempHistory.setDate(rs.getString(5));
            tempHistory.setAddress(rs.getString(6));
            tempHistory.setFirstPlaceClubId(rs.getInt(7));
            tempHistory.setSecondPlaceClubId(rs.getInt(8));
            tempHistory.setThirdPlaceClubId(rs.getInt(9));
            historySportsCompetitions.add(tempHistory);
        }
        return historySportsCompetitions;
    }

    public History getHistoryById(int id) throws SQLException {
        String query = "SELECT * FROM organizes_and_history_sports_competitions WHERE idorganizes_and_history_sports_competitions=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        History tempHistory = new History();
        tempHistory.setId(rs.getInt(1));
        tempHistory.setNameOrganizer(rs.getString(2));
        tempHistory.setPersonalKey(rs.getInt(3));
        tempHistory.setSportId(rs.getInt(4));
        tempHistory.setDate(rs.getString(5));
        tempHistory.setAddress(rs.getString(6));
        tempHistory.setFirstPlaceClubId(rs.getInt(7));
        tempHistory.setSecondPlaceClubId(rs.getInt(8));
        tempHistory.setThirdPlaceClubId(rs.getInt(9));
        return tempHistory;
    }

    public void deleteHistory(int id) throws SQLException {
        String query = "SELECT * FROM organizes_and_history_sports_competitions WHERE idorganizes_and_history_sports_competitions=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        int firstClub = rs.getInt(7);
        int secondClub = rs.getInt(8);
        int thirdClub = rs.getInt(9);
        query = "DELETE FROM organizes_and_history_sports_competitions WHERE idorganizes_and_history_sports_competitions=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 1thWin=1thWin-1" +
                " WHERE idclub=" + firstClub + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 2thWin=2thWin-1" +
                " WHERE idclub=" + secondClub + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 3thWin=3thWin-1" +
                " WHERE idclub=" + thirdClub + ";";
        stmt.executeUpdate(query);

        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation-1" +
                " WHERE clubId=" + firstClub + " and numberOfParticipation!=0;";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation-1" +
                " WHERE clubId=" + secondClub + " and numberOfParticipation!=0;";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation-1" +
                " WHERE clubId=" + thirdClub + " and numberOfParticipation!=0;";
        stmt.executeUpdate(query);
    }

    public void updateHistory(HistoryForm historyForm) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET nameOrganizers=\'" + historyForm.getNameOrganizer() + "\'" +
                " WHERE idorganizes_and_history_sports_competitions=" + historyForm.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET date=\'" + historyForm.getDate() + "\'" +
                " WHERE idorganizes_and_history_sports_competitions=" + historyForm.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + historyForm.getAddress() + "\'" +
                " WHERE idorganizes_and_history_sports_competitions=" + historyForm.getId() + ";";
        stmt.executeUpdate(query);
    }

    public void saveHistory(HistoryForm historyForm) throws SQLException {
        History history = toHistory(historyForm);
        Integer id = history.getId();
        String name = history.getNameOrganizer();
        Integer personalKey = history.getPersonalKey();
        Integer sportId = history.getSportId();
        String date = history.getDate();
        String address = history.getAddress();
        Integer firstPlaceClubId = history.getFirstPlaceClubId();
        Integer secondPlaceClubId = history.getSecondPlaceClubId();
        Integer thirdPlaceClubId = history.getThirdPlaceClubId();
        String values = id + ",\'" + name + "\'," + personalKey + "," + sportId + ",\'"
                + date + "\',\'" + address + "\'," + firstPlaceClubId + "," + secondPlaceClubId + "," + thirdPlaceClubId;
        String query = "INSERT INTO organizes_and_history_sports_competitions" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 1thWin=1thWin+1" +
                " WHERE idclub=" + history.getFirstPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 2thWin=2thWin+1" +
                " WHERE idclub=" + history.getSecondPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 3thWin=3thWin+1" +
                " WHERE idclub=" + history.getThirdPlaceClubId() + ";";
        stmt.executeUpdate(query);

        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation+1" +
                " WHERE clubId=" + history.getFirstPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation+1" +
                " WHERE clubId=" + history.getSecondPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation+1" +
                " WHERE clubId=" + history.getThirdPlaceClubId() + ";";
        stmt.executeUpdate(query);
    }

    public List<HistoryForm> toHistoryForm(List<History> historyList) throws SQLException {
        List<HistoryForm> historyFormList = new ArrayList<>(historyList.size());
        for (int i = 0; i < historyList.size(); i++) {
            History history = historyList.get(i);
            HistoryForm historyForm = new HistoryForm();
            historyForm.setId(history.getId());
            historyForm.setNameOrganizer(history.getNameOrganizer());
            historyForm.setPersonalKey(history.getPersonalKey());
            if (history.getSportId() != 0) {
                String query = "SELECT * FROM sport WHERE idsport=" + history.getSportId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setSport(rs.getString(2));
            } else {
                historyForm.setSport("null");
            }
            historyForm.setDate(history.getDate());
            historyForm.setAddress(history.getAddress());
            if (history.getFirstPlaceClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + history.getFirstPlaceClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setFirstPlaceClubName(rs.getString(2));
            } else
                historyForm.setFirstPlaceClubName("null");
            if (history.getSecondPlaceClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + history.getSecondPlaceClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setSecondPlaceClubName(rs.getString(2));
            } else
                historyForm.setSecondPlaceClubName("null");
            if (history.getThirdPlaceClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + history.getThirdPlaceClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setThirdPlaceClubName(rs.getString(2));
            } else
                historyForm.setThirdPlaceClubName("null");
            historyFormList.add(historyForm);
        }
        return historyFormList;
    }

    public History toHistory(HistoryForm historyForm) throws SQLException {
        History history = new History();
        history.setId(historyForm.getId());
        history.setNameOrganizer(historyForm.getNameOrganizer());
        history.setPersonalKey(historyForm.getPersonalKey());
        String query = "SELECT * FROM sport WHERE name= \'" + historyForm.getSport() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setSportId(rs.getInt(1));
        history.setDate(historyForm.getDate());
        history.setAddress(historyForm.getAddress());
        query = "SELECT * FROM club WHERE name=\'" + historyForm.getFirstPlaceClubName() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setFirstPlaceClubId(rs.getInt(1));
        query = "SELECT * FROM club WHERE name=\'" + historyForm.getSecondPlaceClubName() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setSecondPlaceClubId(rs.getInt(1));
        query = "SELECT * FROM club WHERE name=\'" + historyForm.getThirdPlaceClubName() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setThirdPlaceClubId(rs.getInt(1));
        return history;
    }
}
