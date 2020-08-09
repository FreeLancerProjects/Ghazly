package com.ghazly.activities_fragments.activity_order_details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ghazly.R;
import com.ghazly.adapters.DrinkAdapter;
import com.ghazly.adapters.FoodAdapter;
import com.ghazly.databinding.ActivityOrderDetailsBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.SingleOrderModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.ghazly.remote.Api;
import com.ghazly.share.Common;
import com.ghazly.tags.Tags;

import java.io.IOException;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityOrderDetailsBinding binding;
    private String lang;
    private SingleOrderModel orderModel;
    private FoodAdapter foodAdapter;
    private DrinkAdapter drinkAdapter;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        Paper.init(this);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        orderModel = (SingleOrderModel) intent.getSerializableExtra("data");

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setOrderModel(orderModel);
        binding.setBackListener(this);
        binding.recViewFoods.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewDrink.setLayoutManager(new LinearLayoutManager(this));

        getOrder();
        binding.tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelorder();
            }
        });

    }

    private void updateUi() {
        if (orderModel.getFoods() != null && orderModel.getFoods().size() > 0) {
            foodAdapter = new FoodAdapter(this, orderModel.getFoods());
            binding.recViewFoods.setAdapter(foodAdapter);
        } else {

        }

        if (!orderModel.getOrder_status().equals("new")) {
            binding.tvcancel.setVisibility(View.GONE);
        }
        if (orderModel.getDrinks() != null && orderModel.getDrinks().size() > 0) {
            drinkAdapter = new DrinkAdapter(this, orderModel.getDrinks());
            binding.recViewDrink.setAdapter(drinkAdapter);
            binding.tvNoDrink.setVisibility(View.GONE);

        } else {
            // binding.tv
            //  binding.tvNoDrink.setVisibility(View.VISIBLE);
        }


    }

    private void getOrder() {
        try {

            Api.getService(Tags.base_url)
                    .getOrdersById(userModel.getUser().getToken(), orderModel.getId())
                    .enqueue(new Callback<SingleOrderModel>() {
                        @Override
                        public void onResponse(Call<SingleOrderModel> call, Response<SingleOrderModel> response) {

                            if (response.isSuccessful() && response.body() != null) {
                                orderModel = response.body();
                                binding.setOrderModel(orderModel);
                                updateUi();
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(OrderDetailsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(OrderDetailsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SingleOrderModel> call, Throwable t) {
                            try {


                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(OrderDetailsActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(OrderDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

    public void cancelorder() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);

        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        // rec_sent.setVisibility(View.GONE);
        try {
            String branchid;
            if (orderModel.getBranch_id() == null) {
                branchid = "0";
            } else {
                branchid = orderModel.getBranch_id();
            }
            Log.e("lllll", branchid);
            Api.getService(Tags.base_url)
                    .DelteOrders(userModel.getUser().getToken(), branchid + "", orderModel.getId() + "", orderModel.getRestaurant_id(), orderModel.getUser_id())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();

                            //  binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body() != null) {
                                //binding.coord1.scrollTo(0,0);
                                finish();
                            } else {


                                Toast.makeText(OrderDetailsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (Exception e) {
                                    Log.e("Error_code", response.code() + "");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            try {

                                dialog.dismiss();

                                Toast.makeText(OrderDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

            dialog.dismiss();
        }
    }

    @Override
    public void back() {
        finish();
    }
}
