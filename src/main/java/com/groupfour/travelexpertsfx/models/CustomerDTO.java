package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDTO {
    private SimpleIntegerProperty customerId;
    private SimpleStringProperty customerName;
    private SimpleStringProperty customerPhone;
    private SimpleStringProperty customerEmail;
    private SimpleStringProperty customerAgent;

    public CustomerDTO(Integer customerId, String customerName, String customerPhone, String customerEmail, String customerAgent) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerPhone = new SimpleStringProperty(customerPhone);
        this.customerEmail = new SimpleStringProperty(customerEmail);
        this.customerAgent = new SimpleStringProperty(customerAgent);

    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public SimpleStringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }

    public String getCustomerEmail() {
        return customerEmail.get();
    }

    public SimpleStringProperty customerEmailProperty() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail.set(customerEmail);
    }

    public String getCustomerAgent() {
        return customerAgent.get();
    }

    public SimpleStringProperty customerAgentProperty() {
        return customerAgent;
    }

    public void setCustomerAgent(String customerAgent) {
        this.customerAgent.set(customerAgent);
    }
}
