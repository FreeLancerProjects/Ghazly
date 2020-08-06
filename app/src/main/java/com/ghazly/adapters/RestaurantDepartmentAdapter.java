package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_foodlist.FoodListActivity;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.databinding.DepartmentRowBinding;
import com.ghazly.databinding.RestaurantDepartmentRowBinding;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.RestuarantDepartmentModel;

import java.util.List;

public class RestaurantDepartmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RestuarantDepartmentModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private FoodListActivity homeActivity;
    private int i = -1;

    public RestaurantDepartmentAdapter(List<RestuarantDepartmentModel.Data> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        homeActivity = (FoodListActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        RestaurantDepartmentRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.restaurant_department_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(view -> {

            i = myHolder.getLayoutPosition();
            notifyDataSetChanged();
        });
        if (i == position) {
            homeActivity.setitemData(list.get(holder.getLayoutPosition()).getDepartment().getId() + "",list.get(holder.getLayoutPosition()).getDepartment().getTitle());
            myHolder.binding.cons.setBackgroundColor(context.getResources().getColor(R.color.colorAccents));
            myHolder.binding.tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        } else {
            myHolder.binding.cons.setBackgroundColor(context.getResources().getColor(R.color.white));
            myHolder.binding.tvName.setTextColor(context.getResources().getColor(R.color.gray9));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public RestaurantDepartmentRowBinding binding;

        public MyHolder(@NonNull RestaurantDepartmentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
