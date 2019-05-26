package com.example.demo.Model;

public class Club {
    private int id;
    private String name;
    private int sportId;
    private int countFirstPlace;
    private int countSecondPlace;
    private int countThirdPlace;

    public Club() {
    }

    public Club(int id, String name, int sportId, int countFirstPlace, int countSecondPlace, int countThirdPlace) {
        this.id = id;
        this.name = name;
        this.sportId = sportId;
        this.countFirstPlace = countFirstPlace;
        this.countSecondPlace = countSecondPlace;
        this.countThirdPlace = countThirdPlace;
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

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getCountFirstPlace() {
        return countFirstPlace;
    }

    public void setCountFirstPlace(int countFirstPlace) {
        this.countFirstPlace = countFirstPlace;
    }

    public int getCountSecondPlace() {
        return countSecondPlace;
    }

    public void setCountSecondPlace(int countSecondPlace) {
        this.countSecondPlace = countSecondPlace;
    }

    public int getCountThirdPlace() {
        return countThirdPlace;
    }

    public void setCountThirdPlace(int countThirdPlace) {
        this.countThirdPlace = countThirdPlace;
    }
}
