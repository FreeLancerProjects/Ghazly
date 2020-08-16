package com.ghazly.activities_fragments.activity_restuarant_filter_result;

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
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.adapters.RestaurantAdapter;
import com.ghazly.databinding.ActivityFilterResultBinding;
import com.ghazly.databinding.ActivityMyOrdersBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.RestuarantModel;
import com.ghazly.models.SingleRestaurantModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.ghazly.remote.Api;
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

public class RestuarantFilterActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityFilterResultBinding binding;
    private String lang;

    private RestaurantAdapter restaurantAdapter;
    private List<SingleRestaurantModel> reDataList;
    private boolean isLoading = false;
    private int current_page = 1;
    private Preferences preferences;
    private UserModel userModel;
    private String cityid = "all", niegboorid = "all";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_result);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            cityid = intent.getStringExtra("cityid");
            niegboorid = intent.getStringExtra("niegboorid");

        }
    }
    private void initView() {
        reDataList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
binding.setLang(lang);
        binding.setBackListener(this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));

        restaurantAdapter = new RestaurantAdapter(reDataList, this);
        binding.recView.setAdapter(restaurantAdapter);

        getRestaurant();
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
                        reDataList.add(null);
                        restaurantAdapter.notifyItemInserted(reDataList.size() - 1);

                        loadMore(page);
                    }
                }
            }
        });
    }


    private void getRestaurant() {
        reDataList.clear();
        restaurantAdapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
        try {
            int uid;

            if (userModel != null) {
                uid = userModel.getUser().getId();
            } else {
                uid = 0;
            }
            Api.getService(Tags.base_url)
                    .getRestaurantfilter("on", cityid,niegboorid, uid + "", "20", current_page)
                    .enqueue(new Callback<RestuarantModel>() {
                        @Override
                        public void onResponse(Call<RestuarantModel> call, Response<RestuarantModel> response) {
                            binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                reDataList.clear();
                                reDataList.addAll(response.body().getData());
                                if (reDataList.size() > 0) {

                                    restaurantAdapter.notifyDataSetChanged();

                                    binding.tvNoData.setVisibility(View.GONE);
                                } else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(RestuarantFilterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(RestuarantFilterActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("errorssss", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RestuarantModel> call, Throwable t) {
                            try {
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("errorsssss", t.getClass().toString());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(RestuarantFilterActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RestuarantFilterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
            int uid;

            if (userModel != null) {
                uid = userModel.getUser().getId();
            } else {
                uid = 0;
            }
            Api.getService(Tags.base_url)
                    .getRestaurantfilter("on", cityid,niegboorid, uid + "", "20", page)
                    .enqueue(new Callback<RestuarantModel>() {
                        @Override
                        public void onResponse(Call<RestuarantModel> call, Response<RestuarantModel> response) {
                            isLoading = false;
                            reDataList.remove(reDataList.size() - 1);
                            restaurantAdapter.notifyItemRemoved(reDataList.size() - 1);


                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {

                                int oldPos = reDataList.size() - 1;

                                reDataList.addAll(response.body().getData());

                                if (response.body().getData().size() > 0) {
                                    current_page = response.body().getCurrent_page();
                                    restaurantAdapter.notifyItemRangeChanged(oldPos, reDataList.size() - 1);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(RestuarantFilterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(RestuarantFilterActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RestuarantModel> call, Throwable t) {
                            try {

                                if (reDataList.get(reDataList.size() - 1) == null) {
                                    isLoading = false;
                                    reDataList.remove(reDataList.size() - 1);
                                    restaurantAdapter.notifyItemRemoved(reDataList.size() - 1);

                                }

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(RestuarantFilterActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RestuarantFilterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }

    }
    public void setrestauant(String s) {
        Intent intent = new Intent(RestuarantFilterActivity.this, RestuarnantActivity.class);
        intent.putExtra("restaurand_id", s);
        startActivity(intent);
    }
    public int like_dislike(SingleRestaurantModel productModel, int pos) {
        if (userModel != null) {
            try {
                Api.getService(Tags.base_url)
                        .addFavoriteProduct(userModel.getUser().getToken(), productModel.getId() + "", userModel.getUser().getId() + "")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {

                                    getRestaurant();
                                } else {


                                    if (response.code() == 500) {
                                        Toast.makeText(RestuarantFilterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(RestuarantFilterActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

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

                                    if (t.getMessage() != null) {
                                        Log.e("error", t.getMessage());
                                        if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                            Toast.makeText(RestuarantFilterActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RestuarantFilterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (Exception e) {
                                }
                            }
                        });
            } catch (Exception e) {

            }
            return 1;

        } else {

            // Common.CreateDialogAlert(activity, getString(R.string.please_sign_in_or_sign_up));
            return 0;

        }
    }

    @Override
    public void back() {
        finish();
    }
    public void share(SingleRestaurantModel singleRestaurantModel) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, singleRestaurantModel.getShare_link());
        startActivity(intent);
    }
}
