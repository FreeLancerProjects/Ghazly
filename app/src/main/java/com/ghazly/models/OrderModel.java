package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private int current_page;

    private List<SingleOrderModel> data;

    public int getCurrent_page() {
        return current_page;
    }

    public List<SingleOrderModel> getData() {
        return data;
    }
}
