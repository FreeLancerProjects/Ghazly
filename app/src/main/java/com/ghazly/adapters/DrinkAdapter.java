package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.databinding.ActivityDrinksDetailsBinding;
import com.ghazly.databinding.DrinkdetialsRowBinding;
import com.ghazly.models.DrinkModel;
import com.ghazly.models.SingleOrderModel;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SingleOrderModel.DrinkModel> list;

    public DrinkAdapter(Context context, List<SingleOrderModel.DrinkModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DrinkdetialsRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.drinkdetials_row, parent, false);
        return new Holder1(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SingleOrderModel.DrinkModel drinkModel = list.get(position);


        Holder1 holder1 = (Holder1) holder;
        holder1.binding.setModel(drinkModel);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder1 extends RecyclerView.ViewHolder {
        private DrinkdetialsRowBinding binding;

        public Holder1(@NonNull DrinkdetialsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
