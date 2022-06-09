package com.example.foodordering.loginAndRegister;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.BottomNavigationView.AccountFragment;
import com.example.foodordering.BottomNavigationView.CartFragment;
import com.example.foodordering.BottomNavigationView.HomeFragment;
import com.example.foodordering.BottomNavigationView.SearchFragment;
import com.example.foodordering.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class First_activity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    HomeFragment home=new HomeFragment();
    SearchFragment search=new SearchFragment();
    CartFragment cart=new CartFragment();
    AccountFragment account=new AccountFragment();

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