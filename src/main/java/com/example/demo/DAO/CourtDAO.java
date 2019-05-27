package com.example.demo.DAO;

import com.example.demo.Model.Court;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourtDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public CourtDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<Court> getAllCourt() throws SQLException {
        List<Court> courts = new ArrayList<>();
        String query = "SELECT * FROM court;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Court tempCourt = new Court();
            tempCourt.setId(rs.getInt(1));
            tempCourt.setName(rs.getString(2));
            tempCourt.setAddress(rs.getString(3));
            tempCourt.setNumberOfPlayground(rs.getInt(4));
            courts.add(tempCourt);
        }
        return courts;
    }

    public void saveCourt(Court courtForm) throws SQLException {
        Integer id = courtForm.getId();
        String name = courtForm.getName();
        String address = courtForm.getAddress();
        Integer numberOfPlayground = courtForm.getNumberOfPlayground();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + "," + numberOfPlayground;
        String query = "INSERT INTO court" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Court getCourtById(int id) throws SQLException {
        String query = "SELECT * FROM court WHERE idcourt=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        Court court = new Court();
        court.setId(rs.getInt(1));
        court.setName(rs.getString(2));
        court.setAddress(rs.getString(3));
        court.setNumberOfPlayground(rs.getInt(4));
        return court;
    }

    public void deleteCourt(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getCourtById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM court WHERE idcourt=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateCourt(Court updateCourt) throws SQLException {
        String query = "UPDATE court SET name=\'" + updateCourt.getName() + "\'" +
                " WHERE idcourt=" + updateCourt.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateCourt.getAddress() +
                "\' WHERE address=\'" + getCourtById(updateCourt.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE court SET address=\'" + updateCourt.getAddress() + "\'" +
                " WHERE idcourt=" + updateCourt.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE court SET numberOfPlayground=" + updateCourt.getNumberOfPlayground() +
                " WHERE idcourt=" + updateCourt.getId() + ";";
        stmt.executeUpdate(query);
    }
}
