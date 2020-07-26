package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.databinding.BranchRowBinding;
import com.ghazly.databinding.ImagerestaurantRowBinding;
import com.ghazly.models.SingleRestaurantModel;

import java.util.List;

public class RestaurnantImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleRestaurantModel.RestaurantImages> list;
    private Context context;
    private LayoutInflater inflater;
    private RestuarnantActivity restuarnantActivity;
    public RestaurnantImagesAdapter(List<SingleRestaurantModel.RestaurantImages> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        restuarnantActivity=(RestuarnantActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ImagerestaurantRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.imagerestaurant_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(view -> {
//homeActivity.setitemData(list.get(holder.getLayoutPosition()).getId()+"");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImagerestaurantRowBinding binding;

        public MyHolder(@NonNull ImagerestaurantRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
