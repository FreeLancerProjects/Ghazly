package com.ghazly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_login.LoginActivity;
import com.ghazly.activities_fragments.activity_restuarnt.RestuarnantActivity;
import com.ghazly.databinding.CountriesRowBinding;
import com.ghazly.databinding.TypeRowBinding;
import com.ghazly.models.CountryModel;
import com.ghazly.models.TypeModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String lang;
    private List<TypeModel> list;
    private Context context;
    private LayoutInflater inflater;
    private RestuarnantActivity activity;
    private int i = -1;

    public TypeAdapter(List<TypeModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (RestuarnantActivity) context;

        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        TypeRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.type_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);

        myHolder.binding.rbChoose1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    i = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (i == position) {
            myHolder.binding.rbChoose1.setChecked(true);
            activity.setfamily(list.get(i).getEnname() + "");
        } else {
            myHolder.binding.rbChoose1.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TypeRowBinding binding;

        public MyHolder(@NonNull TypeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
