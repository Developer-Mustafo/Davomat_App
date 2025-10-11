package uz.coder.davomatapp;

import android.app.Application;
import android.content.Context;

import uz.coder.davomatapp.todo.LocaleHelper;

public class App extends Application {
    public static Application application;

    @Override
    protected void attachBaseContext(Context base) {
        Context newBase = LocaleHelper.INSTANCE.setLocale(base, "uz");
        super.attachBaseContext(newBase);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
