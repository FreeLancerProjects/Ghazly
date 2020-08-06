package com.ghazly.activities_fragments.activity_complete_order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_my_orders.MyOrderActivity;
import com.ghazly.adapters.ChooseFoodsAdapter;
import com.ghazly.databinding.ActivityCompleteOrderBinding;
import com.ghazly.databinding.ActivityFoodDetailsBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.ChooseFoodListModel;
import com.ghazly.models.CreateOrderModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.ghazly.remote.Api;
import com.ghazly.share.Common;
import com.ghazly.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteOrderActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityCompleteOrderBinding binding;
    private String lang;
    private CreateOrderModel createOrderModel;

    private Preferences preferences;
    private UserModel userModel;
    private List<ChooseFoodListModel> chooseFoodListModelList;
    private ChooseFoodsAdapter chooseFoodsAdapter;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            createOrderModel = (CreateOrderModel) intent.getSerializableExtra("data");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_order);
        getDataFromIntent();
        initView();


    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        binding.setModel(createOrderModel);
        chooseFoodListModelList = new ArrayList<>();
        if (createOrderModel.getFoods() != null) {
            chooseFoodListModelList.addAll(createOrderModel.getFoods());
            Log.e("lldld",createOrderModel.getFoods().size()+"");
        }

        chooseFoodsAdapter = new ChooseFoodsAdapter(chooseFoodListModelList, this);
        binding.recViewfoods.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewfoods.setAdapter(chooseFoodsAdapter);
        createOrderModel.setPay_type("cash");
        binding.rbChoose1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    createOrderModel.setPay_type("cash");
                }
            }
        });
        binding.rbChoose2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    createOrderModel.setPay_type("card");
                }
            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrderModel.setUser_id(userModel.getUser().getId() + "");

                String copun = binding.edtcopun.getText().toString();
                if (!TextUtils.isEmpty(copun)) {
                    SendCoupon(copun);

                } else {
                    if (userModel != null) {
                        createOrder();
                    } else {
                        Common.CreateNoSignAlertDialog(CompleteOrderActivity.this);
                    }
                }
            }
        });
    }


    @Override
    public void back() {
        finish();
    }

    private void SendCoupon(String coupon) {
        final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .getCouponValue(userModel.getUser().getToken(), coupon)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            createOrderModel.setCoupon_id(response.body().getData().getId() + "");
                            createOrder();
                        } else {
                            try {
                                Log.e("error_code", response.code() + "_" + response.errorBody().string());
                            } catch (Exception e) {
                            }

                            if (response.code() == 406) {
                                CreateAlertDialog(getString(R.string.coupon_not_found));
                            } else {
                                Toast.makeText(CompleteOrderActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();


                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                        try {
                            dialog.dismiss();
                            Toast.makeText(CompleteOrderActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {
                        }


                    }
                });


    }

    public void CreateAlertDialog(String msg) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sign, null);
        Button doneBtn = view.findViewById(R.id.doneBtn);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.app_name));
        tv_msg.setText(msg);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
                dialog.dismiss();
            }
        });

//        dialog.getWindow().getAttributes().windowAnimations=R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setView(view);
        dialog.show();
    }

    public void createOrder() {
        try {

            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

     /*       createOrderModel.setOrder_date(addOrderModel.getDate());
            createOrderModel.setOrder_time(addOrderModel.getTime());*/
            Log.e("jfhfh", userModel.getUser().getId() + "" + userModel.getUser().getToken());
            Api.getService(Tags.base_url)
                    .createOrder(userModel.getUser().getToken(), createOrderModel)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();
                            if (response.isSuccessful()) {


                                if (response.body() != null) {

//                                    orderModel = response.body().getData();
//                                    Intent intent = getIntent();
//                                    intent.putExtra("data", orderModel);
//                                    setResult(RESULT_OK, intent);
//                                    finish();
                                    Intent intent = new Intent(CompleteOrderActivity.this, MyOrderActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                dialog.dismiss();
                                try {
                                    Log.e("error_code", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (response.code() == 500) {
                                    Toast.makeText(CompleteOrderActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(CompleteOrderActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(CompleteOrderActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CompleteOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

}
