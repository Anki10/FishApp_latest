package com.qci.fish.activity;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.qci.fish.util.AppConstants;

public class BaseActivity extends AppCompatActivity {


    public void saveIntoPrefs(String key, String value) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String getFromPrefs(String key) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
        return prefs.getString(key, AppConstants.DEFAULT_VALUE);
    }


}
