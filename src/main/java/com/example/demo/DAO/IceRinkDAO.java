package com.example.demo.DAO;

import com.example.demo.Model.IceRink;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IceRinkDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public IceRinkDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<IceRink> getAllIceRink() throws SQLException {
        List<IceRink> iceRinks = new ArrayList<>();
        String query = "SELECT * FROM ice_rink;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            IceRink tempIceRink = new IceRink();
            tempIceRink.setId(rs.getInt(1));
            tempIceRink.setName(rs.getString(2));
            tempIceRink.setAddress(rs.getString(3));
            tempIceRink.setSquare(rs.getInt(4));
            iceRinks.add(tempIceRink);
        }
        return iceRinks;
    }

    public void saveIceRink(IceRink iceRinkForm) throws SQLException {
        Integer id = iceRinkForm.getId();
        String name = iceRinkForm.getName();
        String address = iceRinkForm.getAddress();
        Integer square = iceRinkForm.getSquare();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'," + square;
        String query = "INSERT INTO ice_rink" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public  IceRink getIceRinkById(int id) throws SQLException {
        String query = "SELECT * FROM ice_rink WHERE idice_rink=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        IceRink iceRink=new IceRink();
        iceRink.setId(rs.getInt(1));
        iceRink.setName(rs.getString(2));
        iceRink.setAddress(rs.getString(3));
        iceRink.setSquare(rs.getInt(4));
        return iceRink;
    }

    public void deleteIceRink(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'"+
                " WHERE address=\'" + getIceRinkById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM ice_rink WHERE idice_rink=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateIceRink(IceRink updateIceRink) throws SQLException {
        String query = "UPDATE ice_rink SET name=\'" + updateIceRink .getName() + "\'" +
                " WHERE idice_rink=" + updateIceRink .getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateIceRink.getAddress()  +
                "\' WHERE address=\'" + getIceRinkById(updateIceRink.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE ice_rink SET address=\'" + updateIceRink .getAddress() + "\'" +
                " WHERE idice_rink=" + updateIceRink .getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE ice_rink SET square=" + updateIceRink.getSquare()  +
                " WHERE idice_rink=" + updateIceRink .getId() + ";";
        stmt.executeUpdate(query);
    }
}
