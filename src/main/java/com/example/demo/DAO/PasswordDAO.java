package com.example.demo.DAO;

import java.sql.*;

public class PasswordDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public PasswordDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public Boolean readPassword(String name,String password) throws SQLException {
        if (!(name.equals("admin"))&&!(name.equals("user")))
            return null;
        String query = "SELECT * FROM access WHERE name=\'"+name+"\';";
        rs = stmt.executeQuery(query);
        rs.next();
        if (rs.getString(3).equals(password))
            return true;
        return false;
    }

}
