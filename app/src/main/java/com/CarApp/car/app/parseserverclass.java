package com.CarApp.car.app;

import android.app.Application;

import com.parse.Parse;

public class parseserverclass extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("w6gFO9k1LQK0V7bgVBpKuS4AIvNzRVxW2r4MBTZh")
                .clientKey("toKkqjuqdLq22BtArfrrxFb1oMwAsPVGrFElhOgD")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
