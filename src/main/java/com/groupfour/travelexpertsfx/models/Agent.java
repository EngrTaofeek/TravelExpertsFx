package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {
    private SimpleIntegerProperty id;
    private SimpleStringProperty agtfirstname;
    private SimpleStringProperty agtmiddleinitial;
    private SimpleStringProperty agtlastname;
    private SimpleStringProperty agtbusphone;
    private SimpleStringProperty agtemail;
    private SimpleStringProperty agtposition;
    private SimpleIntegerProperty agencyid;
    private SimpleStringProperty agency;
    private SimpleStringProperty role;

    public Agent(Integer id, String agtfirstname,
                 String agtmiddleinitial, String agtlastname,
                 String agtbusphone, String agtemail,
                 String agtposition, Integer agencyid,
                 String agency, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.agtfirstname = new SimpleStringProperty(agtfirstname);
        this.agtmiddleinitial = new SimpleStringProperty(agtmiddleinitial);
        this.agtlastname = new SimpleStringProperty(agtlastname);
        this.agtbusphone = new SimpleStringProperty(agtbusphone);
        this.agtemail = new SimpleStringProperty(agtemail);
        this.agtposition = new SimpleStringProperty(agtposition);
        this.agencyid = new SimpleIntegerProperty(agencyid);
        this.agency = new SimpleStringProperty(agency);
        this.role = new SimpleStringProperty(role);
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

    public String getAgtfirstname() {
        return agtfirstname.get();
    }

    public SimpleStringProperty agtfirstnameProperty() {
        return agtfirstname;
    }

    public void setAgtfirstname(String agtfirstname) {
        this.agtfirstname.set(agtfirstname);
    }

    public String getAgtmiddleinitial() {
        return agtmiddleinitial.get();
    }

    public SimpleStringProperty agtmiddleinitialProperty() {
        return agtmiddleinitial;
    }

    public void setAgtmiddleinitial(String agtmiddleinitial) {
        this.agtmiddleinitial.set(agtmiddleinitial);
    }

    public String getAgtlastname() {
        return agtlastname.get();
    }

    public SimpleStringProperty agtlastnameProperty() {
        return agtlastname;
    }

    public void setAgtlastname(String agtlastname) {
        this.agtlastname.set(agtlastname);
    }

    public String getAgtbusphone() {
        return agtbusphone.get();
    }

    public SimpleStringProperty agtbusphoneProperty() {
        return agtbusphone;
    }

    public void setAgtbusphone(String agtbusphone) {
        this.agtbusphone.set(agtbusphone);
    }

    public String getAgtemail() {
        return agtemail.get();
    }

    public SimpleStringProperty agtemailProperty() {
        return agtemail;
    }

    public void setAgtemail(String agtemail) {
        this.agtemail.set(agtemail);
    }

    public String getAgtposition() {
        return agtposition.get();
    }

    public SimpleStringProperty agtpositionProperty() {
        return agtposition;
    }

    public void setAgtposition(String agtposition) {
        this.agtposition.set(agtposition);
    }

    public int getAgencyid() {
        return agencyid.get();
    }

    public SimpleIntegerProperty agencyidProperty() {
        return agencyid;
    }

    public void setAgencyid(int agencyid) {
        this.agencyid.set(agencyid);
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

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }
}
