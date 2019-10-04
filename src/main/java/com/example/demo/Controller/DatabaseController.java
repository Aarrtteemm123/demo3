package com.example.demo.Controller;

import com.example.demo.Model.Stadium;
import com.example.demo.TableForm.StadiumForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public DatabaseController(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public String readPassword() throws SQLException {
        String query = "SELECT * FROM admin;";
        rs = stmt.executeQuery(query);
        rs.next();
       return rs.getString(2);
    }

    public List<Stadium>readTableStadium() throws SQLException {
        List<Stadium>stadiums=new ArrayList<>();
        String query = "SELECT * FROM stadium;";
        rs = stmt.executeQuery(query);
        while (rs.next())
        {
            Stadium tempStadium=new Stadium();
            tempStadium.setId(rs.getInt(1));
            tempStadium.setName(rs.getString(2));
            tempStadium.setAddress(rs.getString(3));
            tempStadium.setNumberOfViewers(rs.getInt(4));
            tempStadium.setType(rs.getString(5));
            stadiums.add(tempStadium);
        }
        return stadiums;
    }

    public void saveStadium(StadiumForm stadiumForm) throws SQLException {
        Integer id = stadiumForm.getId();
        String name = stadiumForm.getName();
        String address = stadiumForm.getAddress();
        Integer numberOfViewers = stadiumForm.getNumberOfViewers();
        String type = stadiumForm.getType();
        String values=id+",\'"+name+"\'"+",\'"+address+"\'"+
                ","+numberOfViewers+",\'"+type+"\'";
        String query = "INSERT INTO stadium" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Stadium editStadium(int id) throws SQLException {
        String query = "SELECT * FROM stadium WHERE idstadium="+id+";";
        rs = stmt.executeQuery(query);
        rs.next();
        Stadium stadium=new Stadium();
        stadium.setId(rs.getInt(1));
        stadium.setName(rs.getString(2));
        stadium.setAddress(rs.getString(3));
        stadium.setNumberOfViewers(rs.getInt(4));
        stadium.setType(rs.getString(5));
        return stadium;
    }

    public void deleteStadium(int id) throws SQLException {
        String query = "DELETE FROM stadium WHERE idstadium="+id+";";
        stmt.executeUpdate(query);
    }

    public void saveEditStadium(Stadium updatedStadium) throws SQLException {
        String query="UPDATE stadium SET name=\'"+updatedStadium.getName()+"\'"+
                " WHERE idstadium="+updatedStadium.getId()+";";
        stmt.executeUpdate(query);
        query="UPDATE stadium SET address=\'"+updatedStadium.getAddress()+"\'"+
                " WHERE idstadium="+updatedStadium.getId()+";";
        stmt.executeUpdate(query);
        query="UPDATE stadium SET numberOfViewers="+updatedStadium.getNumberOfViewers()+
                " WHERE idstadium="+updatedStadium.getId()+";";
        stmt.executeUpdate(query);
        query="UPDATE stadium SET type=\'"+updatedStadium.getType()+"\'"+
                " WHERE idstadium="+updatedStadium.getId()+";";
        stmt.executeUpdate(query);
    }

}
