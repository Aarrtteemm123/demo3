package com.example.demo.tableForm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryForm {
    private int id;
    private String nameOrganizer;
    private int personalKey;
    private String sport;
    private String date;
    private String address;
    private String firstPlaceClubName;
    private String secondPlaceClubName;
    private String thirdPlaceClubName;

    public HistoryForm(int id, String nameOrganizer, int personalKey, String sport, String date, String address, String firstPlaceClubName, String secondPlaceClubName, String thirdPlaceClubName) {
        this.id = id;
        this.nameOrganizer = nameOrganizer;
        this.personalKey = personalKey;
        this.sport = sport;
        this.date = date;
        this.address = address;
        this.firstPlaceClubName = firstPlaceClubName;
        this.secondPlaceClubName = secondPlaceClubName;
        this.thirdPlaceClubName = thirdPlaceClubName;
    }

    public Integer compareDate(String secondDate) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        if (secondDate.matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$")) {
            Date date1 = null;
            try {
                date1 = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date2 = null;
            try {
                date2 = format.parse(secondDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date1.getTime() > date2.getTime())
                return -1;
            if (date1.getTime() <= date2.getTime())
                return 1;
        }
        return 0;
    }

    public HistoryForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOrganizer() {
        return nameOrganizer;
    }

    public void setNameOrganizer(String nameOrganizer) {
        this.nameOrganizer = nameOrganizer;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstPlaceClubName() {
        return firstPlaceClubName;
    }

    public void setFirstPlaceClubName(String firstPlaceClubName) {
        this.firstPlaceClubName = firstPlaceClubName;
    }

    public String getSecondPlaceClubName() {
        return secondPlaceClubName;
    }

    public void setSecondPlaceClubName(String secondPlaceClubName) {
        this.secondPlaceClubName = secondPlaceClubName;
    }

    public String getThirdPlaceClubName() {
        return thirdPlaceClubName;
    }

    public void setThirdPlaceClubName(String thirdPlaceClubName) {
        this.thirdPlaceClubName = thirdPlaceClubName;
    }
}
