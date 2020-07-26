package com.ghazly.activities_fragments.activity_restuarnt.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.adapters.DepartmentAdapter;
import com.ghazly.adapters.RestaurantAdapter;
import com.ghazly.adapters.RestaurnantBranchesAdapter;
import com.ghazly.adapters.TypeAdapter;
import com.ghazly.databinding.FragmentBookBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.SingleRestaurantModel;
import com.ghazly.models.TypeModel;
import com.ghazly.models.UserModel;
import com.ghazly.preferences.Preferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Book extends Fragment implements Listeners.RestaurantActions {


    private RestuarnantActivity activity;
    private FragmentBookBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private RestaurnantBranchesAdapter restaurnantBranchesAdapter;
    private List<SingleRestaurantModel.Branchs> branchsList;
    private TypeAdapter typeAdapter;
    private List<TypeModel> typeModelList;
    int numchild;
    int numpeople;

    public static Fragment_Book newInstance() {
        return new Fragment_Book();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false);
        initView();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        activity = (RestuarnantActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setRestaulistner(this);
        branchsList = new ArrayList<>();
        typeModelList = new ArrayList<>();
        typeModelList.add(new TypeModel("0", "عائلات", "Families"));
        typeModelList.add(new TypeModel("1", "افراد", "ٍSingles"));

        restaurnantBranchesAdapter = new RestaurnantBranchesAdapter(branchsList, activity);
        typeAdapter = new TypeAdapter(typeModelList, activity);

        binding.recViewbranches.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewbranches.setAdapter(restaurnantBranchesAdapter);
        binding.recViewtype.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, true));
        binding.recViewtype.setAdapter(typeAdapter);
        binding.btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showDepartmentlist();
            }
        });


    }


    public void setdata(SingleRestaurantModel body) {
        if (body.getBranchs() != null) {
            branchsList.addAll(body.getBranchs());
            restaurnantBranchesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void order() {

    }

    @Override
    public void increasechild() {
numchild+=1;
binding.tvCounter.setText(numchild+"");
    }

    @Override
    public void decreasechild() {
if(numchild>1){
    numchild-=1;
    binding.tvCounter.setText(numchild+"");

}
    }

    @Override
    public void increasepeople() {
        numpeople+=1;
        binding.tvCounter2.setText(numpeople+"");
    }

    @Override
    public void decreasepeople() {
        if(numpeople>1){
            numpeople-=1;
            binding.tvCounter2.setText(numpeople+"");

        }
    }
}
