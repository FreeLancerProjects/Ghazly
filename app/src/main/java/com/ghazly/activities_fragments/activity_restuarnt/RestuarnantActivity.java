package com.ghazly.activities_fragments.activity_restuarnt;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_foodlist.FoodListActivity;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.activities_fragments.activity_restuarnt.fragments.Fragment_Book;
import com.ghazly.activities_fragments.activity_restuarnt.fragments.Fragment_Convenience;
import com.ghazly.adapters.ViewPagerAdapter;
import com.ghazly.databinding.ActivityRestuarantBinding;
import com.ghazly.language.Language;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestuarnantActivity extends AppCompatActivity {
    private ActivityRestuarantBinding binding;
    private Preferences preferences;
    private FragmentManager fragmentManager;
    private UserModel userModel;
    private String lang;
    private ActionBarDrawerToggle toggle;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> title;
    private String restaurand_id;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Locale.getDefault().getLanguage()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restuarant);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            restaurand_id = intent.getStringExtra("restaurand_id");

        }
    }
    private void initView() {
        fragmentManager = getSupportFragmentManager();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);


        fragmentList = new ArrayList<>();
        title = new ArrayList<>();
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(2);

        fragmentList.add(Fragment_Book.newInstance());
        fragmentList.add(Fragment_Convenience.newInstance());
        title.add(getString(R.string.book));
        title.add(getString(R.string.convenience));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments(fragmentList);
        adapter.addTitles(title);
        binding.pager.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        finish();
    }


    public void showDepartmentlist() {
        Intent intent=new Intent(RestuarnantActivity.this, FoodListActivity.class);
        intent.putExtra("restaurand_id",restaurand_id);
        startActivity(intent);
    }
}
