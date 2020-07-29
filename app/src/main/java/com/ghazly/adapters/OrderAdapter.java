package com.ghazly.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.ghazly.R;
import com.ghazly.databinding.LoadmoreRowBinding;
import com.ghazly.databinding.OrderRowBinding;
import com.ghazly.models.OrderModel;
import com.ghazly.models.SingleOrderModel;

import java.util.List;

import io.paperdb.Paper;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DATA = 1;
    private final int LOAD = 2;

    private Context context;
    private List<SingleOrderModel> list;
    private String lang;
    public OrderAdapter(Context context, List<SingleOrderModel> list) {
        this.context = context;
        this.list = list;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==DATA) {
            OrderRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_row, parent, false);
            return new Holder1(binding);


        }else
            {
                LoadmoreRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.loadmore_row,parent,false);
                return new LoadHolder(binding);
            }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SingleOrderModel model = list.get(position);

        if (holder instanceof Holder1)
        {
            Holder1 holder1 = (Holder1) holder;

            holder1.binding.setModel(model);
if(model.getDrinks()!=null){
   Log.e("lfllvl",model.getDrinks().get(0).getAmount());
}

        }else if (holder instanceof LoadHolder) {
            LoadHolder loadHolder = (LoadHolder) holder;
            loadHolder.binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            loadHolder.binding.progBar.setIndeterminate(true);
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder1 extends RecyclerView.ViewHolder {
        private OrderRowBinding binding;

        public Holder1(@NonNull OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public class LoadHolder extends RecyclerView.ViewHolder {
        private LoadmoreRowBinding binding;

        public LoadHolder(@NonNull LoadmoreRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (list.get(position)==null)
        {
            return LOAD;
        }else
        {
            return DATA;
        }
    }
}
