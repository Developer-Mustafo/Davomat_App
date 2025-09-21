package uz.coder.davomatapp.todo;

import android.content.Context;
import android.content.SharedPreferences;

import uz.coder.davomatapp.R;

public class SharedPref {
    private static SharedPreferences sharedPreferences;

    private SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), android.content.Context.MODE_PRIVATE);
    }
    public static SharedPref getInstance(Context context) {
        return new SharedPref(context);
    }

    public void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
    public void setInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }
    public void setLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }
    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }
    public void setFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }
    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0);
    }
    public void clear(){
        sharedPreferences.edit().clear().apply();
    }
}
