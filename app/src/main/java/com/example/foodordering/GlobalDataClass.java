package com.example.foodordering;

import android.app.Application;

// add ( android:name=".GlobalDataClass" ) in android manifest for be able to get data

public class GlobalDataClass extends Application {
    private Integer global_meals_count = 0;

    public int getGlobal_meals_count() {
        return global_meals_count;
    }

    public void setGlobal_meals_count(int global_meals_count) {
        this.global_meals_count = global_meals_count;
    }
}
