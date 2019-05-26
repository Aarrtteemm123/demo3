package com.example.demo.DAO;

import com.example.demo.Model.Sportsmen;
import com.example.demo.tableForm.SportsmenForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportsmenDAO {
    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public SportsmenDAO(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    public List<SportsmenForm> getSportsmenFormFromManySport(List<SportsmenForm> sportsmenFormList)
    {
        List<SportsmenForm> result = new ArrayList<>(100);
        List<SportsmenForm> buffer = new ArrayList<>(100);
        boolean fl=true;
        for (int i = 0; i < sportsmenFormList.size(); i++)
        {
            buffer.clear();
            for (int j = i; j < sportsmenFormList.size(); j++)
            {
                if (sportsmenFormList.get(i).getPersonalKey()==
                        sportsmenFormList.get(j).getPersonalKey()&&i!=j)
                {
                    if (fl)
                    {
                        buffer.add(sportsmenFormList.get(i));
                        fl=false;
                    }
                    buffer.add(sportsmenFormList.get(j));
                }
            }
            for (int j = 0; j < buffer.size(); j++)
            {
                if(!(buffer.get(0).getSport().equals(buffer.get(j).getSport())))
                {
                    for (int k = 0; k < buffer.size(); k++)
                    {
                        if (!result.contains(buffer.get(k)))
                            result.add(buffer.get(k));
                    }
                    break;
                }
            }
            fl=true;

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
        Sportsmen sportsmen=toSportsmen(sportsmenForm);
        Integer id = sportsmen.getId();
        String name = sportsmen.getName();
        Integer personalKey = sportsmen.getPersonalKey();
        Integer sportId = sportsmen.getSportId();
        String sportCategory = sportsmen.getSportCategory();
        Integer trainerId=sportsmen.getTrainerId();
        Integer clubId=sportsmen.getClubId();
        Integer numberOfParticipation=sportsmen.getNumberOfParticipation();
        String values = id + ",\'" + name + "\'," + personalKey+","+sportId+",\'"
                +sportCategory+"\',"+trainerId+","+clubId+","+
                numberOfParticipation;
        String query = "INSERT INTO sportsmen" + " VALUES (" + values + ");";
        stmt.executeUpdate(query);
    }

    public List<SportsmenForm> toSportsmenForm(List<Sportsmen>sportsmenList) throws SQLException {
        List<SportsmenForm>sportsmenFormList=new ArrayList<>(sportsmenList.size());
        for (int i = 0; i < sportsmenList.size(); i++)
        {
            Sportsmen sportsmen=sportsmenList.get(i);
            SportsmenForm sportsmenForm=new SportsmenForm();
            sportsmenForm.setId(sportsmen.getId());
            sportsmenForm.setName(sportsmen.getName());
            sportsmenForm.setPersonalKey(sportsmen.getPersonalKey());
            if (sportsmen.getSportId()!=0)
            {
                String query="SELECT * FROM sport WHERE idsport="+sportsmen.getSportId()+";";
                rs = stmt.executeQuery(query);
                rs.next();
                sportsmenForm.setSport(rs.getString(2));
            }
            else
            {
                sportsmenForm.setSport("null");
            }
            sportsmenForm.setSportCategory(sportsmen.getSportCategory());
            if (sportsmen.getTrainerId()!=0)
            {
                String query="SELECT * FROM trainer WHERE idtrainer="+sportsmen.getTrainerId()+";";
                rs = stmt.executeQuery(query);
                rs.next();
                sportsmenForm.setTrainerName(rs.getString(2));
            }
            else
            {
                sportsmenForm.setTrainerName("null");
            }
            if (sportsmen.getClubId()!=0)
            {
                String query="SELECT * FROM club WHERE idclub="+sportsmen.getClubId()+";";
                rs = stmt.executeQuery(query);
                rs.next();
                sportsmenForm.setClubName(rs.getString(2));
            }
            else
                sportsmenForm.setClubName("null");
            sportsmenForm.setNumberOfParticipation(sportsmen.getNumberOfParticipation());
            sportsmenForm.setTrainerId(sportsmen.getTrainerId());
            sportsmenFormList.add(sportsmenForm);
        }
        return sportsmenFormList;
    }

    public Sportsmen toSportsmen(SportsmenForm sportsmenForm) throws SQLException {
        Sportsmen sportsmen=new Sportsmen();
        sportsmen.setId(sportsmenForm.getId());
        sportsmen.setName(sportsmenForm.getName());
        sportsmen.setPersonalKey(sportsmenForm.getPersonalKey());
        String query="SELECT * FROM sport WHERE name= \'"+sportsmenForm.getSport()+"\';";
        rs = stmt.executeQuery(query);
        rs.next();
        sportsmen.setSportId(rs.getInt(1));
        sportsmen.setSportCategory(sportsmenForm.getSportCategory());
        sportsmen.setTrainerId(sportsmenForm.getTrainerId());
        query="SELECT * FROM club WHERE name=\'"+sportsmenForm.getClubName()+"\' AND sportId="+sportsmen.getSportId()+";";
        rs = stmt.executeQuery(query);
        rs.next();
        sportsmen.setClubId(rs.getInt(1));
        sportsmen.setNumberOfParticipation(sportsmenForm.getNumberOfParticipation());
        return sportsmen;
    }
}
