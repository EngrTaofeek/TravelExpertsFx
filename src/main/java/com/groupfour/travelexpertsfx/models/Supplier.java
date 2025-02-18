package com.groupfour.travelexpertsfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty id;
    private SimpleStringProperty supname;

    public Supplier(Integer id, String supname) {
        this.id = new SimpleIntegerProperty(id);
        this.supname = new SimpleStringProperty(supname);
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

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", supname=" + supname +
                '}';
    }

    public String getSupname() {
        return supname.get();
    }

    public SimpleStringProperty supnameProperty() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname.set(supname);
    }
}
