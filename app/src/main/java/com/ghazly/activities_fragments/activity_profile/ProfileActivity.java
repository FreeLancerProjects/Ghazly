package com.ghazly.activities_fragments.activity_profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.ghazly.R;
import com.ghazly.databinding.ActivityProfileBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;

import java.util.Locale;
import java.util.Random;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityProfileBinding binding;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        initView();
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
binding.btnlogout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=getIntent();
        intent.putExtra("logout","logout");
        setResult(RESULT_OK, intent);
        finish();
    }
});

    }


    @Override
    public void back() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(preferences!=null){
            userModel=preferences.getUserData(this);
        }
    }
}
