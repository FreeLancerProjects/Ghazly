package com.ghazly.interfaces;

public interface Listeners {
    interface BackListener {
        void back();
    }

    interface LoginListener {
        void validate();

        void showCountryDialog();
    }

    interface SignUpListener {


        void checkDataValid();

    }

    interface HomeActions {
        void order();

        void terms();

        void aboutApp();

        void profile();

        void contactus();
        void citydialog();
    }

    interface RestaurantActions {
        void order();

        void increasechild();

        void decreasechild();

        void increasepeople();

        void decreasepeople();
    }
    interface contactActions {
        void email();

        void call();

        void whats();

        void twitter();

    }


}
