package com.ghazly.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private User user;
    private CopunData data;

    public User getUser() {
        return user;
    }

    public CopunData getData() {
        return data;
    }

    public static class User implements Serializable {
        private int id;
        private String name;
        private String email;
        private String user_type;
        private String phone_code;
        private String phone;
        private String logo;
        private String latitude;
        private String longitude;
        private String address;
        private String block;
        private String login;
        private String logout_time;
        private String is_confirmed;
        private String confirmed_code;
        private String forget_password_code;
        private String software_type;
        private String email_verified_at;
        private String created_at;
        private String updated_at;
        private String token;
        private String gender;
        private String date_of_barth;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
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

        public String getLogo() {
            return logo;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getBlock() {
            return block;
        }

        public String getLogin() {
            return login;
        }

        public String getLogout_time() {
            return logout_time;
        }

        public String getIs_confirmed() {
            return is_confirmed;
        }

        public String getConfirmed_code() {
            return confirmed_code;
        }

        public String getForget_password_code() {
            return forget_password_code;
        }

        public String getSoftware_type() {
            return software_type;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getToken() {
            return token;
        }

        public String getGender() {
            return gender;
        }

        public String getDate_of_barth() {
            return date_of_barth;
        }
    }

    public class CopunData implements Serializable {
        private int id;
        private String coupon_num;
        private String coupon_value;
        private String from_date;
        private String to_date;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getCoupon_num() {
            return coupon_num;
        }

        public String getCoupon_value() {
            return coupon_value;
        }

        public String getFrom_date() {
            return from_date;
        }

        public String getTo_date() {
            return to_date;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
