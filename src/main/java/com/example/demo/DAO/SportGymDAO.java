package com.example.demo.DAO;

import com.example.demo.Model.SportGym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportGymDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public SportGymDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<SportGym> getAllSportGym() throws SQLException {
        List<SportGym> sportGyms = new ArrayList<>();
        String query = "SELECT * FROM sport_gym;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            SportGym tempSportGym = new SportGym();
            tempSportGym.setId(rs.getInt(1));
            tempSportGym.setName(rs.getString(2));
            tempSportGym.setAddress(rs.getString(3));
            tempSportGym.setNumberOfSimulators(rs.getInt(4));
            sportGyms.add(tempSportGym);
        }
        return sportGyms;
    }

    public void saveSportGym(SportGym sportGymForm) throws SQLException {
        Integer id = sportGymForm.getId();
        String name = sportGymForm.getName();
        String address = sportGymForm.getAddress();
        Integer numberOfSimulators = sportGymForm.getNumberOfSimulators();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + "," + numberOfSimulators + " ";
        String query = "INSERT INTO sport_gym" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public SportGym getSportGymById(int id) throws SQLException {
        String query = "SELECT * FROM sport_gym WHERE idsport_gym=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        SportGym sportGym = new SportGym();
        sportGym.setId(rs.getInt(1));
        sportGym.setName(rs.getString(2));
        sportGym.setAddress(rs.getString(3));
        sportGym.setNumberOfSimulators(rs.getInt(4));
        return sportGym;
    }

    public void deleteSportGym(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getSportGymById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM sport_gym WHERE idsport_gym=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateSportGym(SportGym updateSportGym) throws SQLException {
        String query = "UPDATE sport_gym SET name=\'" + updateSportGym.getName() + "\'" +
                " WHERE idsport_gym=" + updateSportGym.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateSportGym.getAddress() +
                "\' WHERE address=\'" + getSportGymById(updateSportGym.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE sport_gym SET address=\'" + updateSportGym.getAddress() + "\'" +
                " WHERE idsport_gym=" + updateSportGym.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sport_gym SET numberOfSimulators=" + updateSportGym.getNumberOfSimulators() +
                " WHERE idsport_gym=" + updateSportGym.getId() + ";";
        stmt.executeUpdate(query);
    }
}
