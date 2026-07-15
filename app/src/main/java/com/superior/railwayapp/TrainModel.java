package com.superior.railwayapp;

public class TrainModel {

    private String trainId;
    private String trainName;
    private String trainNumber;
    private String departureTime;
    private String arrivalTime;
    private String trainImageUrl;
    private int economySeats;
    private int standardSeats;
    private int economyFare;
    private int standardFare;

    // Empty constructor - Firebase ke liye zaroori
    public TrainModel() {}

    public TrainModel(String trainId, String trainName, String trainNumber,
                      String departureTime, String arrivalTime, String trainImageUrl,
                      int economySeats, int standardSeats,
                      int economyFare, int standardFare) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.trainImageUrl = trainImageUrl;
        this.economySeats = economySeats;
        this.standardSeats = standardSeats;
        this.economyFare = economyFare;
        this.standardFare = standardFare;
    }

    // Getters
    public String getTrainId() { return trainId; }
    public String getTrainName() { return trainName; }
    public String getTrainNumber() { return trainNumber; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getTrainImageUrl() { return trainImageUrl; }
    public int getEconomySeats() { return economySeats; }
    public int getStandardSeats() { return standardSeats; }
    public int getEconomyFare() { return economyFare; }
    public int getStandardFare() { return standardFare; }

    // Setters
    public void setTrainId(String trainId) { this.trainId = trainId; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setTrainImageUrl(String trainImageUrl) { this.trainImageUrl = trainImageUrl; }
    public void setEconomySeats(int economySeats) { this.economySeats = economySeats; }
    public void setStandardSeats(int standardSeats) { this.standardSeats = standardSeats; }
    public void setEconomyFare(int economyFare) { this.economyFare = economyFare; }
    public void setStandardFare(int standardFare) { this.standardFare = standardFare; }
}