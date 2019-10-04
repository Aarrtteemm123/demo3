package com.example.demo.TableForm;

public class StadiumForm {
    private int id;
    private String name;
    private String address;
    private int numberOfViewers;
    private String type;

    public StadiumForm() {
    }

    public StadiumForm(int id, String name, String address, int numberOfViewers, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfViewers = numberOfViewers;
        this.type = type;
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

    public int getNumberOfViewers() {
        return numberOfViewers;
    }

    public void setNumberOfViewers(int numberOfViewers) {
        this.numberOfViewers = numberOfViewers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
