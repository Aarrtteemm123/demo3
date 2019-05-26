package com.example.demo.tableForm;

public class ClubForm {
    private int id;
    private String name;
    private String sport;
    private int countFirstPlace;
    private int countSecondPlace;
    private int countThirdPlace;

    public ClubForm(int id, String name, String sport, int countFirstPlace, int countSecondPlace, int countThirdPlace) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.countFirstPlace = countFirstPlace;
        this.countSecondPlace = countSecondPlace;
        this.countThirdPlace = countThirdPlace;
    }

    public ClubForm() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
