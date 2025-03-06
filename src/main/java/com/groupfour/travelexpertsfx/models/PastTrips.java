package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import java.util.Date;

public class PastTrips {
    private SimpleIntegerProperty bookingId;
    private SimpleStringProperty tripDescription;
    private SimpleStringProperty tripDestination;
    private SimpleDoubleProperty tripTotalPrice;
    private SimpleStringProperty tripType;
    private SimpleStringProperty tripClassName;
    private SimpleStringProperty tripStartDate;
    private SimpleStringProperty tripEndDate;

    public PastTrips(Integer bookingId, String tripDescription, String tripDestination, double tripTotalPrice, String tripType, String tripClassName, Date tripStartDate, Date tripEndDate) {
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.tripDescription = new SimpleStringProperty(tripDescription);
        this.tripDestination = new  SimpleStringProperty(tripDestination);
        this.tripTotalPrice = new SimpleDoubleProperty(tripTotalPrice);
        this.tripType = new SimpleStringProperty(tripType);
        this.tripClassName = new SimpleStringProperty(tripClassName);
        this.tripStartDate = new SimpleStringProperty(tripStartDate.toString());
        this.tripEndDate = new SimpleStringProperty(tripEndDate.toString());
    }

    public int getBookingId() {
        return bookingId.get();
    }

    public SimpleIntegerProperty bookingIdProperty() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }

    public String getTripDescription() {
        return tripDescription.get();
    }

    public SimpleStringProperty tripDescriptionProperty() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription.set(tripDescription);
    }

    public String getTripDestination() {
        return tripDestination.get();
    }

    public SimpleStringProperty tripDestinationProperty() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination.set(tripDestination);
    }

    public double getTripTotalPrice() {
        return tripTotalPrice.get();
    }

    public SimpleDoubleProperty tripTotalPriceProperty() {
        return tripTotalPrice;
    }

    public void setTripTotalPrice(double tripTotalPrice) {
        this.tripTotalPrice.set(tripTotalPrice);
    }

    public String getTripType() {
        return tripType.get();
    }

    public SimpleStringProperty tripTypeProperty() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType.set(tripType);
    }

    public String getTripClassName() {
        return tripClassName.get();
    }

    public SimpleStringProperty tripClassNameProperty() {
        return tripClassName;
    }

    public void setTripClassName(String tripClassName) {
        this.tripClassName.set(tripClassName);
    }

    public String getTripStartDate() {
        return tripStartDate.get();
    }

    public SimpleStringProperty tripStartDateProperty() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate.set(tripStartDate);
    }

    public String getTripEndDate() {
        return tripEndDate.get();
    }

    public SimpleStringProperty tripEndDateProperty() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate.set(tripEndDate);
    }
}
