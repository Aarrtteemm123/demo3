package com.example.demo.tableForm;

import java.util.ArrayList;
import java.util.List;

public class SportsmenForm {
    private int id;
    private String name;
    private int personalKey;
    private String sport;
    private String sportCategory;
    private String trainerName;
    private String clubName;
    private int numberOfParticipation;
    private int trainerId;

    public SportsmenForm(int id, String name, int personalKey, String sport, String sportCategory, String trainerName, String clubName, int numberOfParticipation, int trainerId) {
        this.id = id;
        this.name = name;
        this.personalKey = personalKey;
        this.sport = sport;
        this.sportCategory = sportCategory;
        this.trainerName = trainerName;
        this.clubName = clubName;
        this.numberOfParticipation = numberOfParticipation;
        this.trainerId = trainerId;
    }

    public int getIntSportCategory()
    {
        switch (sportCategory)
        {
            case "I":return 1;
            case "II":return 2;
            case "III":return 3;
            case "IV":return 4;
            case "V":return 5;
            case "VI":return 6;
            case "VII":return 7;
            case "VIII":return 8;
            case "IX":return 9;
            case "X":return 10;
        }
        return 0;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }


    public SportsmenForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonalKey() {
        return personalKey;
    }

    public void setPersonalKey(int personalKey) {
        this.personalKey = personalKey;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSportCategory() {
        return sportCategory;
    }

    public void setSportCategory(String sportCategory) {
        this.sportCategory = sportCategory;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getNumberOfParticipation() {
        return numberOfParticipation;
    }

    public void setNumberOfParticipation(int numberOfParticipation) {
        this.numberOfParticipation = numberOfParticipation;
    }

}
