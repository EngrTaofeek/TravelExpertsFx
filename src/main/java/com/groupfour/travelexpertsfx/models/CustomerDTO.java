package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDTO {
    private SimpleIntegerProperty customerId;
    private SimpleStringProperty customerFirstName;
    private SimpleStringProperty customerLastName;

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerFirstName() {
        return customerFirstName.get();
    }

    public SimpleStringProperty customerFirstNameProperty() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName.set(customerFirstName);
    }

    public String getCustomerLastName() {
        return customerLastName.get();
    }

    public SimpleStringProperty customerLastNameProperty() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName.set(customerLastName);
    }

    @Override
    public String toString() {
        return "#" + customerId + " " + customerFirstName + " " + customerLastName;
    }
}
