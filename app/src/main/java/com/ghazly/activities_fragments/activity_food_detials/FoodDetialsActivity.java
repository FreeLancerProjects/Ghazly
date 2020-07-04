package com.ghazly.activities_fragments.activity_food_detials;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ghazly.R;
import com.ghazly.databinding.ActivityFoodDetailsBinding;
import com.ghazly.databinding.ActivityFoodListBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;

import java.util.Locale;

import io.paperdb.Paper;

public class FoodDetialsActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityFoodDetailsBinding binding;
    private String lang;

    private LinearLayoutManager manager;
    private boolean isLoading = false;
    private int current_page2 = 1;
    private Preferences preferences;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Locale.getDefault().getLanguage()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_details);
        initView();


    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        manager = new LinearLayoutManager(this);




    }


    @Override
    public void back() {
        finish();
    }

}
