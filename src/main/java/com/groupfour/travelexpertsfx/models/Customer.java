package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty customerid;
    private SimpleStringProperty custfirstname;
    private SimpleStringProperty custlastname;
    private SimpleStringProperty custaddress;
    private SimpleStringProperty custcity;
    private SimpleStringProperty custprov;
    private SimpleStringProperty custpostal;
    private SimpleStringProperty custcountry;
    private SimpleStringProperty custhomephone;
    private SimpleStringProperty custbusphone;
    private SimpleStringProperty custemail;
    private SimpleIntegerProperty agentid;

    public Customer(int customerid,
                    String custfirstname,
                    String custlastname,
                    String custaddress,
                    String custcity,
                    String custprov,
                    String custpostal,
                    String custcountry,
                    String custhomephone,
                    String custbusphone,
                    String custemail,
                    Integer agentid) {
        this.customerid = new SimpleIntegerProperty(customerid);
        this.custfirstname = new SimpleStringProperty(custfirstname);
        this.custlastname = new SimpleStringProperty(custlastname);
        this.custaddress = new SimpleStringProperty(custaddress);
        this.custcity = new SimpleStringProperty(custcity);
        this.custprov = new SimpleStringProperty(custprov);
        this.custpostal = new SimpleStringProperty(custpostal);
        this.custcountry = new SimpleStringProperty(custcountry);
        this.custhomephone = new SimpleStringProperty(custhomephone);
        this.custbusphone = new SimpleStringProperty(custbusphone);
        this.custemail = new SimpleStringProperty(custemail);
        this.agentid = new SimpleIntegerProperty(agentid);
    }

    public int getCustomerid() {
        return customerid.get();
    }

    public SimpleIntegerProperty customeridProperty() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid.set(customerid);
    }

    public String getCustfirstname() {
        return custfirstname.get();
    }

    public SimpleStringProperty custfirstnameProperty() {
        return custfirstname;
    }

    public void setCustfirstname(String custfirstname) {
        this.custfirstname.set(custfirstname);
    }

    public String getCustlastname() {
        return custlastname.get();
    }

    public SimpleStringProperty custlastnameProperty() {
        return custlastname;
    }

    public void setCustlastname(String custlastname) {
        this.custlastname.set(custlastname);
    }

    public String getCustaddress() {
        return custaddress.get();
    }

    public SimpleStringProperty custaddressProperty() {
        return custaddress;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress.set(custaddress);
    }

    public String getCustcity() {
        return custcity.get();
    }

    public SimpleStringProperty custcityProperty() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity.set(custcity);
    }

    public String getCustprov() {
        return custprov.get();
    }

    public SimpleStringProperty custprovProperty() {
        return custprov;
    }

    public void setCustprov(String custprov) {
        this.custprov.set(custprov);
    }

    public String getCustpostal() {
        return custpostal.get();
    }

    public SimpleStringProperty custpostalProperty() {
        return custpostal;
    }

    public void setCustpostal(String custpostal) {
        this.custpostal.set(custpostal);
    }

    public String getCustcountry() {
        return custcountry.get();
    }

    public SimpleStringProperty custcountryProperty() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry.set(custcountry);
    }

    public String getCusthomephone() {
        return custhomephone.get();
    }

    public SimpleStringProperty custhomephoneProperty() {
        return custhomephone;
    }

    public void setCusthomephone(String custhomephone) {
        this.custhomephone.set(custhomephone);
    }

    public String getCustbusphone() {
        return custbusphone.get();
    }

    public SimpleStringProperty custbusphoneProperty() {
        return custbusphone;
    }

    public void setCustbusphone(String custbusphone) {
        this.custbusphone.set(custbusphone);
    }

    public String getCustemail() {
        return custemail.get();
    }

    public SimpleStringProperty custemailProperty() {
        return custemail;
    }

    public void setCustemail(String custemail) {
        this.custemail.set(custemail);
    }

    public int getAgentid() {
        return agentid.get();
    }

    public SimpleIntegerProperty agentidProperty() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid.set(agentid);
    }
}
