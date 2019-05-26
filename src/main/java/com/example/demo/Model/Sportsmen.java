package com.example.demo.Model;

public class Sportsmen {
    private int id;
    private String name;
    private int personalKey;
    private int sportId;
    private String sportCategory;
    private int trainerId;
    private int clubId;
    private int numberOfParticipation;

    public Sportsmen() {
    }

    public Sportsmen(int id, String name, int personalKey, int sportId, String sportCategory, int trainerId, int clubId, int numberOfParticipation) {
        this.id = id;
        this.name = name;
        this.personalKey = personalKey;
        this.sportId = sportId;
        this.sportCategory = sportCategory;
        this.trainerId = trainerId;
        this.clubId = clubId;
        this.numberOfParticipation = numberOfParticipation;
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

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSportCategory() {
        return sportCategory;
    }

    public void setSportCategory(String sportCategory) {
        this.sportCategory = sportCategory;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getNumberOfParticipation() {
        return numberOfParticipation;
    }

    public void setNumberOfParticipation(int numberOfParticipation) {
        this.numberOfParticipation = numberOfParticipation;
    }
}
