package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class ChooseFoodListModel implements Serializable {
    private String food_id;
    private String price;
    private String amount;
private List<ChooseSnakListModel> snaks;

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
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

    public List<ChooseSnakListModel> getSnaks() {
        return snaks;
    }

    public void setSnaks(List<ChooseSnakListModel> snaks) {
        this.snaks = snaks;
    }
}

