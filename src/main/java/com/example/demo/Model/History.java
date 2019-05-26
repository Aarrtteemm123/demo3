package com.example.demo.Model;

public class History {
    private int id;
    private String nameOrganizer;
    private int personalKey;
    private int sportId;
    private String date;
    private String address;
    private int firstPlaceClubId;
    private int secondPlaceClubId;
    private int thirdPlaceClubId;

    public History(int id, String nameOrganizer, int personalKey, int sportId, String date, String address, int firstPlaceClubId, int secondPlaceClubId, int thirdPlaceClubId) {
        this.id = id;
        this.nameOrganizer = nameOrganizer;
        this.personalKey = personalKey;
        this.sportId = sportId;
        this.date = date;
        this.address = address;
        this.firstPlaceClubId = firstPlaceClubId;
        this.secondPlaceClubId = secondPlaceClubId;
        this.thirdPlaceClubId = thirdPlaceClubId;
    }

    public History() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOrganizer() {
        return nameOrganizer;
    }

    public void setNameOrganizer(String nameOrganizer) {
        this.nameOrganizer = nameOrganizer;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFirstPlaceClubId() {
        return firstPlaceClubId;
    }

    public void setFirstPlaceClubId(int firstPlaceClubId) {
        this.firstPlaceClubId = firstPlaceClubId;
    }

    public int getSecondPlaceClubId() {
        return secondPlaceClubId;
    }

    public void setSecondPlaceClubId(int secondPlaceClubId) {
        this.secondPlaceClubId = secondPlaceClubId;
    }

    public int getThirdPlaceClubId() {
        return thirdPlaceClubId;
    }

    public void setThirdPlaceClubId(int thirdPlaceClubId) {
        this.thirdPlaceClubId = thirdPlaceClubId;
    }
}
