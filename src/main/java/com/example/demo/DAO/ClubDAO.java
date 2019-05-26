package com.example.demo.DAO;

import com.example.demo.Controller.TableController;
import com.example.demo.Model.Club;
import com.example.demo.Model.Sport;
import com.example.demo.tableForm.ClubForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public ClubDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    private boolean checkSize() throws SQLException {
        int size =0;
        if (rs != null)
        {
            rs.last();    // moves cursor to the last row
            size = rs.getRow(); // get row id
        }
        if (size!=0)
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
        String values = id + ",\'" + name + "\',"+sportId+","+0+","+0+","+0;
        String query = "INSERT INTO club" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public Club getClubById(int id) throws SQLException {
        String query = "SELECT * FROM club WHERE idclub=" + id + ";";
        rs = stmt.executeQuery(query);
        TableController tableController=new TableController(url,user,password);
        if (tableController.checkSize(rs))
        {
            rs.beforeFirst();
            rs.next();
            Club club=new Club();
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
        TableController tableController=new TableController(url,user,password);
        if (tableController.checkSize(rs))
        {
            rs.beforeFirst();
            rs.next();
            Club club=new Club();
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

    public ClubForm toClubForm(Club club) throws SQLException
    {
        ClubForm clubForm =new ClubForm();
        String query = "SELECT * FROM club WHERE idclub=" + club.getId() + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        clubForm.setId(rs.getInt(1));
        clubForm.setName(rs.getString(2));
        if (club.getSportId()!=0)
        {
            query = "SELECT * FROM sport WHERE idsport=" + club.getSportId() + ";";
            rs = stmt.executeQuery(query);
            rs.next();
            clubForm.setSport(rs.getString(2));
        }
        else
            clubForm.setSport("null");
        clubForm.setCountFirstPlace(club.getCountFirstPlace());
        clubForm.setCountSecondPlace(club.getCountSecondPlace());
        clubForm.setCountThirdPlace(club.getCountThirdPlace());
        return clubForm;
    }

    public List<ClubForm> toClubForm(List<Club>clubList) throws SQLException {
        List<ClubForm>clubFormList=new ArrayList<>(clubList.size());
        for (int i = 0; i < clubList.size(); i++)
        {
            ClubForm clubForm =new ClubForm();
            String query = "SELECT * FROM club WHERE idclub=" + clubList.get(i).getId() + ";";
            rs = stmt.executeQuery(query);
            rs.next();
            clubForm.setId(rs.getInt(1));
            clubForm.setName(rs.getString(2));
            if (clubList.get(i).getSportId()!=0)
            {
                query = "SELECT * FROM sport WHERE idsport=" + clubList.get(i).getSportId() + ";";
                rs = stmt.executeQuery(query);
                rs.next();
                clubForm.setSport(rs.getString(2));
            }
            else
                clubForm.setSport("null");
            clubForm.setCountFirstPlace(clubList.get(i).getCountFirstPlace());
            clubForm.setCountSecondPlace(clubList.get(i).getCountSecondPlace());
            clubForm.setCountThirdPlace(clubList.get(i).getCountThirdPlace());
            clubFormList.add(clubForm);
        }
        return clubFormList;
    }

    public Club toClub(ClubForm clubForm) throws SQLException {
        Club club=new Club();
        club.setId(clubForm.getId());
        club.setName(clubForm.getName());
        String query = "SELECT * FROM sport WHERE name=\'" + clubForm.getSport() + "\';";
        rs = stmt.executeQuery(query);
        if (checkSize())
        {
            rs.beforeFirst();
            rs.next();
            club.setSportId(rs.getInt(1));
        }
        else
            club.setSportId(0);
        return club;
    }
}
