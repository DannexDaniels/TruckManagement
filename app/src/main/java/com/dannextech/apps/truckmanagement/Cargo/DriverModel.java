package com.dannextech.apps.truckmanagement.Cargo;

public class DriverModel {
    private String name, id, age, phone, url,joined_date,entered_on;

    public DriverModel() {
    }

    public DriverModel(String name, String id, String age, String phone, String joined_date, String entered_on) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.phone = phone;
        this.joined_date = joined_date;
        this.entered_on = entered_on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }

    public String getEntered_on() {
        return entered_on;
    }

    public void setEntered_on(String entered_on) {
        this.entered_on = entered_on;
    }
}
