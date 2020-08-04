package com.ghazly.ui_general_method;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.ghazly.R;
import com.ghazly.tags.Tags;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class UI_General_Method {

    @BindingAdapter("error")
    public static void setErrorUi(View view, String error)
    {
        if (view instanceof EditText)
        {
            EditText editText = (EditText) view;
            editText.setError(error);

        }else if (view instanceof TextView)
        {
            TextView textView = (TextView) view;
            textView.setError(error);

        }
    }
    @BindingAdapter({"order_date"})
    public static void date_time(TextView textView,long date)
    {
        Paper.init(textView.getContext());
        String lang = Paper.book().read("lang","ar");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale(lang));
        String d = dateFormat.format(new Date(date*1000));



        textView.setText(d);
    }
    @BindingAdapter({"order_status"})
    public static void orderStatus(TextView textView, String status) {
       if (status.equals("new ")) {
            textView.setText(textView.getContext().getString(R.string.new_order));
        } else if (status.equals("current ")) {
            textView.setText(textView.getContext().getString(R.string.current));

        } else if (status.equals("confirmed ")) {
            textView.setText(textView.getContext().getString(R.string.restuarant_confirm));

        }  else if (status.equals("end")) {
            textView.setText(textView.getContext().getString(R.string.completed));

        }

    }

    @BindingAdapter("image")
    public static void image(View view,String endPoint)
    {
        if (view instanceof CircleImageView)
        {
            CircleImageView imageView = (CircleImageView) view;

            if (endPoint!=null)
            {
Log.e("lllll",Tags.IMAGE_URL+endPoint);
                Picasso.get().load(Uri.parse(Tags.IMAGE_URL+endPoint)).into(imageView);
            }
        }else if (view instanceof RoundedImageView)
        {
            RoundedImageView imageView = (RoundedImageView) view;

            if (endPoint!=null)
            {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL+endPoint)).fit().into(imageView);
            }
        }else if (view instanceof ImageView)
        {
            ImageView imageView = (ImageView) view;

            if (endPoint!=null)
            {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL+endPoint)).fit().into(imageView);
            }
        }

    }


    
    @BindingAdapter("day")
    public static void displayDay(TextView textView,long time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        String sTime = dateFormat.format(new Date(time*1000));
        textView.setText(sTime);
    }

    @BindingAdapter("date")
    public static void displayDate(TextView textView,long date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
        String sTime = dateFormat.format(new Date(date*1000));
        textView.setText(sTime);
    }

    @BindingAdapter("time")
    public static void displayTime(TextView textView,long time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        String sTime = dateFormat.format(new Date(time*1000));
        textView.setText(sTime);
    }




    @BindingAdapter({"time","date"})
    public static void displayDateTime(TextView textView,long times,long dates)
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        String time = dateFormat.format(new Date(times*1000));


        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
        String date = dateFormat2.format(new Date(dates*1000));

        textView.setText(String.format("%s %s %s",date,"-",time));
    }




    @BindingAdapter({"orderTime","orderDate"})
    public static void displayOrderDate(TextView textView,long time,long date)
    {

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
        String d = dateFormat2.format(new Date(date*1000));

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        String t = dateFormat.format(new Date(time*1000));
        textView.setText(String.format("%s %s %s",d,"-",t));
    }


    @BindingAdapter("month")
    public static void displayMonth(TextView textView,long time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        String sTime = dateFormat.format(new Date(time*1000));
        textView.setText(sTime);
    }

    @BindingAdapter({"time","duration"})
    public static void addDate(TextView textView,String time,String duration)
    {
        if (time!=null&&!time.isEmpty()&&duration!=null&&!duration.isEmpty())
        {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm",Locale.ENGLISH);

            String [] split = duration.split(":",0);
            int hours=0;
            int min = 0;
            if (Integer.parseInt(split[0])>0)
            {
                hours = Integer.parseInt(split[0]);
            }

            if (Integer.parseInt(split[1])>0)
            {
                min = Integer.parseInt(split[1]);
            }

            try {
                Date date = dateFormat1.parse(time);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR_OF_DAY,hours);
                calendar.add(Calendar.MINUTE,min);






                SimpleDateFormat dateFormat3 = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                String d = dateFormat3.format(new Date(calendar.getTimeInMillis()));

                textView.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }


    @BindingAdapter("time_AM_BM")
    public static void timeAM_BM(TextView textView,String time_AM_BM)
    {
        if (time_AM_BM!=null&&!time_AM_BM.isEmpty())
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);

            try {
                Date date = dateFormat.parse(time_AM_BM);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                String d = dateFormat2.format(new Date(calendar.getTimeInMillis()));
                textView.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @BindingAdapter("notification_date")
    public static void notificationDate(TextView textView,String date)
    {
        if (date!=null&&!date.isEmpty())
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy\nhh:mm aa",Locale.ENGLISH);

            String d = dateFormat.format(new Date(Long.parseLong(date)*1000));
            textView.setText(d);
        }

    }










}
