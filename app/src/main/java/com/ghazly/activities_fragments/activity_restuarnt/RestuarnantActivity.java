package com.ghazly.activities_fragments.activity_restuarnt;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_complete_order.CompleteOrderActivity;
import com.ghazly.activities_fragments.activity_foodlist.FoodListActivity;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.activities_fragments.activity_image.Image_Activity;
import com.ghazly.activities_fragments.activity_restuarnt.fragments.Fragment_Book;
import com.ghazly.activities_fragments.activity_restuarnt.fragments.Fragment_Convenience;
import com.ghazly.activities_fragments.drinks_activity.DrinksActivity;
import com.ghazly.adapters.ViewPagerAdapter;
import com.ghazly.databinding.ActivityRestuarantBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.ChooseFoodListModel;
import com.ghazly.models.CreateOrderModel;
import com.ghazly.models.SingleRestaurantModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.ghazly.remote.Api;
import com.ghazly.share.Common;
import com.ghazly.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestuarnantActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityRestuarantBinding binding;
    private Preferences preferences;
    private FragmentManager fragmentManager;
    private UserModel userModel;
    private String lang;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> title;
    private String restaurand_id;
    private CreateOrderModel createOrderModel;
    private String family, branchid, session;
    private String date;
    private SingleRestaurantModel singlrestaurantmodel;
    private String time;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language.updateResources(base, Language.getLanguage(base)));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restuarant);
        getDataFromIntent();
        initView();
        getsinglemarkets();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            restaurand_id = intent.getStringExtra("restaurand_id");

        }
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);

        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);


        fragmentList = new ArrayList<>();
        title = new ArrayList<>();
        binding.setBackListener(this);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(2);

        fragmentList.add(Fragment_Book.newInstance());
        fragmentList.add(Fragment_Convenience.newInstance());
        title.add(getString(R.string.book));
        title.add(getString(R.string.convenience));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments(fragmentList);
        adapter.addTitles(title);
        binding.pager.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        back();
    }


    public void showDepartmentlist() {
        Intent intent = new Intent(RestuarnantActivity.this, FoodListActivity.class);
        intent.putExtra("restaurand_id", restaurand_id);
        startActivityForResult(intent, 1);
    }

    public void getsinglemarkets() {
        //   Common.CloseKeyBoard(homeActivity, edt_name);

        ProgressDialog dialog = Common.createProgressDialog(RestuarnantActivity.this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        // rec_sent.setVisibility(View.GONE);
        try {
            String id = "all";
            if (userModel != null) {
                id = userModel.getUser().getId() + "";
            }

            Api.getService(Tags.base_url)
                    .getSingleAds(restaurand_id, id)
                    .enqueue(new Callback<SingleRestaurantModel>() {
                        @Override
                        public void onResponse(Call<SingleRestaurantModel> call, Response<SingleRestaurantModel> response) {
                            dialog.dismiss();

                            //  binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null) {
                                //binding.coord1.scrollTo(0,0);

                                update(response.body());
                            } else {


                                Toast.makeText(RestuarnantActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SingleRestaurantModel> call, Throwable t) {
                            try {

                                dialog.dismiss();

                                Toast.makeText(RestuarnantActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                Log.e("error", t.getMessage());
                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("fllvlvl", e.toString());
            dialog.dismiss();
        }
    }

    private void update(SingleRestaurantModel body) {
        singlrestaurantmodel = body;
        binding.setModel(body);

        if (fragmentList != null && fragmentList.size() > 0) {
            if (body != null) {
                Fragment_Book fragment_book = (Fragment_Book) fragmentList.get(0);
                fragment_book.setdata(body);
                Fragment_Convenience fragment_convenience = (Fragment_Convenience) fragmentList.get(1);
                fragment_convenience.setdata(body);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data.getSerializableExtra("data") != null) {
                if (createOrderModel == null) {
                    createOrderModel = (CreateOrderModel) data.getSerializableExtra("data");


                } else {
                    CreateOrderModel createOrderModel1 = (CreateOrderModel) data.getSerializableExtra("data");
                    createOrderModel.setFoods(createOrderModel1.getFoods());
                    createOrderModel.setTotal_price((Double.parseDouble(createOrderModel1.getTotal_price()) + Double.parseDouble(createOrderModel.getTotal_price())) + "");
                }

            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (data.getSerializableExtra("data") != null) {
                if (createOrderModel == null) {
                    createOrderModel = (CreateOrderModel) data.getSerializableExtra("data");


                } else {
                    CreateOrderModel createOrderModel1 = (CreateOrderModel) data.getSerializableExtra("data");
                    createOrderModel.setDrinks(createOrderModel1.getDrinks());
                    createOrderModel.setTotal_price((Double.parseDouble(createOrderModel1.getTotal_price()) + Double.parseDouble(createOrderModel.getTotal_price())) + "");
                }
                Log.e("ldlld", createOrderModel.getTotal_price());

            }
        }
    }

    public void setbranchod(String s) {
        branchid = s;
    }

    public void setfamily(String s) {
        family = s;
    }

    public void setsession(String in) {
        session = in;
    }

    public void checkdata(int numchild, int numpeople) {

        if (family != null && session != null && date != null && time != null) {
            Log.e("fllflfl", date + "  " + time);
            if (createOrderModel == null) {
                createOrderModel = new CreateOrderModel();
                createOrderModel.setBranch_id(branchid);
                createOrderModel.setTotal_price("0");
                createOrderModel.setNumber_of_child(numchild + "");
                createOrderModel.setNumber_of_person(numpeople + "");
                createOrderModel.setSession_type(family);
                createOrderModel.setSession_place(session);
                createOrderModel.setRestaurant_id(restaurand_id);
                createOrderModel.setOrder_date(date);
                createOrderModel.setOrder_time(time + ":00");
                Intent intent = new Intent(RestuarnantActivity.this, CompleteOrderActivity.class);
                intent.putExtra("data", createOrderModel);
                startActivityForResult(intent, 3);
                //finish();
            } else {
                createOrderModel.setBranch_id(branchid);
                createOrderModel.setNumber_of_child(numchild + "");
                createOrderModel.setNumber_of_person(numpeople + "");
                createOrderModel.setSession_type(family);
                createOrderModel.setSession_place(session);
                createOrderModel.setRestaurant_id(restaurand_id);
                createOrderModel.setOrder_date(date);
                createOrderModel.setOrder_time(time + ":00");
                Intent intent = new Intent(RestuarnantActivity.this, CompleteOrderActivity.class);
                intent.putExtra("data", createOrderModel);
                startActivityForResult(intent, 3);
                //finish();
            }
        } else {

            if (family == null) {
                Toast.makeText(RestuarnantActivity.this, getResources().getString(R.string.choose_family), Toast.LENGTH_LONG).show();
            }
            if (session == null) {
                Toast.makeText(RestuarnantActivity.this, getResources().getString(R.string.Choose_session), Toast.LENGTH_LONG).show();
            }
            if (date == null) {
                Toast.makeText(RestuarnantActivity.this, getResources().getString(R.string.Choose_date), Toast.LENGTH_LONG).show();
            }
            if (time == null) {
                Toast.makeText(RestuarnantActivity.this, getResources().getString(R.string.Choose_time), Toast.LENGTH_LONG).show();
            }
//            if (branchid == null) {
//                Toast.makeText(RestuarnantActivity.this, getResources().getString(R.string.please_choose_the_required_branch), Toast.LENGTH_LONG).show();
//            }
        }
    }

    public void setdate(String s) {
        date = s;
    }

    public void showDrinks() {
        Intent intent = new Intent(RestuarnantActivity.this, DrinksActivity.class);
        intent.putExtra("data", singlrestaurantmodel);
        startActivityForResult(intent, 2);
    }

    public void settime(String title) {
        time = title;
    }

    @Override
    public void back() {
        finish();
    }

    public void setitemData() {
        Intent intent=new Intent(RestuarnantActivity.this, Image_Activity.class);
        intent.putExtra("single_rest",singlrestaurantmodel);
        intent.putExtra("type","1");
        startActivity(intent);
    }

    public void showrestuarntimeges() {
        Intent intent=new Intent(RestuarnantActivity.this, Image_Activity.class);
        intent.putExtra("single_rest",singlrestaurantmodel);
        intent.putExtra("type","2");
        startActivity(intent);
    }
}
