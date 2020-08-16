package com.ghazly.activities_fragments.activity_my_favorite;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ghazly.R;
import com.ghazly.activities_fragments.activity_restuarant_filter_result.RestuarantFilterActivity;
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.adapters.FavouriteRestaurantAdapter;
import com.ghazly.adapters.RestaurantAdapter;
import com.ghazly.databinding.ActivityMyFavoriteBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.FavouriteRestuarantModel;
import com.ghazly.models.RestuarantModel;
import com.ghazly.models.SingleRestaurantModel;
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

public class MyFavoriteActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityMyFavoriteBinding binding;
    private String lang;
    private boolean isLoading = false;
    private int current_page = 1;

    private LinearLayoutManager manager;
    private UserModel userModel;
    private Preferences preferences;
    // private int selected_pos = -1;
    //p//rivate boolean isFavoriteChange = false;
    // private boolean isItemAdded = false;
    private List<FavouriteRestuarantModel.Data> favouriteDataList;
    private FavouriteRestaurantAdapter favouriteProduct_adapter;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_favorite);
        initView();
    }


    private void initView() {
        favouriteDataList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);


        manager = new GridLayoutManager(this, 1);
        binding.recView.setLayoutManager(manager);
        favouriteProduct_adapter = new FavouriteRestaurantAdapter(favouriteDataList, this);
        binding.recView.setAdapter(favouriteProduct_adapter);
//        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    int total = binding.recView.getAdapter().getItemCount();
//
//                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//
//
//                    if (total > 6 && (total - lastVisibleItem) == 2 && !isLoading) {
//                        isLoading = true;
//                        int page = current_page + 1;
//                        productModelList.add(null);
//                        adapter.notifyDataSetChanged();
//                        loadMore(page);
//                    }
//                }
//            }
//        });*/
        getData();
    }

    public void setrestauant(String s) {
        Intent intent = new Intent(MyFavoriteActivity.this, RestuarnantActivity.class);
        intent.putExtra("restaurand_id", s);
        startActivity(intent);
    }

    public int like_dislike(FavouriteRestuarantModel.Data productModel, int pos) {
        if (userModel != null) {
            try {
                Api.getService(Tags.base_url)
                        .addFavoriteProduct(userModel.getUser().getToken(), productModel.getRestaurant().getId() + "", userModel.getUser().getId() + "")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {

                                    getData();
                                } else {


                                    if (response.code() == 500) {
                                        Toast.makeText(MyFavoriteActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(MyFavoriteActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

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
                                            Toast.makeText(MyFavoriteActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MyFavoriteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

            Common.CreateNoSignAlertDialog(MyFavoriteActivity.this);
            return 0;

        }
    }

    public void getData() {
        favouriteDataList.clear();
        favouriteProduct_adapter.notifyDataSetChanged();
        try {

            String token;
            if (userModel == null) {
                token = "";
            } else {
                token = userModel.getUser().getToken();
            }
            Api.getService(Tags.base_url)
                    .getMyFavoriteProducts(userModel.getUser().getToken(), "off")
                    .enqueue(new Callback<FavouriteRestuarantModel>() {
                        @Override
                        public void onResponse(Call<FavouriteRestuarantModel> call, Response<FavouriteRestuarantModel> response) {
                            binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                favouriteDataList.clear();
                                favouriteDataList.addAll(response.body().getData());
                                if (favouriteDataList.size() > 0) {
                                    favouriteProduct_adapter.notifyDataSetChanged();
                                    binding.tvNoData.setVisibility(View.GONE);
                                } else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(MyFavoriteActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(MyFavoriteActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("errorsss", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<FavouriteRestuarantModel> call, Throwable t) {
                            try {
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(MyFavoriteActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyFavoriteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        /*try {
            String token;
            if (userModel == null) {
                token = "";
            } else {
                token = userModel.getUser().getToken();
            }

            Api.getService(Tags.base_url)
                    .getMyFavoriteProducts(lang, token, "on", page)
                    .enqueue(new Callback<ProductDataModel>() {
                        @Override
                        public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                            isLoading = false;
                            productModelList.remove(productModelList.size() - 1);
                            adapter.notifyItemRemoved(productModelList.size() - 1);

                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                productModelList.addAll(response.body().getData());
                                adapter.notifyDataSetChanged();
                                if (response.body().getData().size() > 0) {
                                    current_page = response.body().getMeta().getCurrent_page();

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(MyFavoriteActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(MyFavoriteActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductDataModel> call, Throwable t) {
                            try {
                                if (productModelList.get(productModelList.size() - 1) == null) {
                                    isLoading = false;
                                    productModelList.remove(productModelList.size() - 1);
                                    adapter.notifyItemRemoved(productModelList.size() - 1);

                                }
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(MyFavoriteActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyFavoriteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

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

    @Override
    public void onBackPressed() {
        back();
    }

}
