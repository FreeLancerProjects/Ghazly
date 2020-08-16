package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class SingleRestaurantModel implements Serializable {

    private int id;
    private String department_id;
    private String admin_type;
    private String name;
    private String user_name = null;
    private String email;
    private String phone_code;
    private String phone;
    private String parent = null;
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
    private String is_login = null;
    private String group_id = null;
    private String created_at;
    private String updated_at;
    private String share_link;
    private List<MenuImages> menu_images;
    private List<RestaurantImages> restaurant_images;
    private City city;
    private Neighbor neighbor;
    private String branch_data_count;
    private List<Branchs> branchs;
    private List<Drinks> drinks;

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

    public String getEmail() {
        return email;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public String getParent() {
        return parent;
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

    public String getShare_link() {
        return share_link;
    }

    public List<MenuImages> getMenu_images() {
        return menu_images;
    }

    public List<RestaurantImages> getRestaurant_images() {
        return restaurant_images;
    }

    public City getCity() {
        return city;
    }

    public Neighbor getNeighbor() {
        return neighbor;
    }

    public String getBranch_data_count() {
        return branch_data_count;
    }

    public List<Branchs> getBranchs() {
        return branchs;
    }

    public List<Drinks> getDrinks() {
        return drinks;
    }

    public class MenuImages implements Serializable {
        private int id;
        private String name;
        private String size;
        private String file;
        private String path;
        private String full_file;
        private String mime_type;
        private String file_type;
        private String relation_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSize() {
            return size;
        }

        public String getFile() {
            return file;
        }

        public String getPath() {
            return path;
        }

        public String getFull_file() {
            return full_file;
        }

        public String getMime_type() {
            return mime_type;
        }

        public String getFile_type() {
            return file_type;
        }

        public String getRelation_id() {
            return relation_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public class RestaurantImages implements Serializable {
        private int id;
        private String name;
        private String size;
        private String file;
        private String path;
        private String full_file;
        private String mime_type;
        private String file_type;
        private String relation_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSize() {
            return size;
        }

        public String getFile() {
            return file;
        }

        public String getPath() {
            return path;
        }

        public String getFull_file() {
            return full_file;
        }

        public String getMime_type() {
            return mime_type;
        }

        public String getFile_type() {
            return file_type;
        }

        public String getRelation_id() {
            return relation_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
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

        public String getAr_city_title() {
            return ar_city_title;
        }

        public String getEn_city_title() {
            return en_city_title;
        }

        public String getProvince_id_fk() {
            return province_id_fk;
        }

        public String getCountry_id_fk() {
            return country_id_fk;
        }
    }

    public class Neighbor implements Serializable {
        private String id_neighborhood;
        private String ar_neighborhood;
        private String en_neighborhood;
        private String city_id_fk;
        private String ne_coordinates;
        private String sw_coordinates;

        public String getId_neighborhood() {
            return id_neighborhood;
        }

        public String getAr_neighborhood() {
            return ar_neighborhood;
        }

        public String getEn_neighborhood() {
            return en_neighborhood;
        }

        public String getCity_id_fk() {
            return city_id_fk;
        }

        public String getNe_coordinates() {
            return ne_coordinates;
        }

        public String getSw_coordinates() {
            return sw_coordinates;
        }
    }

    public class Branchs implements Serializable {
        private int id;
        private String parent;
        private String name;
        private String phone;
        private String city_id;
        private String neighbor_id;
        private String latitude;
        private String longitude;
        private String address;
        private SingleRestaurantModel.City city;
        private SingleRestaurantModel.Neighbor neighbor;

        public int getId() {
            return id;
        }

        public String getParent() {
            return parent;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getCity_id() {
            return city_id;
        }

        public String getNeighbor_id() {
            return neighbor_id;
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

        public SingleRestaurantModel.City getCity() {
            return city;
        }

        public SingleRestaurantModel.Neighbor getNeighbor() {
            return neighbor;
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

            public String getAr_city_title() {
                return ar_city_title;
            }

            public String getEn_city_title() {
                return en_city_title;
            }

            public String getProvince_id_fk() {
                return province_id_fk;
            }

            public String getCountry_id_fk() {
                return country_id_fk;
            }
        }


        public class Neighbor implements Serializable {
            private String id_neighborhood;
            private String ar_neighborhood;
            private String en_neighborhood;
            private String city_id_fk;
            private String ne_coordinates;
            private String sw_coordinates;

            public String getId_neighborhood() {
                return id_neighborhood;
            }

            public String getAr_neighborhood() {
                return ar_neighborhood;
            }

            public String getEn_neighborhood() {
                return en_neighborhood;
            }

            public String getCity_id_fk() {
                return city_id_fk;
            }

            public String getNe_coordinates() {
                return ne_coordinates;
            }

            public String getSw_coordinates() {
                return sw_coordinates;
            }
        }

    }

    public class Drinks implements Serializable {
        private int id;
        private String title;
        private String restaurant_id;
        private String price;
        private String is_free;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public String getPrice() {
            return price;
        }

        public String getIs_free() {
            return is_free;
        }
    }

}


