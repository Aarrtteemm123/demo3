package com.example.demo.DAO;

import com.example.demo.Model.BoardGames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardGamesDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public BoardGamesDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<BoardGames> getAllBoardGames() throws SQLException {
        List<BoardGames> boardGames = new ArrayList<>();
        String query = "SELECT * FROM board_games;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            BoardGames tempBoardGames = new BoardGames();
            tempBoardGames.setId(rs.getInt(1));
            tempBoardGames.setName(rs.getString(2));
            tempBoardGames.setAddress(rs.getString(3));
            tempBoardGames.setNumberOfTable(rs.getInt(4));
            boardGames.add(tempBoardGames);
        }
        return boardGames;
    }

    public void saveBoardGames(BoardGames boardGamesForm) throws SQLException {
        Integer id = boardGamesForm.getId();
        String name = boardGamesForm.getName();
        String address = boardGamesForm.getAddress();
        Integer numberOfTable = boardGamesForm.getNumberOfTable();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'," + numberOfTable;
        String query = "INSERT INTO board_games" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public BoardGames getBoardGamesById(int id) throws SQLException {
        String query = "SELECT * FROM board_games WHERE idboard_games=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        BoardGames boardGame = new BoardGames();
        boardGame.setId(rs.getInt(1));
        boardGame.setName(rs.getString(2));
        boardGame.setAddress(rs.getString(3));
        boardGame.setNumberOfTable(rs.getInt(4));
        return boardGame;
    }

    public void deleteBoardGames(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getBoardGamesById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM board_games WHERE idboard_games=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateBoardGames(BoardGames updateBoardGames) throws SQLException {
        String query = "UPDATE board_games SET name=\'" + updateBoardGames.getName() + "\'" +
                " WHERE idboard_games=" + updateBoardGames.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateBoardGames.getAddress() +
                "\' WHERE address=\'" + getBoardGamesById(updateBoardGames.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE board_games SET address=\'" + updateBoardGames.getAddress() + "\'" +
                " WHERE idboard_games=" + updateBoardGames.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE board_games SET numberOfTable=" + updateBoardGames.getNumberOfTable() +
                " WHERE idboard_games=" + updateBoardGames.getId() + ";";
        stmt.executeUpdate(query);
    }
}
