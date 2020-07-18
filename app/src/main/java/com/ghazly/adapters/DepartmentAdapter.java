package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_home.HomeActivity;
import com.ghazly.activities_fragments.activity_login.LoginActivity;
import com.ghazly.databinding.CountriesRowBinding;
import com.ghazly.databinding.DepartmentRowBinding;
import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.CountryModel;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryDataModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private HomeActivity homeActivity;
    public DepartmentAdapter(List<CategoryDataModel.Data> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        DepartmentRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.department_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public DepartmentRowBinding binding;

        public MyHolder(@NonNull DepartmentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
