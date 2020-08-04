package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class SingleFoodModel implements Serializable {

        private int id;
        private String title;
        private String image;
        private String restaurant_id;
        private String food_departemnt_id;
        private String food_sub_departemnt_id = null;
        private String price;
        private String calories;
        private String contents;
        private String contents_tags = null;
        private String have_offer;
        private String offer_type;
        private String offer_value;
        private String created_at;
        private String updated_at;
        List < FoodsImages > foods_images ;
        List < Snaks > snaks ;


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

        public String getContents_tags() {
            return contents_tags;
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

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public List<FoodsImages> getFoods_images() {
            return foods_images;
        }

        public List<Snaks> getSnaks() {
            return snaks;
        }

        public class FoodsImages implements Serializable {
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

        public class Snaks implements Serializable {
            private int id;
            private String title;
            private String food_id;
            private String price;
            private String is_free;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getFood_id() {
                return food_id;
            }

            public String getPrice() {
                return price;
            }

            public String getIs_free() {
                return is_free;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }





}


