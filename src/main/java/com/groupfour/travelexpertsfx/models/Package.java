package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @Author DarylWxc
 * @Date 2/13/2025
 * @Description Package Model
 */

public class Package {
    private SimpleIntegerProperty id;
    private SimpleStringProperty pkgname;
    private SimpleObjectProperty<LocalDate> pkgstartdate;
    private SimpleObjectProperty<LocalDate>  pkgenddate;
    private SimpleStringProperty pkgdesc;
    private SimpleDoubleProperty pkgbaseprice;
    private SimpleDoubleProperty pkgagencycommission;

    public Package(int id, String pkgname, Double pkgagencycommission, Double pkgbaseprice, Date pkgenddate, Date pkgstartdate, String pkgdesc) {
        this.id = new SimpleIntegerProperty(id);
        this.pkgname = new SimpleStringProperty(pkgname);
        this.pkgagencycommission = new SimpleDoubleProperty(pkgagencycommission);
        this.pkgbaseprice = new SimpleDoubleProperty(pkgbaseprice);
        this.pkgenddate = new SimpleObjectProperty<LocalDate> (pkgenddate.toLocalDate());
        this.pkgstartdate = new SimpleObjectProperty<LocalDate> (pkgstartdate.toLocalDate());
        this.pkgdesc = new SimpleStringProperty(pkgdesc);
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

    public String getPkgname() {
        return pkgname.get();
    }

    public SimpleStringProperty pkgnameProperty() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname.set(pkgname);
    }

    public String getPkgdesc() {
        return pkgdesc.get();
    }

    public SimpleStringProperty pkgdescProperty() {
        return pkgdesc;
    }

    public void setPkgdesc(String pkgdesc) {
        this.pkgdesc.set(pkgdesc);
    }

    public double getPkgbaseprice() {
        return pkgbaseprice.get();
    }

    public SimpleDoubleProperty pkgbasepriceProperty() {
        return pkgbaseprice;
    }

    public void setPkgbaseprice(double pkgbaseprice) {
        this.pkgbaseprice.set(pkgbaseprice);
    }

    public double getPkgagencycommission() {
        return pkgagencycommission.get();
    }

    public SimpleDoubleProperty pkgagencycommissionProperty() {
        return pkgagencycommission;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", pkgname=" + pkgname +
                ", pkgstartdate=" + pkgstartdate +
                ", pkgenddate=" + pkgenddate +
                ", pkgdesc=" + pkgdesc +
                ", pkgbaseprice=" + pkgbaseprice +
                ", pkgagencycommission=" + pkgagencycommission +
                '}';
    }

    public void setPkgagencycommission(double pkgagencycommission) {
        this.pkgagencycommission.set(pkgagencycommission);
    }

    public LocalDate getPkgstartdate() {
        return pkgstartdate.get();
    }

    public SimpleObjectProperty<LocalDate> pkgstartdateProperty() {
        return pkgstartdate;
    }

    public void setPkgstartdate(LocalDate pkgstartdate) {
        this.pkgstartdate.set(pkgstartdate);
    }

    public LocalDate getPkgenddate() {
        return pkgenddate.get();
    }

    public SimpleObjectProperty<LocalDate> pkgenddateProperty() {
        return pkgenddate;
    }

    public void setPkgenddate(LocalDate pkgenddate) {
        this.pkgenddate.set(pkgenddate);
    }
}
