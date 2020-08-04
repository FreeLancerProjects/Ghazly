package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class FoodListModel implements Serializable {
    private List<SingleFoodModel> data;
    private int current_page;

    public List<SingleFoodModel> getData() {
        return data;
    }

    public int getCurrent_page() {
        return current_page;
    }

}
