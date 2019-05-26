package com.example.demo.Model;

public class SportGym {
    private int id;
    private String name;
    private String address;
    private int numberOfSimulators;

    public SportGym() {
    }

    public SportGym(int id, String name, String address, int numberOfSimulators) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfSimulators = numberOfSimulators;
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

    public int getNumberOfSimulators() {
        return numberOfSimulators;
    }

    public void setNumberOfSimulators(int numberOfSimulators) {
        this.numberOfSimulators = numberOfSimulators;
    }
}
