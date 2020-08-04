package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.ghazly.R;
import com.ghazly.databinding.SnaksRowBinding;
import com.ghazly.databinding.SnaksdetialsRowBinding;
import com.ghazly.models.SingleOrderModel;

import java.util.List;

public class SnakAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SingleOrderModel.FoodsModel.SnaksModel> list;

    public SnakAdapter(Context context, List<SingleOrderModel.FoodsModel.SnaksModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SnaksdetialsRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.snaksdetials_row, parent, false);
        return new Holder1(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SingleOrderModel.FoodsModel.SnaksModel snaksModel = list.get(position);


        Holder1 holder1 = (Holder1) holder;
        holder1.binding.setModel(snaksModel);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder1 extends RecyclerView.ViewHolder {
        private SnaksdetialsRowBinding binding;

        public Holder1(@NonNull SnaksdetialsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
