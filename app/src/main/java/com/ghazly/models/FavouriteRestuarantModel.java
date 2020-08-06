package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class FavouriteRestuarantModel implements Serializable {
    private List<Data> data;
    private int current_page;

    public List<Data> getData() {
        return data;
    }

    public int getCurrent_page() {
        return current_page;
    }
    public class Data implements Serializable{
        private SingleRestaurantModel restaurant;

        public SingleRestaurantModel getRestaurant() {
            return restaurant;
        }
    }

}
