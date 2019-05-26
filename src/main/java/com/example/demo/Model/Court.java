package com.example.demo.Model;

public class Court {
    private int id;
    private String name;
    private String address;
    private int numberOfPlayground;

    public Court() {
    }

    public Court(int id, String name, String address, int numberOfPlayground) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfPlayground = numberOfPlayground;
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

    public int getNumberOfPlayground() {
        return numberOfPlayground;
    }

    public void setNumberOfPlayground(int numberOfPlayground) {
        this.numberOfPlayground = numberOfPlayground;
    }
}
