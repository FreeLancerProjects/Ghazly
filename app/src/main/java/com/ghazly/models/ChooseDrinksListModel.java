package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class ChooseDrinksListModel implements Serializable {
   private String drink_id;
    private String drink_name;
    private String price;
    private String amount;

    public String getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(String drink_id) {
        this.drink_id = drink_id;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
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

