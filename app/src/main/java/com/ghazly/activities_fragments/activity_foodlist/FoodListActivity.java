package com.ghazly.activities_fragments.activity_foodlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_food_detials.FoodDetialsActivity;
import com.ghazly.adapters.DepartmentAdapter;
import com.ghazly.adapters.FoodListAdapter;
import com.ghazly.adapters.RestaurantDepartmentAdapter;
import com.ghazly.databinding.ActivityFoodListBinding;
import com.ghazly.databinding.ActivityMyOrdersBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.ChooseFoodListModel;
import com.ghazly.models.CreateOrderModel;
import com.ghazly.models.FoodListModel;
import com.ghazly.models.RestuarantDepartmentModel;
import com.ghazly.models.FoodListModel;
import com.ghazly.models.SingleFoodModel;
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

    private Preferences preferences;
    private UserModel userModel;
    private RestaurantDepartmentAdapter restaurantDepartmentAdapter;
    private List<RestuarantDepartmentModel.Data> resDataList;
    private String restaurand_id;
    private FoodListAdapter foodListAdapter;
    private List<SingleFoodModel> fooDataList;
    private LinearLayoutManager manager2;
    private String category_id = "all";
    private int current_page = 1;
    private boolean isLoading = false;
    private List<ChooseFoodListModel> chooseFoodListModels;
    private CreateOrderModel createOrderModel;
    private double price;
    private String query = "all";
    private String title;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
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
        fooDataList = new ArrayList<>();
        chooseFoodListModels = new ArrayList<>();
        createOrderModel = new CreateOrderModel();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        if(lang.equals("en")){
            title="all";

        }
        else {
            title="الكل";
        }
        binding.setBackListener(this);
        manager2 = new LinearLayoutManager(this);
        manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        restaurantDepartmentAdapter = new RestaurantDepartmentAdapter(resDataList, this);
        binding.recViewdepart.setLayoutManager(manager);
        binding.recViewdepart.setAdapter(restaurantDepartmentAdapter);
        foodListAdapter = new FoodListAdapter(fooDataList, this);
        binding.recView.setLayoutManager(manager2);
        binding.recView.setAdapter(foodListAdapter);
        getFoodlist();
        binding.imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.llsearch.getVisibility() == View.VISIBLE) {
                    binding.llsearch.setVisibility(View.GONE);
                } else {
                    binding.llsearch.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int total_item = binding.recView.getAdapter().getItemCount();
                    int last_visible_item = ((LinearLayoutManager) binding.recView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                    if (total_item >= 20 && (total_item - last_visible_item) == 5 && !isLoading) {

                        isLoading = true;
                        int page = current_page + 1;
                        fooDataList.add(null);
                        foodListAdapter.notifyItemInserted(fooDataList.size() - 1);

                        loadMore(page);
                    }
                }
            }
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    query = editable.toString();

                    getFoodlist();
                    binding.progBar.setVisibility(View.GONE);
                    binding.recView.setVisibility(View.VISIBLE);
                } else {
                    query = "";
                    //productModelList.clear();
                    //searchAdapter.notifyDataSetChanged();


                }
            }
        });

    }

    private void getMainCategory() {

        Api.getService(Tags.base_url)
                .getrestaurantdepartments("off", restaurand_id)
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

    private void getFoodlist() {
        fooDataList.clear();
        foodListAdapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
        if(title!= null){
            binding.tvTitle.setText(title);
            binding.tvTitle.setVisibility(View.VISIBLE);
        }
        try {

            Api.getService(Tags.base_url)
                    .getFoodList("on", category_id, restaurand_id + "", "20", current_page, query)
                    .enqueue(new Callback<FoodListModel>() {
                        @Override
                        public void onResponse(Call<FoodListModel> call, Response<FoodListModel> response) {
                            binding.progBar.setVisibility(View.GONE);
//                            try {
//                                Log.e("rrrkkr", restaurand_id + category_id + response.body().getData().get(0).getTitle());
//
//
//                            } catch (Exception e) {
//
//                            }
                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                fooDataList.clear();
                                fooDataList.addAll(response.body().getData());
                                if (fooDataList.size() > 0) {
                                    binding.tvTitle.setText(title);
                                    binding.tvTitle.setVisibility(View.VISIBLE);
                                    foodListAdapter.notifyDataSetChanged();

                                    binding.tvNoData.setVisibility(View.GONE);
                                } else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(FoodListActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(FoodListActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<FoodListModel> call, Throwable t) {
                            try {
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(FoodListActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FoodListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .getFoodList("on", category_id, restaurand_id + "", "20", page, query)
                    .enqueue(new Callback<FoodListModel>() {
                        @Override
                        public void onResponse(Call<FoodListModel> call, Response<FoodListModel> response) {
                            isLoading = false;
                            fooDataList.remove(fooDataList.size() - 1);
                            foodListAdapter.notifyItemRemoved(fooDataList.size() - 1);


                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {

                                int oldPos = fooDataList.size() - 1;

                                fooDataList.addAll(response.body().getData());

                                if (response.body().getData().size() > 0) {
                                    current_page = response.body().getCurrent_page();
                                    foodListAdapter.notifyItemRangeChanged(oldPos, fooDataList.size() - 1);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(FoodListActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(FoodListActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<FoodListModel> call, Throwable t) {
                            try {

                                if (fooDataList.get(fooDataList.size() - 1) == null) {
                                    isLoading = false;
                                    fooDataList.remove(fooDataList.size() - 1);
                                    foodListAdapter.notifyItemRemoved(fooDataList.size() - 1);

                                }

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(FoodListActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FoodListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        Intent intent = getIntent();
        intent.putExtra("data", createOrderModel);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        back();
    }

    public void setitemData(String s, String title) {
        category_id = s;
        this.title = title;
        getFoodlist();
    }

    public void showdetials(SingleFoodModel data) {
        Intent intent = new Intent(FoodListActivity.this, FoodDetialsActivity.class);
        intent.putExtra("data", data);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            ChooseFoodListModel chooseFoodListModel = null;
            if (data.getSerializableExtra("data") != null) {
                chooseFoodListModel = (ChooseFoodListModel) data.getSerializableExtra("data");
            }
            Log.e("ldlldl", chooseFoodListModel.getPrice());
            if (chooseFoodListModel != null) {
                chooseFoodListModels.add(chooseFoodListModel);
                price = Double.parseDouble(chooseFoodListModel.getPrice());

                createOrderModel.setFoods(chooseFoodListModels);
                if (createOrderModel.getTotal_price() != null) {
                    price += Double.parseDouble(createOrderModel.getTotal_price());
                }


                createOrderModel.setTotal_price(price + "");
            }

        }
    }
}
