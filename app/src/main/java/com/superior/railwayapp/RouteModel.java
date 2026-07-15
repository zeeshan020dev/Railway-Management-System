package com.superior.railwayapp;

// Model class - data ko store karne ka tarika
// Firebase se data is class mein aata hai
public class RouteModel {

    private String routeId;
    private String fromCity;
    private String toCity;
    private String duration;
    private String icon;
    private int trainCount;

    // Empty constructor - Firebase ke liye zaroori hai
    // Bina is ke Firebase data map nahi kar sakta
    public RouteModel() {}

    // Full constructor - manually object banane ke liye
    public RouteModel(String routeId, String fromCity, String toCity,
                      String duration, String icon, int trainCount) {
        this.routeId = routeId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.duration = duration;
        this.icon = icon;
        this.trainCount = trainCount;
    }

    // Getters - data bahar nikalne ke liye
    public String getRouteId() { return routeId; }
    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public String getDuration() { return duration; }
    public String getIcon() { return icon; }
    public int getTrainCount() { return trainCount; }

    // Setters - data andar dalne ke liye
    public void setRouteId(String routeId) { this.routeId = routeId; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setTrainCount(int trainCount) { this.trainCount = trainCount; }
}