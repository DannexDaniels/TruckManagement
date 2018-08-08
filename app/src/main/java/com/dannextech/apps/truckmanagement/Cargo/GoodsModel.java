package com.dannextech.apps.truckmanagement.Cargo;

public class GoodsModel {
    String name,cargo_type,cargo_weight,route,carry_date,delivery_date,submit_date;

    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GoodsModel() {
    }

    public GoodsModel(String name, String cargo_type, String cargo_weight, String route, String carry_date, String delivery_date, String submit_date) {
        this.name = name;
        this.cargo_type = cargo_type;
        this.cargo_weight = cargo_weight;
        this.route = route;
        this.carry_date = carry_date;
        this.delivery_date = delivery_date;
        this.submit_date = submit_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCargo_type() {
        return cargo_type;
    }

    public void setCargo_type(String cargo_type) {
        this.cargo_type = cargo_type;
    }

    public String getCargo_weight() {
        return cargo_weight;
    }

    public void setCargo_weight(String cargo_weight) {
        this.cargo_weight = cargo_weight;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCarry_date() {
        return carry_date;
    }

    public void setCarry_date(String carry_date) {
        this.carry_date = carry_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }
}
