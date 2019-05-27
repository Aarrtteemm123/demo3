package com.example.demo.DAO;

import com.example.demo.Model.Manege;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManegeDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public ManegeDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<Manege> getAllManege() throws SQLException {
        List<Manege> maneges = new ArrayList<>();
        String query = "SELECT * FROM manege;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Manege tempManege = new Manege();
            tempManege.setId(rs.getInt(1));
            tempManege.setName(rs.getString(2));
            tempManege.setAddress(rs.getString(3));
            tempManege.setTypeOfCover(rs.getString(4));
            maneges.add(tempManege);
        }
        return maneges;
    }

    public void saveManege(Manege manegeForm) throws SQLException {
        Integer id = manegeForm.getId();
        String name = manegeForm.getName();
        String address = manegeForm.getAddress();
        String type = manegeForm.getTypeOfCover();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + ",\'" + type + "\'";
        String query = "INSERT INTO manege" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Manege getManegeById(int id) throws SQLException {
        String query = "SELECT * FROM manege WHERE idmanege=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        Manege manege = new Manege();
        manege.setId(rs.getInt(1));
        manege.setName(rs.getString(2));
        manege.setAddress(rs.getString(3));
        manege.setTypeOfCover(rs.getString(4));
        return manege;
    }

    public void deleteManege(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getManegeById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM manege WHERE idmanege=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateManege(Manege updatedManege) throws SQLException {
        String query = "UPDATE manege SET name=\'" + updatedManege.getName() + "\'" +
                " WHERE idmanege=" + updatedManege.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updatedManege.getAddress() +
                "\' WHERE address=\'" + getManegeById(updatedManege.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE manege SET address=\'" + updatedManege.getAddress() + "\'" +
                " WHERE idmanege=" + updatedManege.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE manege SET typeOfCover=\'" + updatedManege.getTypeOfCover() + "\'" +
                " WHERE idmanege=" + updatedManege.getId() + ";";
        stmt.executeUpdate(query);
    }
}
