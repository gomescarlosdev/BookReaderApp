package br.com.poo2.bookreaderapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

    private final SharedPreferences appSharedPreferences;

    public AppSharedPreferences(Context context) {
        appSharedPreferences = context.getSharedPreferences("bookReaderPreferences", Context.MODE_PRIVATE);
    }

    public void storeString(String key, String value) {
        appSharedPreferences.edit().putString(key, value).apply();
    }

    public String getStoredString(String key) {
        return appSharedPreferences.getString(key, "");
    }

    public void storeBoolean(String key, boolean value) {
        appSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getStoredBoolean(String key) {
        return appSharedPreferences.getBoolean(key, false);
    }

    public void clearPreferences() {
        appSharedPreferences.edit().clear().apply();
    }
}