package com.example.dclassicsbooks;

public class Store {

    private String name;
    private String address;
    private String distance;
    private int imageResId;

    public Store(String name, String address, String distance, int imageResId) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.imageResId = imageResId;
    }

    // -------- GETTERS --------
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public int getImage() {
        return imageResId;
    }

    // -------- SETTERS --------
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setImage(int imageResId) {
        this.imageResId = imageResId;
    }
}