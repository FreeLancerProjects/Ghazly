package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class SingleOrderModel implements Serializable {
    private String id;
    private String order_code;
    private String order_status;
    private String order_type;
    private String user_id;
    private String restaurant_id;
    private String branch_id;
    private String coupon_id;
    private String total_price;
    private String latitude;
    private String longitude;
    private String order_date;
    private String order_time;
    private String pay_type;
    private String number_of_person;
    private String number_of_child;
    private String session_place;
    private String session_type;
    private String barcode_image;
    private String details;
    private Branches branches;
    private Restuarant restuarant;
    private List<FoodsModel> foods;
    private List<DrinkModel> drinks;


    public String getId() {
        return id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getNumber_of_person() {
        return number_of_person;
    }

    public String getNumber_of_child() {
        return number_of_child;
    }

    public String getSession_place() {
        return session_place;
    }

    public String getSession_type() {
        return session_type;
    }

    public String getBarcode_image() {
        return barcode_image;
    }

    public String getDetails() {
        return details;
    }


    public List<FoodsModel> getFoods() {
        return foods;
    }

    public List<DrinkModel> getDrinks() {
        return drinks;
    }

    public Branches getBranches() {
        return branches;
    }

    public Restuarant getRestuarant() {
        return restuarant;
    }

    public class FoodsModel implements Serializable {
        private int id;
        private String order_id;
        private String price;
        private String amount;
        private FoodInfoModel food_info;
        private List<SnaksModel> snaks;

        public int getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getPrice() {
            return price;
        }

        public String getAmount() {
            return amount;
        }

        public FoodInfoModel getFood_info() {
            return food_info;
        }

        public List<SnaksModel> getSnaks() {
            return snaks;
        }

        public class FoodInfoModel implements Serializable {
            private int id;
            private String title;
            private String image;
            private String restaurant_id;
            private String food_departemnt_id;
            private String food_sub_departemnt_id;
            private String price;
            private String calories;
            private String contents;
            private String have_offer;
            private String offer_type;
            private String offer_value;


            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getImage() {
                return image;
            }

            public String getRestaurant_id() {
                return restaurant_id;
            }

            public String getFood_departemnt_id() {
                return food_departemnt_id;
            }

            public String getFood_sub_departemnt_id() {
                return food_sub_departemnt_id;
            }

            public String getPrice() {
                return price;
            }

            public String getCalories() {
                return calories;
            }

            public String getContents() {
                return contents;
            }

            public String getHave_offer() {
                return have_offer;
            }

            public String getOffer_type() {
                return offer_type;
            }

            public String getOffer_value() {
                return offer_value;
            }
        }

        public class SnaksModel implements Serializable {
            private int id;
            private String order_id;
            private String detail_id;
            private String snak_id;
            private String snak_name;
            private String amount;
            private String price;

            public int getId() {
                return id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public String getDetail_id() {
                return detail_id;
            }

            public String getSnak_id() {
                return snak_id;
            }

            public String getSnak_name() {
                return snak_name;
            }

            public String getAmount() {
                return amount;
            }

            public String getPrice() {
                return price;
            }
        }
    }

    public class DrinkModel implements Serializable {
        private int id;
        private String order_id;
        private String drink_id;
        private String drink_name;
        private String amount;
        private String price;

        public int getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getDrink_id() {
            return drink_id;
        }

        public String getDrink_name() {
            return drink_name;
        }

        public String getAmount() {
            return amount;
        }

        public String getPrice() {
            return price;
        }
    }

    public class Restuarant implements Serializable {
        private int id;
        private String department_id = null;
        private String admin_type;
        private String name;
        private String user_name;
        private String parent;
        private String email;
        private String phone_code;
        private String phone;
        private String logo;
        private String banner;
        private String rating = null;
        private String latitude;
        private String longitude;
        private String address;
        private String city_id;
        private String neighbor_id;
        private String foods;
        private String min_price;
        private String max_price;
        private String child_from;
        private String child_to;
        private String work_from;
        private String work_to;
        private String session_place;
        private String session_type;
        private String days;
        private String payment_type;
        private String website_address;
        private String parking;
        private String details = null;
        private String details_tags;
        private String is_login;
        private String group_id = null;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getDepartment_id() {
            return department_id;
        }

        public String getAdmin_type() {
            return admin_type;
        }

        public String getName() {
            return name;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getParent() {
            return parent;
        }

        public String getEmail() {
            return email;
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

        public String getBanner() {
            return banner;
        }

        public String getRating() {
            return rating;
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

        public String getCity_id() {
            return city_id;
        }

        public String getNeighbor_id() {
            return neighbor_id;
        }

        public String getFoods() {
            return foods;
        }

        public String getMin_price() {
            return min_price;
        }

        public String getMax_price() {
            return max_price;
        }

        public String getChild_from() {
            return child_from;
        }

        public String getChild_to() {
            return child_to;
        }

        public String getWork_from() {
            return work_from;
        }

        public String getWork_to() {
            return work_to;
        }

        public String getSession_place() {
            return session_place;
        }

        public String getSession_type() {
            return session_type;
        }

        public String getDays() {
            return days;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public String getWebsite_address() {
            return website_address;
        }

        public String getParking() {
            return parking;
        }

        public String getDetails() {
            return details;
        }

        public String getDetails_tags() {
            return details_tags;
        }

        public String getIs_login() {
            return is_login;
        }

        public String getGroup_id() {
            return group_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public class Branches implements Serializable {

        private int id;
        private String department_id;
        private String admin_type;
        private String name;
        private String user_name = null;
        private String parent = null;
        private String email;
        private String phone_code;
        private String phone;
        private String logo;
        private String banner;
        private String rating = null;
        private String latitude;
        private String longitude;
        private String address;
        private String city_id;
        private String neighbor_id;
        private String foods;
        private String min_price;
        private String max_price;
        private String child_from;
        private String child_to;
        private String work_from;
        private String work_to;
        private String session_place;
        private String session_type;
        private String days;
        private String payment_type;
        private String website_address;
        private String parking;
        private String details;
        private String details_tags;
        private String is_login = null;
        private String group_id = null;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getDepartment_id() {
            return department_id;
        }

        public String getAdmin_type() {
            return admin_type;
        }

        public String getName() {
            return name;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getParent() {
            return parent;
        }

        public String getEmail() {
            return email;
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

        public String getBanner() {
            return banner;
        }

        public String getRating() {
            return rating;
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

        public String getCity_id() {
            return city_id;
        }

        public String getNeighbor_id() {
            return neighbor_id;
        }

        public String getFoods() {
            return foods;
        }

        public String getMin_price() {
            return min_price;
        }

        public String getMax_price() {
            return max_price;
        }

        public String getChild_from() {
            return child_from;
        }

        public String getChild_to() {
            return child_to;
        }

        public String getWork_from() {
            return work_from;
        }

        public String getWork_to() {
            return work_to;
        }

        public String getSession_place() {
            return session_place;
        }

        public String getSession_type() {
            return session_type;
        }

        public String getDays() {
            return days;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public String getWebsite_address() {
            return website_address;
        }

        public String getParking() {
            return parking;
        }

        public String getDetails() {
            return details;
        }

        public String getDetails_tags() {
            return details_tags;
        }

        public String getIs_login() {
            return is_login;
        }

        public String getGroup_id() {
            return group_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
