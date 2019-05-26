package com.example.demo.tableForm;

public class TrainerForm {
    private int id;
    private String name;
    private int personalKey;
    private String sport;

    public TrainerForm() {
    }

    public TrainerForm(int id, String name, int personalKey, String sport) {
        this.id = id;
        this.name = name;
        this.personalKey = personalKey;
        this.sport = sport;
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
}
