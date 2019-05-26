package com.example.demo.Model;

public class Track {
    private int id;
    private String name;
    private String address;
    private String typeOfCover;
    private int lengthTrack;

    public Track() {
    }

    public Track(int id, String name, String address, String typeOfCover, int lengthTrack) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.typeOfCover = typeOfCover;
        this.lengthTrack = lengthTrack;
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

    public int getLengthTrack() {
        return lengthTrack;
    }

    public void setLengthTrack(int lengthTrack) {
        this.lengthTrack = lengthTrack;
    }
}
