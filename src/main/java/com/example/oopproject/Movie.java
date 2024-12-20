package com.example.oopproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Movie {

    private final StringProperty title;
    private final StringProperty price;
    private final StringProperty timeslot1;
    private final IntegerProperty seats1;
    private final StringProperty available1;

    private final StringProperty timeslot2;
    private final IntegerProperty seats2;
    private final StringProperty available2;

    private final StringProperty timeslot3;
    private final IntegerProperty seats3;
    private final StringProperty available3;

    public Movie(String title, String price, String timeslot1, int seats1, String available1,
                 String timeslot2, int seats2, String available2,
                 String timeslot3, int seats3, String available3) {
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleStringProperty(price);
        this.timeslot1 = new SimpleStringProperty(timeslot1);
        this.seats1 = new SimpleIntegerProperty(seats1);
        this.available1 = new SimpleStringProperty(available1);

        this.timeslot2 = new SimpleStringProperty(timeslot2);
        this.seats2 = new SimpleIntegerProperty(seats2);
        this.available2 = new SimpleStringProperty(available2);

        this.timeslot3 = new SimpleStringProperty(timeslot3);
        this.seats3 = new SimpleIntegerProperty(seats3);
        this.available3 = new SimpleStringProperty(available3);
    }

    // Getter and Setter methods for each property

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty timeslot1Property() {
        return timeslot1;
    }

    public IntegerProperty seats1Property() {
        return seats1;
    }

    public StringProperty available1Property() {
        return available1;
    }

    public StringProperty timeslot2Property() {
        return timeslot2;
    }

    public IntegerProperty seats2Property() {
        return seats2;
    }

    public StringProperty available2Property() {
        return available2;
    }

    public StringProperty timeslot3Property() {
        return timeslot3;
    }

    public IntegerProperty seats3Property() {
        return seats3;
    }

    public StringProperty available3Property() {
        return available3;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getTimeslot1() {
        return timeslot1.get();
    }

    public void setTimeslot1(String timeslot1) {
        this.timeslot1.set(timeslot1);
    }

    public int getSeats1() {
        return seats1.get();
    }

    public void setSeats1(int seats1) {
        this.seats1.set(seats1);
    }

    public String getAvailable1() {
        return available1.get();
    }

    public void setAvailable1(String available1) {
        this.available1.set(available1);
    }

    public String getTimeslot2() {
        return timeslot2.get();
    }

    public void setTimeslot2(String timeslot2) {
        this.timeslot2.set(timeslot2);
    }

    public int getSeats2() {
        return seats2.get();
    }

    public void setSeats2(int seats2) {
        this.seats2.set(seats2);
    }

    public String getAvailable2() {
        return available2.get();
    }

    public void setAvailable2(String available2) {
        this.available2.set(available2);
    }

    public String getTimeslot3() {
        return timeslot3.get();
    }

    public void setTimeslot3(String timeslot3) {
        this.timeslot3.set(timeslot3);
    }

    public int getSeats3() {
        return seats3.get();
    }

    public void setSeats3(int seats3) {
        this.seats3.set(seats3);
    }

    public String getAvailable3() {
        return available3.get();
    }

    public void setAvailable3(String available3) {
        this.available3.set(available3);
    }
}
