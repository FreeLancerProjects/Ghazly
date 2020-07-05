package com.ghazly.share;


import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.ghazly.language.Language;
import com.ghazly.share.TypefaceUtil;

import java.util.Locale;


public class App extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Locale.getDefault().getLanguage()));
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/ar_font.ttf");

    }
}

