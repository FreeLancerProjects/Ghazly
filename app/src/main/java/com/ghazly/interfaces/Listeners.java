package com.ghazly.interfaces;
public interface Listeners {
    interface BackListener
    {
        void back();
    }
    interface LoginListener{
        void validate();
        void showCountryDialog();
    }

    interface SignUpListener{


        void checkDataValid();

    }

    interface SettingActions
    {
        void order();
        void charge();
        void returns();
        void terms();
        void aboutApp();
        void logout();
        void favorite();
        void bankAccount();
        void share();
        void rateApp();
        void arLang();
        void enLang();
        void profile();

    }


    interface PaymentTypeAction
    {
        void onCredit();
        void onPaypal();
        void onCash();
        void onNext();
        void onPrevious();
    }


    interface NextPreviousAction
    {
        void onNext();
        void onPrevious();
    }
    interface UpdateProfileListener
    {
        void updateProfile();
    }





}
