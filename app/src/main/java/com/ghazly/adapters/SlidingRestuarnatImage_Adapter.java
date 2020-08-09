package com.ghazly.adapters;


import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.databinding.SliderBinding;
import com.ghazly.models.SingleRestaurantModel;
import com.ghazly.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SlidingRestuarnatImage_Adapter extends PagerAdapter {


    List<SingleRestaurantModel.RestaurantImages> IMAGES;
    private LayoutInflater inflater;
    Context context;

    public SlidingRestuarnatImage_Adapter(Context context, List<SingleRestaurantModel.RestaurantImages> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        SliderBinding rowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.slider, view, false);


        Picasso.get().load(Uri.parse(Tags.IMAGE_URL + IMAGES.get(position).getFull_file())).fit().into(rowBinding.image);

        view.addView(rowBinding.getRoot());
        return rowBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
