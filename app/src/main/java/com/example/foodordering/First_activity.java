package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.BottomNavigationView.account;
import com.example.foodordering.BottomNavigationView.cart;
import com.example.foodordering.BottomNavigationView.home;
import com.example.foodordering.BottomNavigationView.search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class First_activity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    home home=new home();
    search search=new search();
    cart cart=new cart();
    account account=new account();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        bottomNavigationView=findViewById(R.id.bottommenu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,home).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homemenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,home).commit();
                        return true;
                    case R.id.searchmenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,search).commit();
                        return true;
                    case R.id.cartmenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,cart).commit();
                        return true;
                    case R.id.accountmenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,account).commit();
                        return true;
                }
                return false;
            }
        });
    }
}