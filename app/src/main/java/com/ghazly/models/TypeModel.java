package com.ghazly.models;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Locale;

public class TypeModel implements Serializable {

    private String id;
    private String arname;
    private String enname;


    TypeModel() {
    }

    public TypeModel(String id, String arname,String enname) {
        this.arname = arname;
        this.id=id;
        this.enname=enname;

    }

    public String getId() {
        return id;
    }

    public String getArname() {
        return arname;
    }

    public String getEnname() {
        return enname;
    }
}
