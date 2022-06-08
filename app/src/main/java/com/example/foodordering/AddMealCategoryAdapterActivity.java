package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.AccessControlContext;


public class AddMealCategoryAdapterActivity{
    private String categoryName;
    //private String isApproved;

    private AddMealCategoryAdapterActivity() {}

    public AddMealCategoryAdapterActivity(String categoryName) {
        this.categoryName = categoryName;
        //this.isApproved = isApproved;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


//    public String getIsApproved() {
//        return this.isApproved;
//    }


    @Override
    public String toString() {
        return categoryName;
    }
}


/*
public class AddMealCategoryAdapterActivity{
    private String email;

    private AddMealCategoryAdapterActivity() {}

    public AddMealCategoryAdapterActivity(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return email;
    }
}

 */