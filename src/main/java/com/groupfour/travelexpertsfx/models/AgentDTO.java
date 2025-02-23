package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AgentDTO {
    private SimpleIntegerProperty agentId;
    private SimpleStringProperty agentFirstName;
    private SimpleStringProperty agentMiddleInitial;
    private SimpleStringProperty agentLastName;
    private SimpleStringProperty agency;

    public AgentDTO(int agentId, String agentFirstName, String agentMiddleInitial, String agentLastName, String agency) {
        this.agentId = new SimpleIntegerProperty(agentId);
        this.agentFirstName = new SimpleStringProperty(agentFirstName);
        this.agentMiddleInitial = new SimpleStringProperty(agentMiddleInitial);
        this.agentLastName = new SimpleStringProperty(agentLastName);
        this.agency = new SimpleStringProperty(agency);
    }

    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }

    public String getAgentFirstName() {
        return agentFirstName.get();
    }

    public SimpleStringProperty agentFirstNameProperty() {
        return agentFirstName;
    }

    public void setAgentFirstName(String agentFirstName) {
        this.agentFirstName.set(agentFirstName);
    }

    public String getAgentMiddleInitial() {
        return agentMiddleInitial.get();
    }

    public SimpleStringProperty agentMiddleInitialProperty() {
        return agentMiddleInitial;
    }

    public void setAgentMiddleInitial(String agentMiddleInitial) {
        this.agentMiddleInitial.set(agentMiddleInitial);
    }

    public String getAgentLastName() {
        return agentLastName.get();
    }

    public SimpleStringProperty agentLastNameProperty() {
        return agentLastName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName.set(agentLastName);
    }

    public String getAgency() {
        return agency.get();
    }

    public SimpleStringProperty agencyProperty() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency.set(agency);
    }

    @Override
    public String toString() {
        if (getAgentMiddleInitial() != null) {
            return getAgentFirstName() + " " + getAgentMiddleInitial() + " " + getAgentLastName() + " (" + getAgency() + ")";
        } else {
            return getAgentFirstName() + " " + getAgentLastName() + " (" + getAgency() + ")";
        }
    }
}
