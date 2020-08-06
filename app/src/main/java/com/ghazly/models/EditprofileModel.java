package com.ghazly.models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.ghazly.BR;
import com.ghazly.R;

import java.io.Serializable;

public class EditprofileModel extends BaseObservable implements Serializable {

    private String name;

    public ObservableField<String> error_name = new ObservableField<>();




    public EditprofileModel() {
        this.name = "";

    }

    public EditprofileModel(String name) {
        setName(name);

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);


    }


    public boolean isDataValid(Context context)
    {
        if (
                !TextUtils.isEmpty(name)

        )
        {
            error_name.set(null);


            return true;
        }else
        {
            if (name.isEmpty())
            {
                error_name.set(context.getString(R.string.field_required));
            }else
            {
                error_name.set(null);
            }



            return false;
        }
    }
}
