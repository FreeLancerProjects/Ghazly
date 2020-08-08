package com.ghazly.activities_fragments.drinks_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.adapters.DrinksAdapter;
import com.ghazly.databinding.ActivityDrinksDetailsBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.ChooseDrinksListModel;
import com.ghazly.models.ChooseSnakListModel;
import com.ghazly.models.CreateOrderModel;
import com.ghazly.models.SingleRestaurantModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class DrinksActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityDrinksDetailsBinding binding;
    private String lang;
    private DrinksAdapter drinksAdapter;
    private List<SingleRestaurantModel.Drinks> drinksList;
    private List<ChooseDrinksListModel> chooseDrinksListModels;
    private SingleRestaurantModel restaurantModel;
    private Preferences preferences;
    private UserModel userModel;
    private int drinkamount = 0;
    private List<Integer> ids = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drinks_details);
        getDataFromIntent();
        initView();


    }

    private void initView() {
        chooseDrinksListModels = new ArrayList<>();
        drinksList = new ArrayList<>();
        if (restaurantModel != null) {
            drinksList.addAll(restaurantModel.getDrinks());
        }
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        drinksAdapter = new DrinksAdapter(drinksList, this);
        binding.recviewdrinks.setLayoutManager(new LinearLayoutManager(this));
        binding.recviewdrinks.setAdapter(drinksAdapter);
        binding.tvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdata();
            }
        });
    }

    private void checkdata() {
        double totlaprice = 0.0;
        if (drinksList != null && drinksList.size() > 0) {
            for (int i = 0; i < drinksAdapter.getItemCount(); i++) {

                RecyclerView.ViewHolder view = binding.recviewdrinks.findViewHolderForAdapterPosition(i);
                CheckBox checkBox = view.itemView.findViewById(R.id.ch_choose);
                if (checkBox.isChecked()) {
                    TextView tvcounter = view.itemView.findViewById(R.id.tvCounter);
                    ChooseDrinksListModel chooseDrinksListModel = new ChooseDrinksListModel();
                    int amount = Integer.parseInt(tvcounter.getText().toString());
                    totlaprice += amount * Double.parseDouble(drinksList.get(i).getPrice());
                    chooseDrinksListModel.setAmount(amount + "");
                    chooseDrinksListModel.setDrink_id(drinksList.get(i).getId() + "");
                    chooseDrinksListModel.setPrice((amount * Double.parseDouble(drinksList.get(i).getPrice())) + "");
                    chooseDrinksListModel.setDrink_name(drinksList.get(i).getTitle());
                    chooseDrinksListModels.add(chooseDrinksListModel);
                }
            }
            CreateOrderModel createOrderModel = new CreateOrderModel();
            createOrderModel.setDrinks(chooseDrinksListModels);
            createOrderModel.setTotal_price(totlaprice + "");
            Intent intent = getIntent();
            intent.putExtra("data", createOrderModel);
            setResult(RESULT_OK, intent);
        }
        finish();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            restaurantModel = (SingleRestaurantModel) intent.getSerializableExtra("data");

        }
    }


    @Override
    public void back() {
        finish();
    }

    public void setsnaksamount(boolean x, int id) {
        if (x) {
            if (drinkamount < 2) {
                drinkamount += 1;
                ids.add(id);
            } else {
                Toast.makeText(DrinksActivity.this, getResources().getString(R.string.choose_untill_2), Toast.LENGTH_LONG).show();
            }
        } else {
            drinkamount -= 1;
            ids.remove(ids.indexOf(id));
        }

    }
}
