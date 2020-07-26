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
import com.ghazly.adapters.MenuImagesAdapter;
import com.ghazly.adapters.RestaurnantBranchesAdapter;
import com.ghazly.adapters.RestaurnantImagesAdapter;
import com.ghazly.adapters.TypeAdapter;
import com.ghazly.databinding.FragmentConvenienceBinding;
import com.ghazly.models.SingleRestaurantModel;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Convenience extends Fragment implements OnMapReadyCallback {
    private RestuarnantActivity activity;
    private FragmentConvenienceBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private GoogleMap mMap;
    private SingleRestaurantModel singleRestaurantModel;
    private RestaurnantImagesAdapter restaurnantImagesAdapter;
    private List<SingleRestaurantModel.RestaurantImages> restaurantImages;
    private MenuImagesAdapter menuImagesAdapter;
    private List<SingleRestaurantModel.MenuImages> menuImagesList;
    private Marker marker;
    private float zoom = 15.0f;
    public static Fragment_Convenience newInstance() {
        return new Fragment_Convenience();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_convenience, container, false);
        initView();
        updateUI();

        return binding.getRoot();
    }

    private void initView() {
        activity = (RestuarnantActivity) getActivity();
menuImagesList=new ArrayList<>();
restaurantImages=new ArrayList<>();

        restaurnantImagesAdapter = new RestaurnantImagesAdapter(restaurantImages, activity);
        menuImagesAdapter = new MenuImagesAdapter(menuImagesList, activity);

        binding.recViewimages.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL,true));
        binding.recViewimages.setAdapter(menuImagesAdapter);
        binding.recViewres.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL,true));
        binding.recViewres.setAdapter(restaurnantImagesAdapter);
    }

    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);

            AddMarker();
        }
    }

    private void AddMarker() {




        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(singleRestaurantModel.getLatitude()), Double.parseDouble(singleRestaurantModel.getLongitude()))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(singleRestaurantModel.getLatitude()), Double.parseDouble(singleRestaurantModel.getLongitude())), zoom));
        } else {
            marker.setPosition(new LatLng(Double.parseDouble(singleRestaurantModel.getLatitude()), Double.parseDouble(singleRestaurantModel.getLongitude())));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(singleRestaurantModel.getLatitude()), Double.parseDouble(singleRestaurantModel.getLongitude())), zoom));


        }

    }


    public void setdata(SingleRestaurantModel body) {
        this.singleRestaurantModel = body;
        binding.setModel(singleRestaurantModel);
        if (mMap != null) {
            AddMarker();
        }
        if(singleRestaurantModel.getMenu_images()!=null){
            menuImagesList.addAll(singleRestaurantModel.getMenu_images());
            menuImagesAdapter.notifyDataSetChanged();
        }
        if(singleRestaurantModel.getRestaurant_images()!=null){
            restaurantImages.addAll(singleRestaurantModel.getRestaurant_images());
            restaurnantImagesAdapter.notifyDataSetChanged();
        }
    }
}

