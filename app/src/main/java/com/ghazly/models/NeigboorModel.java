package com.ghazly.models;

import java.io.Serializable;
import java.util.List;

public class NeigboorModel implements Serializable {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable {
        private String neighbor_id;
        private Neighbor neighbor;


        public String getNeighbor_id() {
            return neighbor_id;
        }

        public Neighbor getNeighbor() {
            return neighbor;
        }

        public class Neighbor implements Serializable {
            private String id_neighborhood;
            private String ar_neighborhood;
            private String en_neighborhood;

            public String getId_neighborhood() {
                return id_neighborhood;
            }

            public String getAr_neighborhood() {
                return ar_neighborhood;
            }

            public String getEn_neighborhood() {
                return en_neighborhood;
            }
        }
    }
}