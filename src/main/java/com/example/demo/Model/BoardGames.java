package com.example.demo.Model;

public class BoardGames {
    private int id;
    private String name;
    private String address;
    private int numberOfTable;

    public BoardGames() {
    }

    public BoardGames(int id, String name, String address, int numberOfTable) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfTable = numberOfTable;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfTable() {
        return numberOfTable;
    }

    public void setNumberOfTable(int numberOfTable) {
        this.numberOfTable = numberOfTable;
    }
}
