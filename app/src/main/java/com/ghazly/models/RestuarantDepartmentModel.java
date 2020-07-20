package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class RestuarantDepartmentModel implements Serializable {
    private List<Data> data;
    private int current_page;

    public List<Data> getData() {
        return data;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public class Data implements Serializable {
        private int id;
        private String department_id;
        private String restaurant_id;
        private Department department;

        public int getId() {
            return id;
        }

        public String getDepartment_id() {
            return department_id;
        }

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public Department getDepartment() {
            return department;
        }

        public class Department implements Serializable {
            private int id;
            private String title;
            private String background;
            private String image = null;
            private String icon;
            private String details = null;
            private String parent = null;
            private String level;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBackground() {
                return background;
            }

            public String getImage() {
                return image;
            }

            public String getIcon() {
                return icon;
            }

            public String getDetails() {
                return details;
            }

            public String getParent() {
                return parent;
            }

            public String getLevel() {
                return level;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }
    }
}
