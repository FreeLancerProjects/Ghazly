package com.ghazly.models;

import java.io.Serializable;

public class ClientModel implements Serializable {
    private String id;
    private String user_type;
    private String phone_code;
    private String phone;
    private String name;
    private String email;
    private String logo;


    public String getId() {
        return id;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo() {
        return logo;
    }
}
