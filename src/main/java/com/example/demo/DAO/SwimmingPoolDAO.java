package com.example.demo.DAO;

import com.example.demo.Model.SwimmingPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SwimmingPoolDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public SwimmingPoolDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<SwimmingPool> getAllSwimmingPool() throws SQLException {
        List<SwimmingPool> swimmingPools = new ArrayList<>();
        String query = "SELECT * FROM swimming_pool;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            SwimmingPool tempSwimmingPool = new SwimmingPool();
            tempSwimmingPool.setId(rs.getInt(1));
            tempSwimmingPool.setName(rs.getString(2));
            tempSwimmingPool.setAddress(rs.getString(3));
            tempSwimmingPool.setDepth(rs.getInt(4));
            tempSwimmingPool.setNumberOfTower(rs.getInt(5));
            swimmingPools.add(tempSwimmingPool);
        }
        return swimmingPools;
    }

    public void saveSwimmingPool(SwimmingPool swimmingPoolForm) throws SQLException {
        Integer id = swimmingPoolForm.getId();
        String name = swimmingPoolForm.getName();
        String address = swimmingPoolForm.getAddress();
        Integer depth = swimmingPoolForm.getDepth();
        Integer numberOfTower = swimmingPoolForm.getNumberOfTower();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + "," +
                depth + "," + numberOfTower;
        String query = "INSERT INTO swimming_pool" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public SwimmingPool getSwimmingPoolById(int id) throws SQLException {
        String query = "SELECT * FROM swimming_pool WHERE idswimming_pool=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        SwimmingPool swimmingPool = new SwimmingPool();
        swimmingPool.setId(rs.getInt(1));
        swimmingPool.setName(rs.getString(2));
        swimmingPool.setAddress(rs.getString(3));
        swimmingPool.setDepth(rs.getInt(4));
        swimmingPool.setNumberOfTower(rs.getInt(5));
        return swimmingPool;
    }

    public void deleteSwimmingPool(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getSwimmingPoolById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM swimming_pool WHERE idswimming_pool=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateSwimmingPool(SwimmingPool updateSwimmingPool) throws SQLException {
        String query = "UPDATE swimming_pool SET name=\'" + updateSwimmingPool.getName() + "\'" +
                " WHERE idswimming_pool=" + updateSwimmingPool.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateSwimmingPool.getAddress() +
                "\' WHERE address=\'" + getSwimmingPoolById(updateSwimmingPool.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE swimming_pool SET address=\'" + updateSwimmingPool.getAddress() + "\'" +
                " WHERE idswimming_pool=" + updateSwimmingPool.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE swimming_pool SET depth=" + updateSwimmingPool.getDepth() +
                " WHERE idswimming_pool=" + updateSwimmingPool.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE swimming_pool SET numberOfTower=" + updateSwimmingPool.getNumberOfTower() +
                " WHERE idswimming_pool=" + updateSwimmingPool.getId() + ";";
        stmt.executeUpdate(query);
    }
}
