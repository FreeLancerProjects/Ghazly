package com.ghazly.models;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class CityModel implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable {
        private String city_id;
        private City city;

        public String getCity_id() {
            return city_id;
        }

        public City getCity() {
            return city;
        }

        public class City implements Serializable {
            private String id_city;
            private String ar_city_title;
            private String en_city_title;
            private String province_id_fk;
            private String country_id_fk;

            public String getId_city() {
                return id_city;
            }

            public void setId_city(String id_city) {
                this.id_city = id_city;
            }

            public String getAr_city_title() {
                return ar_city_title;
            }

            public void setAr_city_title(String ar_city_title) {
                this.ar_city_title = ar_city_title;
            }

            public String getEn_city_title() {
                return en_city_title;
            }

            public void setEn_city_title(String en_city_title) {
                this.en_city_title = en_city_title;
            }

            public String getProvince_id_fk() {
                return province_id_fk;
            }

            public void setProvince_id_fk(String province_id_fk) {
                this.province_id_fk = province_id_fk;
            }

            public String getCountry_id_fk() {
                return country_id_fk;
            }

            public void setCountry_id_fk(String country_id_fk) {
                this.country_id_fk = country_id_fk;
            }
        }
    }
}