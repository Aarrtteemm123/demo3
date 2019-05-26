package com.example.demo.Model;

public class SwimmingPool {
    private int id;
    private String name;
    private String address;
    private int depth;
    private int numberOfTower;

    public SwimmingPool() {
    }

    public SwimmingPool(int id, String name, String address, int depth, int numberOfTower) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.depth = depth;
        this.numberOfTower = numberOfTower;
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getNumberOfTower() {
        return numberOfTower;
    }

    public void setNumberOfTower(int numberOfTower) {
        this.numberOfTower = numberOfTower;
    }
}
