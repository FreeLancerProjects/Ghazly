package com.ghazly.activities_fragments.activity_foodlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.adapters.DepartmentAdapter;
import com.ghazly.adapters.RestaurantDepartmentAdapter;
import com.ghazly.databinding.ActivityFoodListBinding;
import com.ghazly.databinding.ActivityMyOrdersBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.RestuarantDepartmentModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.ghazly.remote.Api;
import com.ghazly.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityFoodListBinding binding;
    private String lang;

    private LinearLayoutManager manager;
    private boolean isLoading = false;
    private int current_page2 = 1;
    private Preferences preferences;
    private UserModel userModel;
    private RestaurantDepartmentAdapter restaurantDepartmentAdapter;
    private List<RestuarantDepartmentModel.Data> resDataList;
    private String restaurand_id;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Locale.getDefault().getLanguage()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_list);
        getDataFromIntent();

        initView();
getMainCategory();
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            restaurand_id = intent.getStringExtra("restaurand_id");

        }
    }

    private void initView() {
        resDataList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        restaurantDepartmentAdapter = new RestaurantDepartmentAdapter(resDataList, this);
        binding.recViewdepart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
        binding.recViewdepart.setAdapter(restaurantDepartmentAdapter);

    }
    private void getMainCategory() {

        Api.getService(Tags.base_url)
                .getrestaurantdepartments("off",restaurand_id)
                .enqueue(new Callback<RestuarantDepartmentModel>() {
                    @Override
                    public void onResponse(Call<RestuarantDepartmentModel> call, Response<RestuarantDepartmentModel> response) {
                        binding.progBardepart.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                           // Log.e("dkkdkdk",response.body().getData().size()+"");
                            resDataList.clear();
                            resDataList.addAll(response.body().getData());

                            if (resDataList.size() > 0) {
                                restaurantDepartmentAdapter.notifyDataSetChanged();
                                binding.tvNoDatadepart.setVisibility(View.GONE);
                            } else {
                                binding.tvNoDatadepart.setVisibility(View.VISIBLE);

                            }


                        } else {
                            binding.progBardepart.setVisibility(View.GONE);

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(FoodListActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FoodListActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RestuarantDepartmentModel> call, Throwable t) {
                        try {
                            binding.progBardepart.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(FoodListActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FoodListActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }


    @Override
    public void back() {
        finish();
    }

    public void setitemData(String s) {
    }
}
