package com.example.demo.DAO;

import com.example.demo.Model.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public TrackDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<Track> getAllTrack() throws SQLException {
        List<Track> tracks = new ArrayList<>();
        String query = "SELECT * FROM track;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Track tempTrack = new Track();
            tempTrack.setId(rs.getInt(1));
            tempTrack.setName(rs.getString(2));
            tempTrack.setAddress(rs.getString(3));
            tempTrack.setTypeOfCover(rs.getString(4));
            tempTrack.setLengthTrack(rs.getInt(5));
            tracks.add(tempTrack);
        }
        return tracks;
    }

    public void saveTrack(Track trackForm) throws SQLException {
        Integer id = trackForm.getId();
        String name = trackForm.getName();
        String address = trackForm.getAddress();
        String typeOfCover = trackForm.getTypeOfCover();
        Integer lengthTrack = trackForm.getLengthTrack();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + ",\'" + typeOfCover+"\',"+lengthTrack;
        String query = "INSERT INTO track" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Track getTrackById(int id) throws SQLException {
        String query = "SELECT * FROM track WHERE idtrack=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        Track track=new Track();
        track.setId(rs.getInt(1));
        track.setName(rs.getString(2));
        track.setAddress(rs.getString(3));
        track.setTypeOfCover(rs.getString(4));
        track.setLengthTrack(rs.getInt(5));
        return track;
    }

    public void deleteTrack(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'"+
                " WHERE address=\'" + getTrackById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM track WHERE idtrack=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateTrack(Track updateTrack) throws SQLException {
        String query = "UPDATE track SET name=\'" + updateTrack.getName() + "\'" +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateTrack.getAddress()  +
                "\' WHERE address=\'" + getTrackById(updateTrack.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE track SET address=\'" + updateTrack.getAddress() + "\'" +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE track SET typeOfCover=\'" + updateTrack.getTypeOfCover()+"\'"  +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE track SET lenghtTrack_m=" + updateTrack.getLengthTrack()  +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
    }
}
