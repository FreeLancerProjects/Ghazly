package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.activities_fragments.drinks_activity.DrinksActivity;
import com.ghazly.databinding.CityRowBinding;
import com.ghazly.databinding.DrinksRowBinding;
import com.ghazly.models.CityModel;
import com.ghazly.models.SingleRestaurantModel;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CityModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private HomeActivity homeActivity;
    private int i = -1;

    public CitiesAdapter(List<CityModel.Data> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        homeActivity = (HomeActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CityRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.city_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.binding.rbChoose1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    i = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (i == position) {
            myHolder.binding.rbChoose1.setChecked(true);
            homeActivity.getneighboor(list.get(i).getCity_id());
        } else {
            myHolder.binding.rbChoose1.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public CityRowBinding binding;

        public MyHolder(@NonNull CityRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
