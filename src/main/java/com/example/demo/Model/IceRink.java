package com.example.demo.Model;

public class IceRink {
    private int id;
    private String name;
    private String address;
    private int square;

    public IceRink() {
    }

    public IceRink(int id, String name, String address, int square) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.square = square;
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

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }
}
