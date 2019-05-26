package com.example.demo.Model;

public class Manege {
    private int id;
    private String name;
    private String address;
    private String typeOfCover;

    public Manege(int id, String name, String address, String typeOfCover) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.typeOfCover = typeOfCover;
    }

    public Manege() {
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

    public String getTypeOfCover() {
        return typeOfCover;
    }

    public void setTypeOfCover(String typeOfCover) {
        this.typeOfCover = typeOfCover;
    }
}
