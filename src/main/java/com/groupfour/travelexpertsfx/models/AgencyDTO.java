package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AgencyDTO {
    private SimpleIntegerProperty agencyid;
    private SimpleStringProperty agencyAddress;
    private SimpleStringProperty agencyCity;

    public int getAgencyid() {
        return agencyid.get();
    }

    public SimpleIntegerProperty agencyidProperty() {
        return agencyid;
    }

    public void setAgencyid(int agencyid) {
        this.agencyid.set(agencyid);
    }

    public String getAgencyAddress() {
        return agencyAddress.get();
    }

    public SimpleStringProperty agencyAddressProperty() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress.set(agencyAddress);
    }

    public String getAgencyCity() {
        return agencyCity.get();
    }

    public SimpleStringProperty agencyCityProperty() {
        return agencyCity;
    }

    public void setAgencyCity(String agencyCity) {
        this.agencyCity.set(agencyCity);
    }

    @Override
    public String toString() {
        return agencyAddress + ", " + agencyCity;
    }
}
