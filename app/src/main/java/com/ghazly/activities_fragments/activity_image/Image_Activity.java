package com.ghazly.activities_fragments.activity_image;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.ghazly.R;
import com.ghazly.adapters.SlidingMenuImage_Adapter;
import com.ghazly.adapters.SlidingRestuarnatImage_Adapter;
import com.ghazly.databinding.ActivityImageBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.SingleRestaurantModel;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class Image_Activity extends AppCompatActivity implements Listeners.BackListener {

    private ActivityImageBinding binding;
    private List<SingleRestaurantModel.MenuImages> menuImagesList;
    private List<SingleRestaurantModel.RestaurantImages> restaurantImages;
    private String lang;
    private int current_page = 0, NUM_PAGES;
    private SlidingMenuImage_Adapter singleslidingImage__adapter;
    private SlidingRestuarnatImage_Adapter slidingRestuarnatImage_adapter;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        getdatafromintent();
        initView();

    }

    private void getdatafromintent() {
        if (getIntent().getStringExtra("type").equals("1")) {
            menuImagesList = ((SingleRestaurantModel) (getIntent().getSerializableExtra("single_rest"))).getMenu_images();
        } else {
            restaurantImages = ((SingleRestaurantModel) (getIntent().getSerializableExtra("single_rest"))).getRestaurant_images();

        }
    }

    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());


        binding.setBackListener(this);
        binding.setLang(lang);
       // Log.e("flllflf",menuImagesList.size()+"");
        if (menuImagesList != null && menuImagesList.size() > 0) {
            NUM_PAGES = menuImagesList.size();
            singleslidingImage__adapter = new SlidingMenuImage_Adapter(this, menuImagesList);
            binding.pager.setAdapter(singleslidingImage__adapter);
        } else if (restaurantImages != null && restaurantImages.size() > 0) {
            NUM_PAGES = restaurantImages.size();
            slidingRestuarnatImage_adapter = new SlidingRestuarnatImage_Adapter(this, restaurantImages);
            binding.pager.setAdapter(slidingRestuarnatImage_adapter);
        }

        change_slide_image();


    }

    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page == NUM_PAGES) {
                    current_page = 0;
                }
                binding.pager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    @Override
    public void back() {
        finish();
    }


}
