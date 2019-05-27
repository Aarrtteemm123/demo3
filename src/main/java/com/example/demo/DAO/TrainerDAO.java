package com.example.demo.DAO;

import com.example.demo.Controller.TableController;
import com.example.demo.Model.IceRink;
import com.example.demo.Model.Trainer;
import com.example.demo.tableForm.TrainerForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public TrainerDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<Trainer> getAllTrainer() throws SQLException {
        List<Trainer> trainers = new ArrayList<>();
        String query = "SELECT * FROM trainer;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Trainer tempTrainer = new Trainer();
            tempTrainer.setId(rs.getInt(1));
            tempTrainer.setName(rs.getString(2));
            tempTrainer.setPersonalKey(rs.getInt(3));
            tempTrainer.setSportId(rs.getInt(4));
            trainers.add(tempTrainer);
        }
        return trainers;
    }

    public void saveTrainer(Trainer trainerForm) throws SQLException {
        String query = "SELECT * FROM sport WHERE idsport=" + trainerForm.getSportId() + ";";
        rs = stmt.executeQuery(query);
        int size = 0;
        if (rs != null) {
            rs.last();    // moves cursor to the last row
            size = rs.getRow(); // get row id
        }
        if (size != 0) {
            Integer id = trainerForm.getId();
            String name = trainerForm.getName();
            Integer personalKey = trainerForm.getPersonalKey();
            Integer sportId = trainerForm.getSportId();
            String values = id + ",\'" + name + "\'" + "," + personalKey + "," + sportId;
            query = "INSERT INTO trainer" + " VALUES (" + values + ");";
            stmt.executeUpdate(query);
        }
    }

    public Trainer getTrainerById(int id) throws SQLException {
        String query = "SELECT * FROM trainer WHERE idtrainer=" + id + ";";
        rs = stmt.executeQuery(query);
        TableController tableController = new TableController(url, user, password);
        if (tableController.checkSize(rs)) {
            rs.beforeFirst();
            rs.next();
            Trainer trainer = new Trainer();
            trainer.setId(rs.getInt(1));
            trainer.setName(rs.getString(2));
            trainer.setPersonalKey(rs.getInt(3));
            trainer.setSportId(rs.getInt(4));
            return trainer;
        }
        return new Trainer();
    }

    public void deleteTrainer(int id) throws SQLException {
        String query = "UPDATE sportsmen SET trainerId=0" +
                " WHERE trainerId=" + id + ";";
        stmt.executeUpdate(query);

        query = "DELETE FROM trainer WHERE idtrainer=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateTrainer(Trainer updateTrainer) throws SQLException {
        String query = "UPDATE trainer SET name=\'" + updateTrainer.getName() + "\'" +
                " WHERE idtrainer=" + updateTrainer.getId() + ";";
        stmt.executeUpdate(query);
    }

    public List<TrainerForm> toTrainerFrom(List<Trainer> trainers) throws SQLException {
        List<TrainerForm> trainerFormList = new ArrayList<>(trainers.size());
        for (int i = 0; i < trainers.size(); i++) {
            TrainerForm tempTrainerForm = new TrainerForm();
            if (trainers.get(i).getSportId() != 0) {
                String query = "SELECT * FROM sport WHERE idsport=" + trainers.get(i).getSportId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                tempTrainerForm.setSport(rs.getString(2));
            } else {
                tempTrainerForm.setSport("null");
            }
            tempTrainerForm.setId(trainers.get(i).getId());
            tempTrainerForm.setName(trainers.get(i).getName());
            tempTrainerForm.setPersonalKey(trainers.get(i).getPersonalKey());
            trainerFormList.add(tempTrainerForm);
        }
        return trainerFormList;
    }
}
