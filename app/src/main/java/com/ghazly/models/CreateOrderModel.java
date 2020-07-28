package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class CreateOrderModel implements Serializable {
    private String user_id;
    private String branch_id;
    private String restaurant_id;
    private String coupon_id;
    private String total_price;
    private String order_date;
    private String order_time;
    private String pay_type;
    private String number_of_person;
    private String number_of_child;
    private String session_place;
    private String session_type;
    private List<ChooseFoodListModel> foods;
    private List<ChooseDrinksListModel> drinks;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getNumber_of_person() {
        return number_of_person;
    }

    public void setNumber_of_person(String number_of_person) {
        this.number_of_person = number_of_person;
    }

    public String getNumber_of_child() {
        return number_of_child;
    }

    public void setNumber_of_child(String number_of_child) {
        this.number_of_child = number_of_child;
    }

    public String getSession_place() {
        return session_place;
    }

    public void setSession_place(String session_place) {
        this.session_place = session_place;
    }

    public String getSession_type() {
        return session_type;
    }

    public void setSession_type(String session_type) {
        this.session_type = session_type;
    }

    public List<ChooseFoodListModel> getFoods() {
        return foods;
    }

    public void setFoods(List<ChooseFoodListModel> foods) {
        this.foods = foods;
    }

    public List<ChooseDrinksListModel> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<ChooseDrinksListModel> drinks) {
        this.drinks = drinks;
    }
}

