package com.example.oopproject;


public class Movie {
    private final String movieName;
    private final double ticketPrice;
    private final String timeSlots;
    private final int seats;

    public Movie(String movieName, double ticketPrice, String timeSlots, int seats) {
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
        this.timeSlots = timeSlots;
        this.seats = seats;
    }

    public String getMovieName() {
        return movieName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public int getSeats() {
        return seats;
    }

    public String toDatabaseFormat() {
        return movieName + "|" + ticketPrice + "+" + seats + "|" + timeSlots;
    }
}
