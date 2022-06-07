package com.example.foodordering;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodordering.restaurant.ResturantCommentsActivity;

import org.w3c.dom.Text;

public class Restaurant extends AppCompatActivity implements View.OnClickListener {
    private String []foodname= {"KFC XL set", "KFC family set", "KFC egg tart", "coca cola", "sevenup"};
    private String []foodprice={"200$","400$","180$","50$","50$"};
    private int []foodimg={R.drawable.kfcxlset,R.drawable.kfcfamilyset,R.drawable.eggcake,R.drawable.cocacola,R.drawable.sevenup};

    private Button btnComment;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        ListView foodlist =findViewById(R.id.restaurantFoodList);
        TextView restaurantname=findViewById(R.id.restaurantName);
        ImageView restaurantimg=findViewById(R.id.restaurantImg);
        ImageView back=findViewById(R.id.backkey);

        back.setOnClickListener(this);
        restaurantAdapter = new RestaurantAdapter(getApplicationContext(),foodimg,foodname,foodprice);
        foodlist.setAdapter(restaurantAdapter);

        btnComment = (Button) findViewById(R.id.restaurant_btn_comment);
        System.out.println(getIntent().getIntExtra("index", -1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backkey:
                finish();
                break;
        }
    }

    public void showComment(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ResturantCommentsActivity.class);
        startActivity(intent);
    }
}