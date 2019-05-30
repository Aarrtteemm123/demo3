package com.example.demo;

import com.example.demo.Controller.TableController;
import com.example.demo.Model.*;
import com.example.demo.tableForm.ClubForm;
import com.example.demo.tableForm.HistoryForm;
import com.example.demo.tableForm.SportsmenForm;
import com.example.demo.tableForm.TrainerForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public Database(String url, String user, String password) throws SQLException {
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
    private boolean checkSize() throws SQLException {
        int size = 0;
        if (rs != null) {
            rs.last();    // moves cursor to the last row
            size = rs.getRow(); // get row id
        }
        if (size != 0)
            return true;
        return false;
    }

    public List<Club> getAllClub() throws SQLException {
        List<Club> clubs = new ArrayList<>();
        String query = "SELECT * FROM club;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Club tempClub = new Club();
            tempClub.setId(rs.getInt(1));
            tempClub.setName(rs.getString(2));
            tempClub.setSportId(rs.getInt(3));
            tempClub.setCountFirstPlace(rs.getInt(4));
            tempClub.setCountSecondPlace(rs.getInt(5));
            tempClub.setCountThirdPlace(rs.getInt(6));
            clubs.add(tempClub);
        }
        return clubs;
    }

    public void saveClub(Club clubForm) throws SQLException {
        Integer id = clubForm.getId();
        String name = clubForm.getName();
        Integer sportId = clubForm.getSportId();
        String values = id + ",\'" + name + "\'," + sportId + "," + 0 + "," + 0 + "," + 0;
        String query = "INSERT INTO club" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Club getClubById(int id) throws SQLException {
        String query = "SELECT * FROM club WHERE idclub=" + id + ";";
        rs = stmt.executeQuery(query);
        TableController tableController = new TableController(url, user, password);
        if (tableController.checkSize(rs)) {
            rs.beforeFirst();
            rs.next();
            Club club = new Club();
            club.setId(rs.getInt(1));
            club.setName(rs.getString(2));
            club.setSportId(rs.getInt(3));
            club.setCountFirstPlace(rs.getInt(4));
            club.setCountSecondPlace(rs.getInt(5));
            club.setCountThirdPlace(rs.getInt(6));
            return club;
        }
        return new Club();
    }

    public Club getClubByName(String name) throws SQLException {
        String query = "SELECT * FROM club WHERE name=\'" + name + "\';";
        rs = stmt.executeQuery(query);
        TableController tableController = new TableController(url, user, password);
        if (tableController.checkSize(rs)) {
            rs.beforeFirst();
            rs.next();
            Club club = new Club();
            club.setId(rs.getInt(1));
            club.setName(rs.getString(2));
            club.setSportId(rs.getInt(3));
            club.setCountFirstPlace(rs.getInt(4));
            club.setCountSecondPlace(rs.getInt(5));
            club.setCountThirdPlace(rs.getInt(6));
            return club;
        }
        return new Club();
    }

    public void deleteClub(int id) throws SQLException {
        String query = "UPDATE sportsmen SET clubId=0" +
                " WHERE clubId=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET 1thClubId=0" +
                " WHERE 1thClubId=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET 2thClubId=0" +
                " WHERE 2thClubId=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET 3thClubId=0" +
                " WHERE 3thClubId=" + id + ";";
        stmt.executeUpdate(query);
        query = "DELETE FROM club WHERE idclub=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateClub(ClubForm updateClubForm) throws SQLException {
        String query = "UPDATE club SET name=\'" + updateClubForm.getName() + "\'" +
                " WHERE idclub=" + updateClubForm.getId() + ";";
        stmt.executeUpdate(query);
    }

    public ClubForm toClubForm(Club club) throws SQLException {
        ClubForm clubForm = new ClubForm();
        String query = "SELECT * FROM club WHERE idclub=" + club.getId() + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        clubForm.setId(rs.getInt(1));
        clubForm.setName(rs.getString(2));
        if (club.getSportId() != 0) {
            query = "SELECT * FROM sport WHERE idsport=" + club.getSportId() + ";";
            rs = stmt.executeQuery(query);
            rs.next();
            clubForm.setSport(rs.getString(2));
        } else
            clubForm.setSport("null");
        clubForm.setCountFirstPlace(club.getCountFirstPlace());
        clubForm.setCountSecondPlace(club.getCountSecondPlace());
        clubForm.setCountThirdPlace(club.getCountThirdPlace());
        return clubForm;
    }

    public List<ClubForm> toClubForm(List<Club> clubList) throws SQLException {
        List<ClubForm> clubFormList = new ArrayList<>(clubList.size());
        for (int i = 0; i < clubList.size(); i++) {
            ClubForm clubForm = new ClubForm();
            String query = "SELECT * FROM club WHERE idclub=" + clubList.get(i).getId() + ";";
            rs = stmt.executeQuery(query);
            rs.next();
            clubForm.setId(rs.getInt(1));
            clubForm.setName(rs.getString(2));
            if (clubList.get(i).getSportId() != 0) {
                query = "SELECT * FROM sport WHERE idsport=" + clubList.get(i).getSportId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                clubForm.setSport(rs.getString(2));
            } else
                clubForm.setSport("null");
            clubForm.setCountFirstPlace(clubList.get(i).getCountFirstPlace());
            clubForm.setCountSecondPlace(clubList.get(i).getCountSecondPlace());
            clubForm.setCountThirdPlace(clubList.get(i).getCountThirdPlace());
            clubFormList.add(clubForm);
        }
        return clubFormList;
    }

    public Club toClub(ClubForm clubForm) throws SQLException {
        Club club = new Club();
        club.setId(clubForm.getId());
        club.setName(clubForm.getName());
        String query = "SELECT * FROM sport WHERE name=\'" + clubForm.getSport() + "\';";
        rs = stmt.executeQuery(query);
        if (checkSize()) {
            rs.beforeFirst();
            rs.next();
            club.setSportId(rs.getInt(1));
        } else
            club.setSportId(0);
        return club;
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
    public List<History> getAllHistory() throws SQLException {
        List<History> historySportsCompetitions = new ArrayList<>();
        String query = "SELECT * FROM organizes_and_history_sports_competitions;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            History tempHistory = new History();
            tempHistory.setId(rs.getInt(1));
            tempHistory.setNameOrganizer(rs.getString(2));
            tempHistory.setPersonalKey(rs.getInt(3));
            tempHistory.setSportId(rs.getInt(4));
            tempHistory.setDate(rs.getString(5));
            tempHistory.setAddress(rs.getString(6));
            tempHistory.setFirstPlaceClubId(rs.getInt(7));
            tempHistory.setSecondPlaceClubId(rs.getInt(8));
            tempHistory.setThirdPlaceClubId(rs.getInt(9));
            historySportsCompetitions.add(tempHistory);
        }
        return historySportsCompetitions;
    }

    public History getHistoryById(int id) throws SQLException {
        String query = "SELECT * FROM organizes_and_history_sports_competitions WHERE idorganizes_and_history_sports_competitions=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        History tempHistory = new History();
        tempHistory.setId(rs.getInt(1));
        tempHistory.setNameOrganizer(rs.getString(2));
        tempHistory.setPersonalKey(rs.getInt(3));
        tempHistory.setSportId(rs.getInt(4));
        tempHistory.setDate(rs.getString(5));
        tempHistory.setAddress(rs.getString(6));
        tempHistory.setFirstPlaceClubId(rs.getInt(7));
        tempHistory.setSecondPlaceClubId(rs.getInt(8));
        tempHistory.setThirdPlaceClubId(rs.getInt(9));
        return tempHistory;
    }

    public void deleteHistory(int id) throws SQLException {
        String query = "SELECT * FROM organizes_and_history_sports_competitions WHERE idorganizes_and_history_sports_competitions=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        int firstClub = rs.getInt(7);
        int secondClub = rs.getInt(8);
        int thirdClub = rs.getInt(9);
        query = "DELETE FROM organizes_and_history_sports_competitions WHERE idorganizes_and_history_sports_competitions=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 1thWin=1thWin-1" +
                " WHERE idclub=" + firstClub + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 2thWin=2thWin-1" +
                " WHERE idclub=" + secondClub + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 3thWin=3thWin-1" +
                " WHERE idclub=" + thirdClub + ";";
        stmt.executeUpdate(query);

        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation-1" +
                " WHERE clubId=" + firstClub + " and numberOfParticipation!=0;";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation-1" +
                " WHERE clubId=" + secondClub + " and numberOfParticipation!=0;";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation-1" +
                " WHERE clubId=" + thirdClub + " and numberOfParticipation!=0;";
        stmt.executeUpdate(query);
    }

    public void updateHistory(HistoryForm historyForm) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET nameOrganizers=\'" + historyForm.getNameOrganizer() + "\'" +
                " WHERE idorganizes_and_history_sports_competitions=" + historyForm.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET date=\'" + historyForm.getDate() + "\'" +
                " WHERE idorganizes_and_history_sports_competitions=" + historyForm.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + historyForm.getAddress() + "\'" +
                " WHERE idorganizes_and_history_sports_competitions=" + historyForm.getId() + ";";
        stmt.executeUpdate(query);
    }

    public void saveHistory(HistoryForm historyForm) throws SQLException {
        History history = toHistory(historyForm);
        Integer id = history.getId();
        String name = history.getNameOrganizer();
        Integer personalKey = history.getPersonalKey();
        Integer sportId = history.getSportId();
        String date = history.getDate();
        String address = history.getAddress();
        Integer firstPlaceClubId = history.getFirstPlaceClubId();
        Integer secondPlaceClubId = history.getSecondPlaceClubId();
        Integer thirdPlaceClubId = history.getThirdPlaceClubId();
        String values = id + ",\'" + name + "\'," + personalKey + "," + sportId + ",\'"
                + date + "\',\'" + address + "\'," + firstPlaceClubId + "," + secondPlaceClubId + "," + thirdPlaceClubId;
        String query = "INSERT INTO organizes_and_history_sports_competitions" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 1thWin=1thWin+1" +
                " WHERE idclub=" + history.getFirstPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 2thWin=2thWin+1" +
                " WHERE idclub=" + history.getSecondPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE club SET 3thWin=3thWin+1" +
                " WHERE idclub=" + history.getThirdPlaceClubId() + ";";
        stmt.executeUpdate(query);

        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation+1" +
                " WHERE clubId=" + history.getFirstPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation+1" +
                " WHERE clubId=" + history.getSecondPlaceClubId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=numberOfParticipation+1" +
                " WHERE clubId=" + history.getThirdPlaceClubId() + ";";
        stmt.executeUpdate(query);
    }

    public List<HistoryForm> toHistoryForm(List<History> historyList) throws SQLException {
        List<HistoryForm> historyFormList = new ArrayList<>(historyList.size());
        for (int i = 0; i < historyList.size(); i++) {
            History history = historyList.get(i);
            HistoryForm historyForm = new HistoryForm();
            historyForm.setId(history.getId());
            historyForm.setNameOrganizer(history.getNameOrganizer());
            historyForm.setPersonalKey(history.getPersonalKey());
            if (history.getSportId() != 0) {
                String query = "SELECT * FROM sport WHERE idsport=" + history.getSportId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setSport(rs.getString(2));
            } else {
                historyForm.setSport("null");
            }
            historyForm.setDate(history.getDate());
            historyForm.setAddress(history.getAddress());
            if (history.getFirstPlaceClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + history.getFirstPlaceClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setFirstPlaceClubName(rs.getString(2));
            } else
                historyForm.setFirstPlaceClubName("null");
            if (history.getSecondPlaceClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + history.getSecondPlaceClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setSecondPlaceClubName(rs.getString(2));
            } else
                historyForm.setSecondPlaceClubName("null");
            if (history.getThirdPlaceClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + history.getThirdPlaceClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                historyForm.setThirdPlaceClubName(rs.getString(2));
            } else
                historyForm.setThirdPlaceClubName("null");
            historyFormList.add(historyForm);
        }
        return historyFormList;
    }

    public History toHistory(HistoryForm historyForm) throws SQLException {
        History history = new History();
        history.setId(historyForm.getId());
        history.setNameOrganizer(historyForm.getNameOrganizer());
        history.setPersonalKey(historyForm.getPersonalKey());
        String query = "SELECT * FROM sport WHERE name= \'" + historyForm.getSport() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setSportId(rs.getInt(1));
        history.setDate(historyForm.getDate());
        history.setAddress(historyForm.getAddress());
        query = "SELECT * FROM club WHERE name=\'" + historyForm.getFirstPlaceClubName() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setFirstPlaceClubId(rs.getInt(1));
        query = "SELECT * FROM club WHERE name=\'" + historyForm.getSecondPlaceClubName() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setSecondPlaceClubId(rs.getInt(1));
        query = "SELECT * FROM club WHERE name=\'" + historyForm.getThirdPlaceClubName() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        history.setThirdPlaceClubId(rs.getInt(1));
        return history;
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

    public IceRink getIceRinkById(int id) throws SQLException {
        String query = "SELECT * FROM ice_rink WHERE idice_rink=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        IceRink iceRink = new IceRink();
        iceRink.setId(rs.getInt(1));
        iceRink.setName(rs.getString(2));
        iceRink.setAddress(rs.getString(3));
        iceRink.setSquare(rs.getInt(4));
        return iceRink;
    }

    public void deleteIceRink(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getIceRinkById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM ice_rink WHERE idice_rink=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateIceRink(IceRink updateIceRink) throws SQLException {
        String query = "UPDATE ice_rink SET name=\'" + updateIceRink.getName() + "\'" +
                " WHERE idice_rink=" + updateIceRink.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateIceRink.getAddress() +
                "\' WHERE address=\'" + getIceRinkById(updateIceRink.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE ice_rink SET address=\'" + updateIceRink.getAddress() + "\'" +
                " WHERE idice_rink=" + updateIceRink.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE ice_rink SET square=" + updateIceRink.getSquare() +
                " WHERE idice_rink=" + updateIceRink.getId() + ";";
        stmt.executeUpdate(query);
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
    public Boolean readPassword(String name, String password) throws SQLException {
        if (!(name.equals("admin")) && !(name.equals("user")))
            return null;
        String query = "SELECT * FROM access WHERE name=\'" + name + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        if (rs.getString(3).equals(password))
            return true;
        return false;
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
        TableController tableController = new TableController(url, user, password);
        if (tableController.checkSize(rs)) {
            rs.beforeFirst();
            rs.next();
            Sport sport = new Sport();
            sport.setId(rs.getInt(1));
            sport.setName(rs.getString(2));
            return sport;
        }
        return new Sport();
    }

    public void deleteSport(int id) throws SQLException {
        String query = "UPDATE trainer SET sportId=0" +
                " WHERE sportId=" + id + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET sportId=0" +
                " WHERE sportId=" + id + ";";
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
    public List<SportGym> getAllSportGym() throws SQLException {
        List<SportGym> sportGyms = new ArrayList<>();
        String query = "SELECT * FROM sport_gym;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            SportGym tempSportGym = new SportGym();
            tempSportGym.setId(rs.getInt(1));
            tempSportGym.setName(rs.getString(2));
            tempSportGym.setAddress(rs.getString(3));
            tempSportGym.setNumberOfSimulators(rs.getInt(4));
            sportGyms.add(tempSportGym);
        }
        return sportGyms;
    }

    public void saveSportGym(SportGym sportGymForm) throws SQLException {
        Integer id = sportGymForm.getId();
        String name = sportGymForm.getName();
        String address = sportGymForm.getAddress();
        Integer numberOfSimulators = sportGymForm.getNumberOfSimulators();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + "," + numberOfSimulators + " ";
        String query = "INSERT INTO sport_gym" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public SportGym getSportGymById(int id) throws SQLException {
        String query = "SELECT * FROM sport_gym WHERE idsport_gym=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        SportGym sportGym = new SportGym();
        sportGym.setId(rs.getInt(1));
        sportGym.setName(rs.getString(2));
        sportGym.setAddress(rs.getString(3));
        sportGym.setNumberOfSimulators(rs.getInt(4));
        return sportGym;
    }

    public void deleteSportGym(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getSportGymById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM sport_gym WHERE idsport_gym=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateSportGym(SportGym updateSportGym) throws SQLException {
        String query = "UPDATE sport_gym SET name=\'" + updateSportGym.getName() + "\'" +
                " WHERE idsport_gym=" + updateSportGym.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateSportGym.getAddress() +
                "\' WHERE address=\'" + getSportGymById(updateSportGym.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE sport_gym SET address=\'" + updateSportGym.getAddress() + "\'" +
                " WHERE idsport_gym=" + updateSportGym.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sport_gym SET numberOfSimulators=" + updateSportGym.getNumberOfSimulators() +
                " WHERE idsport_gym=" + updateSportGym.getId() + ";";
        stmt.executeUpdate(query);
    }

    public List<SportsmenForm> getSportsmenFormFromManySport(List<SportsmenForm> sportsmenFormList) {
        List<SportsmenForm> result = new ArrayList<>(100);
        List<SportsmenForm> buffer = new ArrayList<>(100);
        boolean fl = true;
        for (int i = 0; i < sportsmenFormList.size(); i++) {
            buffer.clear();
            for (int j = i; j < sportsmenFormList.size(); j++) {
                if (sportsmenFormList.get(i).getPersonalKey() ==
                        sportsmenFormList.get(j).getPersonalKey() && i != j) {
                    if (fl) {
                        buffer.add(sportsmenFormList.get(i));
                        fl = false;
                    }
                    buffer.add(sportsmenFormList.get(j));
                }
            }
            for (int j = 0; j < buffer.size(); j++) {
                if (!(buffer.get(0).getSport().equals(buffer.get(j).getSport()))) {
                    for (int k = 0; k < buffer.size(); k++) {
                        if (!result.contains(buffer.get(k)))
                            result.add(buffer.get(k));
                    }
                    break;
                }
            }
            fl = true;

        }
        return result;
    }

    public List<Sportsmen> getAllSportsmen() throws SQLException {
        List<Sportsmen> sportsmens = new ArrayList<>();
        String query = "SELECT * FROM sportsmen;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Sportsmen tempSportsmen = new Sportsmen();
            tempSportsmen.setId(rs.getInt(1));
            tempSportsmen.setName(rs.getString(2));
            tempSportsmen.setPersonalKey(rs.getInt(3));
            tempSportsmen.setSportId(rs.getInt(4));
            tempSportsmen.setSportCategory(rs.getString(5));
            tempSportsmen.setTrainerId(rs.getInt(6));
            tempSportsmen.setClubId(rs.getInt(7));
            tempSportsmen.setNumberOfParticipation(rs.getInt(8));
            sportsmens.add(tempSportsmen);
        }
        return sportsmens;
    }

    public Sportsmen getSportsmenById(int id) throws SQLException {
        String query = "SELECT * FROM sportsmen WHERE idsportsmen=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        Sportsmen stadium = new Sportsmen();
        stadium.setId(rs.getInt(1));
        stadium.setName(rs.getString(2));
        stadium.setPersonalKey(rs.getInt(3));
        stadium.setSportId(rs.getInt(4));
        stadium.setSportCategory(rs.getString(5));
        stadium.setTrainerId(rs.getInt(6));
        stadium.setClubId(rs.getInt(7));
        stadium.setNumberOfParticipation(rs.getInt(8));
        return stadium;
    }

    public void deleteSportsmen(int id) throws SQLException {
        String query = "DELETE FROM sportsmen WHERE idsportsmen=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateSportsmen(SportsmenForm sportsmenForm) throws SQLException {
        String query = "UPDATE sportsmen SET name=\'" + sportsmenForm.getName() + "\'" +
                " WHERE idsportsmen=" + sportsmenForm.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET sport_category=\'" + sportsmenForm.getSportCategory() + "\'" +
                " WHERE idsportsmen=" + sportsmenForm.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE sportsmen SET numberOfParticipation=" + sportsmenForm.getNumberOfParticipation() +
                " WHERE idsportsmen=" + sportsmenForm.getId() + ";";
        stmt.executeUpdate(query);
    }

    public void saveSportsmen(SportsmenForm sportsmenForm) throws SQLException {
        Sportsmen sportsmen = toSportsmen(sportsmenForm);
        Integer id = sportsmen.getId();
        String name = sportsmen.getName();
        Integer personalKey = sportsmen.getPersonalKey();
        Integer sportId = sportsmen.getSportId();
        String sportCategory = sportsmen.getSportCategory();
        Integer trainerId = sportsmen.getTrainerId();
        Integer clubId = sportsmen.getClubId();
        Integer numberOfParticipation = sportsmen.getNumberOfParticipation();
        String values = id + ",\'" + name + "\'," + personalKey + "," + sportId + ",\'"
                + sportCategory + "\'," + trainerId + "," + clubId + "," +
                numberOfParticipation;
        String query = "INSERT INTO sportsmen" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public List<SportsmenForm> toSportsmenForm(List<Sportsmen> sportsmenList) throws SQLException {
        List<SportsmenForm> sportsmenFormList = new ArrayList<>(sportsmenList.size());
        for (int i = 0; i < sportsmenList.size(); i++) {
            Sportsmen sportsmen = sportsmenList.get(i);
            SportsmenForm sportsmenForm = new SportsmenForm();
            sportsmenForm.setId(sportsmen.getId());
            sportsmenForm.setName(sportsmen.getName());
            sportsmenForm.setPersonalKey(sportsmen.getPersonalKey());
            if (sportsmen.getSportId() != 0) {
                String query = "SELECT * FROM sport WHERE idsport=" + sportsmen.getSportId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                sportsmenForm.setSport(rs.getString(2));
            } else {
                sportsmenForm.setSport("null");
            }
            sportsmenForm.setSportCategory(sportsmen.getSportCategory());
            if (sportsmen.getTrainerId() != 0) {
                String query = "SELECT * FROM trainer WHERE idtrainer=" + sportsmen.getTrainerId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                sportsmenForm.setTrainerName(rs.getString(2));
            } else {
                sportsmenForm.setTrainerName("null");
            }
            if (sportsmen.getClubId() != 0) {
                String query = "SELECT * FROM club WHERE idclub=" + sportsmen.getClubId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                sportsmenForm.setClubName(rs.getString(2));
            } else
                sportsmenForm.setClubName("null");
            sportsmenForm.setNumberOfParticipation(sportsmen.getNumberOfParticipation());
            sportsmenForm.setTrainerId(sportsmen.getTrainerId());
            sportsmenFormList.add(sportsmenForm);
        }
        return sportsmenFormList;
    }

    public Sportsmen toSportsmen(SportsmenForm sportsmenForm) throws SQLException {
        Sportsmen sportsmen = new Sportsmen();
        sportsmen.setId(sportsmenForm.getId());
        sportsmen.setName(sportsmenForm.getName());
        sportsmen.setPersonalKey(sportsmenForm.getPersonalKey());
        String query = "SELECT * FROM sport WHERE name= \'" + sportsmenForm.getSport() + "\';";
        rs = stmt.executeQuery(query);
        rs.next();
        sportsmen.setSportId(rs.getInt(1));
        sportsmen.setSportCategory(sportsmenForm.getSportCategory());
        sportsmen.setTrainerId(sportsmenForm.getTrainerId());
        query = "SELECT * FROM club WHERE name=\'" + sportsmenForm.getClubName() + "\' AND sportId=" + sportsmen.getSportId() + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        sportsmen.setClubId(rs.getInt(1));
        sportsmen.setNumberOfParticipation(sportsmenForm.getNumberOfParticipation());
        return sportsmen;
    }

    public List<Stadium> getAllStadium() throws SQLException {
        List<Stadium> stadiums = new ArrayList<>();
        String query = "SELECT * FROM stadium;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            Stadium tempStadium = new Stadium();
            tempStadium.setId(rs.getInt(1));
            tempStadium.setName(rs.getString(2));
            tempStadium.setAddress(rs.getString(3));
            tempStadium.setNumberOfViewers(rs.getInt(4));
            tempStadium.setType(rs.getString(5));
            stadiums.add(tempStadium);
        }
        return stadiums;
    }

    public void saveStadium(Stadium stadiumForm) throws SQLException {
        Integer id = stadiumForm.getId();
        String name = stadiumForm.getName();
        String address = stadiumForm.getAddress();
        Integer numberOfViewers = stadiumForm.getNumberOfViewers();
        String type = stadiumForm.getType();
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" +
                "," + numberOfViewers + ",\'" + type + "\'";
        String query = "INSERT INTO stadium" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Stadium getStadiumById(int id) throws SQLException {
        String query = "SELECT * FROM stadium WHERE idstadium=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        Stadium stadium = new Stadium();
        stadium.setId(rs.getInt(1));
        stadium.setName(rs.getString(2));
        stadium.setAddress(rs.getString(3));
        stadium.setNumberOfViewers(rs.getInt(4));
        stadium.setType(rs.getString(5));
        return stadium;
    }

    public void deleteStadium(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getStadiumById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM stadium WHERE idstadium=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateStadium(Stadium updatedStadium) throws SQLException {
        String query = "UPDATE stadium SET name=\'" + updatedStadium.getName() + "\'" +
                " WHERE idstadium=" + updatedStadium.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updatedStadium.getAddress() +
                "\' WHERE address=\'" + getStadiumById(updatedStadium.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE stadium SET address=\'" + updatedStadium.getAddress() + "\'" +
                " WHERE idstadium=" + updatedStadium.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE stadium SET numberOfViewers=" + updatedStadium.getNumberOfViewers() +
                " WHERE idstadium=" + updatedStadium.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE stadium SET type=\'" + updatedStadium.getType() + "\'" +
                " WHERE idstadium=" + updatedStadium.getId() + ";";
        stmt.executeUpdate(query);
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
        String values = id + ",\'" + name + "\'" + ",\'" + address + "\'" + ",\'" + typeOfCover + "\'," + lengthTrack;
        String query = "INSERT INTO track" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Track getTrackById(int id) throws SQLException {
        String query = "SELECT * FROM track WHERE idtrack=" + id + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        Track track = new Track();
        track.setId(rs.getInt(1));
        track.setName(rs.getString(2));
        track.setAddress(rs.getString(3));
        track.setTypeOfCover(rs.getString(4));
        track.setLengthTrack(rs.getInt(5));
        return track;
    }

    public void deleteTrack(int id) throws SQLException {
        String query = "UPDATE organizes_and_history_sports_competitions SET address=\'null\'" +
                " WHERE address=\'" + getTrackById(id).getAddress() + "\' ;";
        stmt.executeUpdate(query);
        query = "DELETE FROM track WHERE idtrack=" + id + ";";
        stmt.executeUpdate(query);
    }

    public void updateTrack(Track updateTrack) throws SQLException {
        String query = "UPDATE track SET name=\'" + updateTrack.getName() + "\'" +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE organizes_and_history_sports_competitions SET address=\'" + updateTrack.getAddress() +
                "\' WHERE address=\'" + getTrackById(updateTrack.getId()).getAddress() + "\';";
        stmt.executeUpdate(query);
        query = "UPDATE track SET address=\'" + updateTrack.getAddress() + "\'" +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE track SET typeOfCover=\'" + updateTrack.getTypeOfCover() + "\'" +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
        query = "UPDATE track SET lenghtTrack_m=" + updateTrack.getLengthTrack() +
                " WHERE idtrack=" + updateTrack.getId() + ";";
        stmt.executeUpdate(query);
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
