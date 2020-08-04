package com.ghazly.models;

import java.io.Serializable;

public class DrinkModel implements Serializable {
    private int id;
    private String order_id;
    private String drink_id;
    private String drink_name;
    private String amount;
    private String price;

    public int getId() {
        return id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDrink_id() {
        return drink_id;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public String getAmount() {
        return amount;
    }

    public String getPrice() {
        return price;
    }
}
