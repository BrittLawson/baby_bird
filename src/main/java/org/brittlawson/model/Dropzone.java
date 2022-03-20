package org.brittlawson.model;

public class Dropzone {

    private int dzId;
    private String dzName;
    private String city;
    private String stateAbbreviation;
    private String country;

    public int getDzId() {
        return dzId;
    }

    public void setDzId(int dzId) {
        this.dzId = dzId;
    }

    public String getDzName() {
        return dzName;
    }

    public void setDzName(String dzName) {
        this.dzName = dzName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
