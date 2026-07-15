package com.superior.railwayapp;

// Model class - Firebase se booking data store karne ke liye
public class BookingModel {

    private String bookingId;
    private String userId;
    private String trainName;
    private String trainNumber;
    private String fromCity;
    private String toCity;
    private String selectedClass;
    private String departureTime;
    private String arrivalTime;
    private String selectedSeats;
    private String bookingDate;
    private String status;
    private int totalFare;

    // Empty constructor - Firebase ke liye zaroori
    public BookingModel() {}

    // Getters
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getTrainName() { return trainName; }
    public String getTrainNumber() { return trainNumber; }
    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public String getSelectedClass() { return selectedClass; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getSelectedSeats() { return selectedSeats; }
    public String getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }
    public int getTotalFare() { return totalFare; }

    // Setters
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }
    public void setSelectedClass(String selectedClass) { this.selectedClass = selectedClass; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setSelectedSeats(String selectedSeats) { this.selectedSeats = selectedSeats; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public void setStatus(String status) { this.status = status; }
    public void setTotalFare(int totalFare) { this.totalFare = totalFare; }
}