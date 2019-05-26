package com.example.demo.DAO;

import com.example.demo.Controller.TableController;
import com.example.demo.Model.Sport;
import com.example.demo.Model.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public SportDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<Sport> getAllSport() throws SQLException {
        List<Sport> sports = new ArrayList<>();
        String query = "SELECT * FROM sport;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Sport tempSport = new Sport();
            tempSport.setId(rs.getInt(1));
            tempSport.setName(rs.getString(2));
            sports.add(tempSport);
        }
        return sports;
    }

    public void saveSport(Sport sportForm) throws SQLException {
        Integer id = sportForm.getId();
        String name = sportForm.getName();
        String values = id + ",\'" + name + "\'";
        String query = "INSERT INTO sport" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Sport getSportById(int id) throws SQLException {
        String query = "SELECT * FROM sport WHERE idsport=" + id + ";";
        rs = stmt.executeQuery(query);
        TableController tableController=new TableController(url,user,password);
        if (tableController.checkSize(rs))
        {
            rs.beforeFirst();
            rs.next();
            Sport sport=new Sport();
            sport.setId(rs.getInt(1));
            sport.setName(rs.getString(2));
            return sport;
        }
        return new Sport();
    }

    public void deleteSport(int id) throws SQLException {
        String query = "UPDATE trainer SET sportId=0" +
                " WHERE sportId=" + id+ ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET sportId=0" +
                " WHERE sportId=" + id+ ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET sportId=0" +
                " WHERE sportId=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET sportId=0" +
                " WHERE sportId=" + id + ";";
        stmt.executeUpdate(query);
        query = "DELETE FROM sport WHERE idsport=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateSport(Sport updateSport) throws SQLException {
        String query = "UPDATE sport SET name=\'" + updateSport.getName() + "\'" +
                " WHERE idsport=" + updateSport.getId() + ";";
        stmt.executeUpdate(query);
    }
}
