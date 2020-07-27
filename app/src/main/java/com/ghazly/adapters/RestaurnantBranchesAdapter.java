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
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.databinding.BranchRowBinding;
import com.ghazly.databinding.DepartmentRowBinding;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.SingleRestaurantModel;

import java.util.List;

public class RestaurnantBranchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleRestaurantModel.Branchs> list;
    private Context context;
    private LayoutInflater inflater;
    private RestuarnantActivity restuarnantActivity;
    private int i=-1;
    public RestaurnantBranchesAdapter(List<SingleRestaurantModel.Branchs> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        restuarnantActivity=(RestuarnantActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        BranchRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.branch_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

     myHolder.binding.rbChoose1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if(b){
                 i=position;
                 notifyDataSetChanged();
             }
         }
     });
     if(i==position){
         myHolder.binding.rbChoose1.setChecked(true);
         restuarnantActivity.setbranchod(list.get(i).getId()+"");
     }
     else {
         myHolder.binding.rbChoose1.setChecked(false);
     }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public BranchRowBinding binding;

        public MyHolder(@NonNull BranchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
