package com.example.foodordering;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.FontResourcesParserCompat;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlContext;
import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    Bitmap bitmap;
    Handler handler=new Handler();
    Context context;
    ArrayList<String> foodname;
    ArrayList<String> foodimg;
    LayoutInflater inflater;
    ArrayList<String> foodprice;
    public RestaurantAdapter(Context context, ArrayList<String> foodimg, ArrayList<String> foodname, ArrayList<String> foodprice) {
        this.context=context;
        this.foodimg=foodimg;
        this.foodname=foodname;
        this.foodprice=foodprice;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodimg.size();
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

        fd_name.setText(foodname.get(position));
        fdprice.setText(foodprice.get(position));
        String url=foodimg.get(position);
        Picasso.get().load(url).into(foodImg);
        foodImg.setImageBitmap(bitmap);
        return view;
    }

}
