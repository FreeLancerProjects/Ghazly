package com.ghazly.activities_fragments.activity_home;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.activities_fragments.activity_about_app.AboutAppActivity;
import com.ghazly.activities_fragments.activity_complete_order.CompleteOrderActivity;
import com.ghazly.activities_fragments.activity_contactus.ContactusActivity;
import com.ghazly.activities_fragments.activity_login.LoginActivity;
import com.ghazly.activities_fragments.activity_my_favorite.MyFavoriteActivity;
import com.ghazly.activities_fragments.activity_my_orders.MyOrderActivity;
import com.ghazly.activities_fragments.activity_profile.ProfileActivity;
import com.ghazly.activities_fragments.activity_restuarant_filter_result.RestuarantFilterActivity;
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.activities_fragments.activity_search.SearchActivity;
import com.ghazly.adapters.CitiesAdapter;
import com.ghazly.adapters.CountriesAdapter;
import com.ghazly.adapters.DepartmentAdapter;
import com.ghazly.adapters.NeigboorAdapter;
import com.ghazly.adapters.RestaurantAdapter;
import com.ghazly.databinding.ActivityHomeBinding;
import com.ghazly.databinding.DialogCitiesBinding;
import com.ghazly.databinding.DialogCountriesBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.CityModel;
import com.ghazly.models.NeigboorModel;
import com.ghazly.models.RestuarantModel;
import com.ghazly.models.SingleRestaurantModel;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ghazly.R;
import com.ghazly.language.Language;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.ghazly.remote.Api;
import com.ghazly.share.Common;
import com.ghazly.tags.Tags;

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

public class HomeActivity extends AppCompatActivity implements Listeners.HomeActions {
    private ActivityHomeBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String current_lang;
    private ActionBarDrawerToggle toggle;
    private DepartmentAdapter departmentAdapter;
    private List<CategoryDataModel.Data> categoryDataModelDataList;
    private RestaurantAdapter restaurantAdapter;
    private List<SingleRestaurantModel> reDataList;
    private CitiesAdapter citiesAdapter;
    private List<CityModel.Data> citiesmodles;
    private NeigboorAdapter neigboorAdapter;
    private List<NeigboorModel.Data> neigboormodels;
    private LinearLayoutManager manager;
    private String category_id = "all", cityid = "all", niegboorid = "all";

