package com.example.demo.Model;

public class Trainer {
    private int id;
    private String name;
    private int personalKey;
    private int sportId;

    public Trainer() {
    }

    public Trainer(int id, String name, int personalKey, int sportId) {
        this.id = id;
        this.name = name;
        this.personalKey = personalKey;
        this.sportId = sportId;
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
}
