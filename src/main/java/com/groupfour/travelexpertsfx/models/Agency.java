package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Agency {
    private SimpleIntegerProperty id;
    private SimpleStringProperty agencyaddress;
    private SimpleStringProperty agencycity;
    private SimpleStringProperty  agencyprov;
    private SimpleStringProperty agencypostal;
    private SimpleStringProperty agencycountry;

    @Override
    public String toString() {
        return
                agencyaddress.getValue() + "," +
                agencycity.getValue() + "," +
                agencyprov.getValue() + "," +
                agencycountry.getValue();
    }

    private SimpleStringProperty agencyphone;
    private SimpleStringProperty agencyfax;

    public Agency(Integer id, String agencyaddress,
                  String agencycity, String agencyprov,
                  String agencypostal, String agencycountry,
                  String agencyphone, String agencyfax) {
        this.id = new SimpleIntegerProperty(id);
        this.agencyaddress = new SimpleStringProperty(agencyaddress);
        this.agencycity = new SimpleStringProperty(agencycity);
        this.agencyprov = new SimpleStringProperty(agencyprov);
        this.agencypostal = new SimpleStringProperty(agencypostal);
        this.agencycountry = new SimpleStringProperty(agencycountry);
        this.agencyphone = new SimpleStringProperty(agencyphone);
        this.agencyfax = new SimpleStringProperty(agencyfax);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getAgencyaddress() {
        return agencyaddress.get();
    }

    public SimpleStringProperty agencyaddressProperty() {
        return agencyaddress;
    }

    public void setAgencyaddress(String agencyaddress) {
        this.agencyaddress.set(agencyaddress);
    }

    public String getAgencycity() {
        return agencycity.get();
    }

    public SimpleStringProperty agencycityProperty() {
        return agencycity;
    }

    public void setAgencycity(String agencycity) {
        this.agencycity.set(agencycity);
    }

    public String getAgencyprov() {
        return agencyprov.get();
    }

    public SimpleStringProperty agencyprovProperty() {
        return agencyprov;
    }

    public void setAgencyprov(String agencyprov) {
        this.agencyprov.set(agencyprov);
    }

    public String getAgencypostal() {
        return agencypostal.get();
    }

    public SimpleStringProperty agencypostalProperty() {
        return agencypostal;
    }

    public void setAgencypostal(String agencypostal) {
        this.agencypostal.set(agencypostal);
    }

    public String getAgencycountry() {
        return agencycountry.get();
    }

    public SimpleStringProperty agencycountryProperty() {
        return agencycountry;
    }

    public void setAgencycountry(String agencycountry) {
        this.agencycountry.set(agencycountry);
    }

    public String getAgencyphone() {
        return agencyphone.get();
    }

    public SimpleStringProperty agencyphoneProperty() {
        return agencyphone;
    }

    public void setAgencyphone(String agencyphone) {
        this.agencyphone.set(agencyphone);
    }

    public String getAgencyfax() {
        return agencyfax.get();
    }

    public SimpleStringProperty agencyfaxProperty() {
        return agencyfax;
    }

    public void setAgencyfax(String agencyfax) {
        this.agencyfax.set(agencyfax);
    }
}