    private int current_page = 1;
    private boolean isLoading = false;
    private String id;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.getData() != null) {
            List<String> pathSegments = intent.getData().getPathSegments();
            id = pathSegments.get(pathSegments.size() - 1);
            Intent intent1 = new Intent(HomeActivity.this, RestuarnantActivity.class);
            intent1.putExtra("restaurand_id", id);
            startActivity(intent1);
        }
    }

    private void initView() {
        categoryDataModelDataList = new ArrayList<>();
        reDataList = new ArrayList<>();
        citiesmodles = new ArrayList<>();
        neigboormodels = new ArrayList<>();
        preferences = Preferences.getInstance();
        manager = new LinearLayoutManager(this);
        userModel = preferences.getUserData(this);
        if (userModel != null) {
            binding.setUsermodel(userModel);
            //  Log.e("ldldldll",userModel.getData().getToken());
        }
        Paper.init(this);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        Log.e("llll", current_lang);
        binding.setLang(current_lang);
        binding.setHomelistner(this);
        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.open, R.string.close);
        toggle.syncState();
        if (current_lang.equals("ar")) {
            binding.btnAr.setBackgroundResource(R.drawable.small_rounded_stroke_primarydark);
            binding.btnEn.setBackgroundResource(R.drawable.small_rounded_btn_gray);
        } else {
            binding.btnAr.setBackgroundResource(R.drawable.small_rounded_btn_gray);
            binding.btnEn.setBackgroundResource(R.drawable.small_rounded_stroke_primarydark);
        }

        if (userModel != null) {
            // EventBus.getDefault().register(this);
            updateTokenFireBase();

        }
        departmentAdapter = new DepartmentAdapter(categoryDataModelDataList, this);
        restaurantAdapter = new RestaurantAdapter(reDataList, this);
        citiesAdapter = new CitiesAdapter(citiesmodles, this);
        neigboorAdapter = new NeigboorAdapter(neigboormodels, this);
        binding.recViewdepart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        binding.recViewdepart.setAdapter(departmentAdapter);
        binding.recView.setLayoutManager(manager);
        binding.recView.setAdapter(restaurantAdapter);
        getMainCategory();
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

    private void Logout() {
        if (userModel != null) {


            ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
            dialog.show();


            FirebaseInstanceId.getInstance()
                    .getInstanceId().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();

                    Api.getService(Tags.base_url)
                            .logout(userModel.getUser().getToken(), token, userModel.getUser().getId(), "android")
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    dialog.dismiss();
                                    if (response.isSuccessful()) {
                                        preferences.clear(HomeActivity.this);
                                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        if (manager != null) {
                                            manager.cancel(Tags.not_tag, Tags.not_id);
                                        }
                                        navigateToSignInActivity();


                                    } else {
                                        dialog.dismiss();
                                        try {
                                            Log.e("error", response.code() + "__" + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        if (response.code() == 500) {
                                            Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    try {
                                        dialog.dismiss();
                                        if (t.getMessage() != null) {
                                            Log.e("error", t.getMessage() + "__");

                                            if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                                Toast.makeText(HomeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (Exception e) {
                                        Log.e("Error", e.getMessage() + "__");
                                    }
                                }
                            });

                }
            });


        } else {
            Common.CreateNoSignAlertDialog(HomeActivity.this);

        }

    }

    private void getMainCategory() {

        Api.getService(Tags.base_url)
                .getMainCategory("off")
                .enqueue(new Callback<CategoryDataModel>() {
                    @Override
                    public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                        binding.progBardepart.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            categoryDataModelDataList.clear();
                            categoryDataModelDataList.addAll(response.body().getData());

                            if (categoryDataModelDataList.size() > 0) {
                                departmentAdapter.notifyDataSetChanged();
                                binding.tvNoDatadepart.setVisibility(View.GONE);
                                Log.e(",dkdfkfkkfk", categoryDataModelDataList.get(0).getTitle());
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
                                Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                        try {
                            binding.progBardepart.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(HomeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
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
                    .getRestaurant("on", category_id, uid + "", "20", current_page)
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
                                    Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

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
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .getRestaurant("on", category_id, uid + "", "20", current_page)
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
                                    Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

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
                                        Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }

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
                                        Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

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
                                            Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void updateTokenFireBase() {


        FirebaseInstanceId.getInstance()
                .getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult().getToken();

                try {

                    try {

                        Api.getService(Tags.base_url)
                                .updatePhoneToken(userModel.getUser().getToken(), token, userModel.getUser().getId(), "android", userModel.getUser().getUser_type())
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            Log.e("token", "updated successfully");
                                        } else {
                                            try {

                                                Log.e("error", response.code() + "_" + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        try {

                                            if (t.getMessage() != null) {
                                                Log.e("error", t.getMessage());
                                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                                    Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        } catch (Exception e) {
                                        }
                                    }
                                });
                    } catch (Exception e) {


                    }
                } catch (Exception e) {


                }

            }
        });
    }


    public void RefreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }, 1050);


    }


    @Override
    public void onBackPressed() {
        finish();
    }


    private void navigateToSignInActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void order() {
        if (userModel != null) {
            Intent intent = new Intent(this, MyOrderActivity.class);
            startActivity(intent);
        } else {
            Common.CreateNoSignAlertDialog(this);
        }
    }

    @Override
    public void terms() {
        Intent intent = new Intent(this, AboutAppActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @Override
    public void aboutApp() {
        Intent intent = new Intent(this, AboutAppActivity.class);
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    @Override
    public void profile() {
        if (userModel != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivityForResult(intent, 100);
        } else {
            Common.CreateNoSignAlertDialog(HomeActivity.this);

        }
    }

    @Override
    public void contactus() {
        Intent intent = new Intent(this, ContactusActivity.class);
        startActivity(intent);
    }

    @Override
    public void citydialog() {
        CreatefilterAlert();
    }

    @Override
    public void favourite() {
        if (userModel != null) {
            Intent intent = new Intent(this, MyFavoriteActivity.class);
            startActivity(intent);
        } else {
            Common.CreateNoSignAlertDialog(HomeActivity.this);

        }
    }

    @Override
    public void search() {
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void arLang() {

        if (!current_lang.equals("ar")) {
            RefreshActivity("ar");
            binding.btnAr.setBackgroundResource(R.drawable.small_rounded_btn_gray);
            binding.btnEn.setBackgroundResource(R.drawable.small_rounded_stroke_primarydark);
        }

    }

    @Override
    public void enLang() {

        if (!current_lang.equals("en")) {
            RefreshActivity("en");
            binding.btnAr.setBackgroundResource(R.drawable.small_rounded_stroke_primarydark);
            binding.btnEn.setBackgroundResource(R.drawable.small_rounded_btn_gray);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data.getStringExtra("logout") != null) {
            Logout();
        }
    }

    public void setitemData(String s) {
        category_id = s;
        getRestaurant();
    }

    public void setrestauant(String s) {
        Intent intent = new Intent(HomeActivity.this, RestuarnantActivity.class);
        intent.putExtra("restaurand_id", s);
        startActivity(intent);
    }

    public void CreatefilterAlert() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogCitiesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cities, null, false);
        binding.recViewcities.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewcities.setAdapter(citiesAdapter);

        binding.recViewneighboor.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewneighboor.setAdapter(neigboorAdapter);
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, RestuarantFilterActivity.class);
                intent.putExtra("cityid", cityid);
                intent.putExtra("niegboorid", niegboorid);
                startActivity(intent);

            }
        });
        //  dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.small_rounded_btn_white);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
        getcities();
    }

    private void getcities() {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .getMainCities()
                .enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        //  binding.progBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            citiesmodles.clear();
                            citiesmodles.addAll(response.body().getData());

                            if (citiesmodles.size() > 0) {
                                citiesAdapter.notifyDataSetChanged();
                                // binding..setVisibility(View.GONE);
                            } else {
                                //binding.tvNoDatadepart.setVisibility(View.VISIBLE);

                            }


                        } else {
                            //    binding.progBar.setVisibility(View.GONE);

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            //  binding.progBar.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(HomeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void getneighboor(String cityid) {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        this.cityid = cityid;

        Api.getService(Tags.base_url)
                .getNeigboor(cityid)
                .enqueue(new Callback<NeigboorModel>() {
                    @Override
                    public void onResponse(Call<NeigboorModel> call, Response<NeigboorModel> response) {
                        // binding.progBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            neigboormodels.clear();
                            neigboormodels.addAll(response.body().getData());

                            if (neigboormodels.size() > 0) {
                                neigboorAdapter.notifyDataSetChanged();
                                // binding..setVisibility(View.GONE);
                            } else {
                                //binding.tvNoDatadepart.setVisibility(View.VISIBLE);

                            }


                        } else {
                            binding.progBar.setVisibility(View.GONE);

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NeigboorModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            binding.progBar.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(HomeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void setniegboor(String neighbor_id) {
        this.niegboorid = neighbor_id;
    }

    public void share(SingleRestaurantModel singleRestaurantModel) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, singleRestaurantModel.getShare_link());
        startActivity(intent);
    }
}
