package uz.coder.davomatapp;

import android.app.Application;

public class App extends Application {
    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
