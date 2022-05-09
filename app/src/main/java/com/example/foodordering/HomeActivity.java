package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private Integer[] shopimg = {R.drawable.hongya,R.drawable.laya,R.drawable.morning,R.drawable.mwd};
    private String[] shopname = {"弘爺漢堡","拉亞漢堡","早安有喜","麥味登"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView state = findViewById(R.id.imghome);
        GridView dailly=findViewById(R.id.dailyshow);
        GridView newshows=findViewById(R.id.newshow);
        GridView historyshows=findViewById(R.id.historyshow);

        imageadapter show=new imageadapter(this,shopimg,shopname);

        dailly.setAdapter(show);
        dailly.setNumColumns(2);

        newshows.setAdapter(show);
        newshows.setNumColumns(2);

        historyshows.setAdapter(show);
        historyshows.setNumColumns(2);
    }

    private class imageadapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private Integer[] shopimg;
        private String[] shopname;
        public imageadapter(Context context, Integer[] shopimg, String[] shopname) {
            this.context=context;
            this.shopimg=shopimg;
            this.shopname=shopname;
        }

        @Override
        public int getCount() {
            return shopname.length;
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
            if(inflater==null) {
                inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if(view==null) {
                view=inflater.inflate(R.layout.foodshow,null);
            }
            ImageView imageView=view.findViewById(R.id.shopshow);
            TextView textView=view.findViewById(R.id.shopnameshow);

            imageView.setImageResource(shopimg[position]);
            textView.setText(shopname[position]);
            imageView.setAdjustViewBounds(true);
            return view;
        }
    }
}