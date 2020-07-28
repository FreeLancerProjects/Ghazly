package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.drinks_activity.DrinksActivity;
import com.ghazly.databinding.DrinksRowBinding;
import com.ghazly.models.SingleRestaurantModel;

import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleRestaurantModel.Drinks> list;
    private Context context;
    private LayoutInflater inflater;
    private DrinksActivity drinksActivity;

    public DrinksAdapter(List<SingleRestaurantModel.Drinks> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        drinksActivity = (DrinksActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        DrinksRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.drinks_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.binding.chChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    drinksActivity.setsnaksamount(true, list.get(myHolder.getLayoutPosition()).getId());
                } else {
                    drinksActivity.setsnaksamount(false, list.get(myHolder.getLayoutPosition()).getId());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public DrinksRowBinding binding;

        public MyHolder(@NonNull DrinksRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
