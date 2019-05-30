package com.example.demo;

import com.example.demo.Controller.TableController;
import com.example.demo.Model.*;
import com.example.demo.tableForm.ClubForm;
import com.example.demo.tableForm.HistoryForm;

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
}
