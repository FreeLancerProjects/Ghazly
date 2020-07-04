package com.ghazly.activities_fragments.activity_contactus;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.ghazly.R;
import com.ghazly.databinding.ActivityAboutAppBinding;
import com.ghazly.databinding.ActivityContactusBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;

import java.util.Locale;

import io.paperdb.Paper;

public class ContactusActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityContactusBinding binding;
    private String lang;
    private int type;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Locale.getDefault().getLanguage()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contactus);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("type")) {
            type = intent.getIntExtra("type", 0);

        }
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);



    }




    @Override
    public void back() {
        finish();
    }

}
