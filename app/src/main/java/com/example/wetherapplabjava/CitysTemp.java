package com.example.wetherapplabjava;

public class CitysTemp {

    private String city;
    private String temp;

    public CitysTemp(String city, String temp) {
        this.city = city;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
