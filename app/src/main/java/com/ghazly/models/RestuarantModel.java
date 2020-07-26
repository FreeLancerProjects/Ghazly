package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class RestuarantModel implements Serializable {
    private List<SingleRestaurantModel> data;
    private int current_page;

    public List<SingleRestaurantModel> getData() {
        return data;
    }

    public int getCurrent_page() {
        return current_page;
    }

}
