package com.dannextech.apps.truckmanagement.Cargo;

public class TruckModel {
    String name, plate, type,entered_on;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TruckModel() {
    }

    public TruckModel(String name, String plate, String type, String entered_on) {
        this.name = name;
        this.plate = plate;
        this.type = type;
        this.entered_on = entered_on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntered_on() {
        return entered_on;
    }

    public void setEntered_on(String entered_on) {
        this.entered_on = entered_on;
    }
}
