package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
  private List<SingleOrderModel> data;

    public List<SingleOrderModel> getData() {
        return data;
    }
}
