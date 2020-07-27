package com.ghazly.models;

import java.io.Serializable;

public class ChooseSnakListModel implements Serializable {
    private String snak_id;
    private String snak_name;
    private String price;
    private String amount;

    public String getSnak_id() {
        return snak_id;
    }

    public void setSnak_id(String snak_id) {
        this.snak_id = snak_id;
    }

    public String getSnak_name() {
        return snak_name;
    }

    public void setSnak_name(String snak_name) {
        this.snak_name = snak_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
