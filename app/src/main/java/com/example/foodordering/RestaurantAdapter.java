package com.example.foodordering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.AccessControlContext;

public class RestaurantAdapter extends BaseAdapter {
    AccessControlContext context;
    String foodname[];
    int foodimg[];
    LayoutInflater inflater;
    String foodprice[];
    public RestaurantAdapter(AccessControlContext context, int[] foodimg, String[] foodname, String[] foodprice) {
        this.context=context;
        this.foodimg=foodimg;
        this.foodname=foodname;
        this.foodprice=foodprice;
        //inflater = LayoutInflater.from();
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
        fd_name.setText(foodname[position]);
        foodImg.setImageResource(foodimg[position]);
        fdprice.setText(foodprice[position]);
        return view;
    }
}
