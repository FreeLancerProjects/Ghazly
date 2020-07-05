package com.ghazly.activities_fragments.activity_complete_order;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ghazly.R;
import com.ghazly.databinding.ActivityCompleteOrderBinding;
import com.ghazly.databinding.ActivityFoodDetailsBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;

import java.util.Locale;

import io.paperdb.Paper;

public class CompleteOrderActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityCompleteOrderBinding binding;
    private String lang;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_order);
        initView();


    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);




    }


    @Override
    public void back() {
        finish();
    }

}
