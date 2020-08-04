package com.ghazly.activities_fragments.activity_my_orders;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ghazly.R;
import com.ghazly.activities_fragments.activity_order_details.OrderDetailsActivity;
import com.ghazly.adapters.OrderAdapter;
import com.ghazly.databinding.ActivityMyOrdersBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.OrderDataModel;
import com.ghazly.models.SingleOrderModel;
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

public class MyOrderActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityMyOrdersBinding binding;
    private String lang;

    private OrderAdapter adapter;
    private List<SingleOrderModel> orderModelList;
    private boolean isLoading = false;
    private int current_page = 1;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders);
        initView();


    }

    private void initView() {
        orderModelList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderAdapter(this, orderModelList);
        binding.recView.setAdapter(adapter);

        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int total = binding.recView.getAdapter().getItemCount();

                    int lastVisibleItem = ((LinearLayoutManager) binding.recView.getLayoutManager()).findLastCompletelyVisibleItemPosition();


                    if (total >= 20 && (total - lastVisibleItem) == 2 && !isLoading) {
                        isLoading = true;
                        int page = current_page + 1;
                        orderModelList.add(null);
                        adapter.notifyDataSetChanged();
                        loadMore(page);
                    }
                }
            }
        });
        getOrders();
    }


    public void getOrders() {
        try {

            Api.getService(Tags.base_url)
                    .getOrders(userModel.getUser().getToken(), String.valueOf(userModel.getUser().getId()), "on",  1, 20)
                    .enqueue(new Callback<OrderDataModel>() {
                        @Override
                        public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                            binding.progBar.setVisibility(View.GONE);

                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                orderModelList.clear();
                                orderModelList.addAll(response.body().getData());
                                if (orderModelList.size() > 0) {
                                    adapter.notifyDataSetChanged();
                                    binding.tvNoData.setVisibility(View.GONE);
                                } else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(MyOrderActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(MyOrderActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDataModel> call, Throwable t) {
                            try {

                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(MyOrderActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }

    }

    private void loadMore(int page) {

        try {



            Api.getService(Tags.base_url)
                    .getOrders(userModel.getUser().getToken(), String.valueOf(userModel.getUser().getId()), "on",  page, 20)
                    .enqueue(new Callback<OrderDataModel>() {
                        @Override
                        public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                            isLoading = false;
                            orderModelList.remove(orderModelList.size()-1);
                            adapter.notifyItemRemoved(orderModelList.size()-1);

                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                orderModelList.addAll(response.body().getData());
                                adapter.notifyDataSetChanged();
                                if (response.body().getData().size()>0)
                                {
                                    current_page = response.body().getCurrent_page();

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(MyOrderActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(MyOrderActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDataModel> call, Throwable t) {
                            try {
                                if (orderModelList.get(orderModelList.size()-1)==null)
                                {
                                    isLoading = false;
                                    orderModelList.remove(orderModelList.size()-1);
                                    adapter.notifyItemRemoved(orderModelList.size()-1);

                                }
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(MyOrderActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

    @Override
    public void back() {
        finish();
    }

    public void showdetials(SingleOrderModel singleOrderModel) {
        Intent intent = new Intent(MyOrderActivity.this, OrderDetailsActivity.class);
        intent.putExtra("data",singleOrderModel);
        startActivity(intent);
    }
}
