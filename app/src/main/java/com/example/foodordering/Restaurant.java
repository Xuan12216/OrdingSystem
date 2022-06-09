package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodordering.restaurant.RestaurantCommentsActivity;
import com.example.foodordering.user.User;
import com.example.foodordering.utils.ImageUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> foodname = new ArrayList<String>();
    ArrayList<String> foodprice = new ArrayList<String>();
    ArrayList<String> foodimg = new ArrayList<String>();

    private Button btnComment;
    private RestaurantAdapter restaurantAdapter;

    private String sellerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        ListView foodlist =findViewById(R.id.restaurantFoodList);
        TextView restaurantname=findViewById(R.id.restaurantName);
        ImageView restaurantimg=findViewById(R.id.restaurantImg);
        ImageView back=findViewById(R.id.backkey);

        Intent intent = getIntent();

        back.setOnClickListener(this);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        btnComment = (Button) findViewById(R.id.restaurant_btn_comment);
        String userId=intent.getStringExtra("userID");
        //System.out.println(userId+"#");

        sellerID = intent.getStringExtra("sellerID");

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if (user!=null){
                    restaurantname.setText(user.restaurantName);
                    restaurantimg.setImageBitmap(ImageUtils.imageFromBase64(user.icon_base64));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference refmeals = FirebaseDatabase.getInstance().getReference("Meals");
        refmeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot mealsSnapshot:snapshot.getChildren()){

                    if(mealsSnapshot.child("seller_id").getValue().equals(userId)){

                        foodimg.add(mealsSnapshot.child("image_link").getValue().toString());
                        foodname.add(mealsSnapshot.child("meal_price").getValue().toString());
                        foodprice.add(mealsSnapshot.child("meal_name").getValue().toString());

                    }
                }
                restaurantAdapter = new RestaurantAdapter(getApplicationContext(), foodimg, foodname, foodprice);
                foodlist.setAdapter(restaurantAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        intent.setClass(this, RestaurantCommentsActivity.class);
        intent.putExtra("sellerID", sellerID);
        startActivity(intent);
    }
}