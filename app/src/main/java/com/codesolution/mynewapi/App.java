package com.codesolution.mynewapi;

import android.app.Application;

public class App extends Application {

    public static Singleton singleton;

    public static Singleton getSingleton() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton=new Singleton();
    }

}
