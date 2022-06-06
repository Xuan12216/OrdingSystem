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

public class AddMealCategoryAdapterActivity extends BaseAdapter {
    Context context;
    String meal_category[];
    int foodimg[];
    LayoutInflater inflater;
    String foodprice[];
    public AddMealCategoryAdapterActivity(Context context, int[] foodimg, String[] meal_category, String[] foodprice) {
        this.context=context;
        this.foodimg=foodimg;
        this.meal_category=meal_category;
        this.foodprice=foodprice;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodimg.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.foodlistshow,null);
        TextView fd_name = (TextView) view.findViewById(R.id.fdname);
        ImageView foodImg = (ImageView) view.findViewById(R.id.fdImg);
        TextView fdprice =(TextView)view.findViewById(R.id.fdprice);
        fd_name.setText(meal_category[position]);
        foodImg.setImageResource(foodimg[position]);
        fdprice.setText(foodprice[position]);
        return view;
    }
}
