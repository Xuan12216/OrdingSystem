package com.example.foodordering;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.restaurant.ResturantCommentsActivity;
import com.example.foodordering.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> foodname = new ArrayList<String>();
    ArrayList<String> foodprice = new ArrayList<String>();
    ArrayList<String> foodimg = new ArrayList<String>();

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        btnComment = (Button) findViewById(R.id.restaurant_btn_comment);
        String userId=getIntent().getStringExtra("userID");
        //System.out.println(userId+"#");

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if (user!=null){
                    restaurantname.setText(user.restaurantName);
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
        intent.setClass(this, ResturantCommentsActivity.class);
        startActivity(intent);
    }
}