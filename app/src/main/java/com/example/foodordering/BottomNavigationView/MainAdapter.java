package com.example.foodordering.BottomNavigationView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodordering.R;
import com.example.foodordering.utils.ImageUtils;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> shopname;
    ArrayList<String> shopimg;
    LayoutInflater inflater;
    String priceFood[];
    public MainAdapter(Context context, ArrayList<String> shopnimg, ArrayList<String> shopname) {
        this.context = context;
        this.shopimg = shopnimg;
        this.shopname =shopname;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shopimg.size();
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
        view = inflater.inflate(R.layout.maindailyshop,null);
        TextView tv_name = (TextView) view.findViewById(R.id.mainShopName);
        ImageView foodImg = (ImageView) view.findViewById(R.id.mainShopImg);
        tv_name.setText(shopname.get(position));
        foodImg.setImageBitmap(ImageUtils.imageFromBase64(shopimg.get(position)));

        return view;
    }
}
