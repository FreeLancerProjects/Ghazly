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
import com.ghazly.databinding.CityRowBinding;
import com.ghazly.databinding.NeigboorRowBinding;
import com.ghazly.models.CityModel;
import com.ghazly.models.NeigboorModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class NeigboorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String lang;
    private List<NeigboorModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private HomeActivity homeActivity;
    private int i = -1;

    public NeigboorAdapter(List<NeigboorModel.Data> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        homeActivity = (HomeActivity) context;
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        NeigboorRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.neigboor_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);
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
            homeActivity.setniegboor(list.get(i).getNeighbor_id());
        } else {
            myHolder.binding.rbChoose1.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public NeigboorRowBinding binding;

        public MyHolder(@NonNull NeigboorRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
