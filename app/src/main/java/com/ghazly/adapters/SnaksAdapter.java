package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_food_detials.FoodDetialsActivity;
import com.ghazly.databinding.ImagerestaurantRowBinding;
import com.ghazly.databinding.SnaksRowBinding;
import com.ghazly.models.FoodListModel;
import com.ghazly.models.SingleFoodModel;
import com.ghazly.models.SingleRestaurantModel;

import java.util.List;

public class SnaksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleFoodModel.Snaks> list;
    private Context context;
    private LayoutInflater inflater;
    private FoodDetialsActivity foodDetialsActivity;
    public SnaksAdapter(List<SingleFoodModel.Snaks> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        foodDetialsActivity =(FoodDetialsActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        SnaksRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.snaks_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

      myHolder.binding.chChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
              if(ischecked){
                  foodDetialsActivity.setsnaksamount(true,list.get(myHolder.getLayoutPosition()).getId(),Double.parseDouble(list.get(myHolder.getLayoutPosition()).getPrice()));
              }
              else {
                  foodDetialsActivity.setsnaksamount(false,list.get(myHolder.getLayoutPosition()).getId(),Double.parseDouble(list.get(myHolder.getLayoutPosition()).getPrice()));

              }
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public SnaksRowBinding binding;

        public MyHolder(@NonNull SnaksRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
