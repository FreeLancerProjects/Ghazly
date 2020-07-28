package com.ghazly.activities_fragments.activity_contactus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.ghazly.R;
import com.ghazly.activities_fragments.activity_about_app.AboutAppActivity;
import com.ghazly.databinding.ActivityAboutAppBinding;
import com.ghazly.databinding.ActivityContactusBinding;
import com.ghazly.interfaces.Listeners;
import com.ghazly.language.Language;
import com.ghazly.models.SettingModel;
import com.ghazly.remote.Api;
import com.ghazly.tags.Tags;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactusActivity extends AppCompatActivity implements Listeners.BackListener, Listeners.contactActions {
    private ActivityContactusBinding binding;
    private String lang;
    private String twitter, whats, email, phone;
    private Intent intent;
    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Locale.getDefault().getLanguage()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contactus);
        initView();
        getAppData();
    }

    private void getAppData() {

        Api.getService(Tags.base_url)
                .getSetting()
                .enqueue(new Callback<SettingModel>() {
                    @Override
                    public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            twitter = response.body().getSettings().getTwitter();
                            whats = response.body().getSettings().getWhatsapp();
                            email = response.body().getSettings().getEmail1();
                            phone = response.body().getSettings().getPhone1();
                            binding.tvphone.setText(phone);
                            binding.tvemail.setText(email);
                            binding.tvtwitter.setText(twitter);
                            binding.tvwhats.setText(whats);
                        } else {
                            try {

                                Log.e("error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 500) {
                                //  Toast.makeText(AboutAppActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                //Toast.makeText(AboutAppActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingModel> call, Throwable t) {
                        try {

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //    Toast.makeText(AboutAppActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    //    Toast.makeText(AboutAppActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {
                        }
                    }
                });

    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setContactlistener(this);
        binding.setLang(lang);


    }


    @Override
    public void back() {
        finish();
    }

    @Override
    public void email() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Gahzly");
        i.putExtra(Intent.EXTRA_TEXT, "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getResources().getString(R.string.not_av), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void call() {
        if (phone != null) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+9200 03450", null));

            if (intent != null) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(intent);
                    }
                } else {
                    startActivity(intent);
                }
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.not_av), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void whats() {
        if (whats != null) {

            whats = whats.replace("(", "").replace(")", "").replaceAll("_", "");

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + whats));
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.not_av, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void twitter() {
        if (twitter != null) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.not_av, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (this.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    startActivity(intent);
                } else {

                }
                return;
            }
        }
    }
}
