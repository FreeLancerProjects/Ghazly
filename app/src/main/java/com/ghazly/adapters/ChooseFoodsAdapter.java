package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_complete_order.CompleteOrderActivity;
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.databinding.ChoosefoodsrowBinding;
import com.ghazly.databinding.TimeRowBinding;
import com.ghazly.models.ChooseFoodListModel;
import com.ghazly.models.TimesModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class ChooseFoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String lang;
    private List<ChooseFoodListModel> list;
    private Context context;
    private LayoutInflater inflater;
    private CompleteOrderActivity activity;
    private int i = -1;

    public ChooseFoodsAdapter(List<ChooseFoodListModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (CompleteOrderActivity) context;

        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ChoosefoodsrowBinding binding = DataBindingUtil.inflate(inflater, R.layout.choosefoodsrow, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ChoosefoodsrowBinding binding;

        public MyHolder(@NonNull ChoosefoodsrowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
