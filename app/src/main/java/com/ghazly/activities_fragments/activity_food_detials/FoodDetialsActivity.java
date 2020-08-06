package com.ghazly.activities_fragments.activity_food_detials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ghazly.R;
import com.ghazly.adapters.SnaksAdapter;
import com.ghazly.databinding.ActivityFoodDetailsBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.ChooseFoodListModel;
import com.ghazly.models.ChooseSnakListModel;
import com.ghazly.models.FoodListModel;
import com.ghazly.models.SingleFoodModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class FoodDetialsActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityFoodDetailsBinding binding;
    private String lang;
    private SnaksAdapter snaksAdapter;
    private List<SingleFoodModel.Snaks> snaksList;
    private List<ChooseSnakListModel> chooseSnakListModels;
    private SingleFoodModel fData;
    private Preferences preferences;
    private UserModel userModel;
    private int amount = 1, snakamount = 0;
    private List<Integer> ids = new ArrayList<>();
    private double totalprice = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_details);
        getDataFromIntent();
        initView();


    }

    private void initView() {
        chooseSnakListModels=new ArrayList<>();
        snaksList = new ArrayList<>();
        if (fData.getSnaks() != null) {
            snaksList.addAll(fData.getSnaks());
        }
        totalprice += Double.parseDouble(fData.getPrice());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        snaksAdapter = new SnaksAdapter(snaksList, this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(snaksAdapter);
        binding.setModel(fData);
        binding.imgDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount > 1) {
                    totalprice = (totalprice) - (totalprice / amount);
                    amount -= 1;
                    binding.tvCounter.setText(amount + "");

                    binding.tvtotal.setText(totalprice + getResources().getString(R.string.ryal));
                }
            }
        });
        binding.imgIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalprice = (totalprice) + (totalprice / amount);

                amount += 1;
                binding.tvCounter.setText(amount + "");
                binding.tvtotal.setText(totalprice + getResources().getString(R.string.ryal));

            }
        });
        binding.tvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
            }
        });
    }

    private void checkdata() {
        for(int i=0;i<snaksList.size();i++){
if(ids.contains(snaksList.get(i).getId())){
    ChooseSnakListModel chooseSnakListModel=new ChooseSnakListModel();
    chooseSnakListModel.setAmount(amount+"");
    chooseSnakListModel.setPrice((amount*Double.parseDouble(snaksList.get(i).getPrice()))+"");
    chooseSnakListModel.setSnak_id(snaksList.get(i).getId()+"");
    chooseSnakListModel.setSnak_name(snaksList.get(i).getTitle());
    chooseSnakListModels.add(chooseSnakListModel);
}
        }
        ChooseFoodListModel chooseFoodListModel=new ChooseFoodListModel();
        chooseFoodListModel.setSnaks(chooseSnakListModels);
        chooseFoodListModel.setAmount(amount+"");
        chooseFoodListModel.setTitle(fData.getTitle());
        chooseFoodListModel.setFood_id(fData.getId()+"");
        chooseFoodListModel.setPrice(totalprice+"");
        Intent intent=getIntent();
        intent.putExtra("data", chooseFoodListModel);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            fData = (SingleFoodModel) intent.getSerializableExtra("data");

        }
    }


    @Override
    public void back() {
        finish();
    }

    public void setsnaksamount(boolean x, int id, double price) {
        price *= amount;
        if (x) {
            if (snakamount < 2) {
                snakamount += 1;
                ids.add(id);
                totalprice += price;
            } else {
                Toast.makeText(FoodDetialsActivity.this, getResources().getString(R.string.choose_untill_2), Toast.LENGTH_LONG).show();
            }
        } else {
            snakamount -= 1;
            ids.remove(ids.indexOf(id));
            totalprice -= price;
        }
        binding.tvtotal.setText(totalprice + getResources().getString(R.string.ryal));

    }
}
