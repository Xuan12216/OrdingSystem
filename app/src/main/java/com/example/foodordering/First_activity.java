package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class First_activity extends AppCompatActivity implements View.OnClickListener
{
    BottomNavigationView bottomNavigationView;
    private Button logout;
    home home=new home();
    search search=new search();
    cart cart=new cart();
    account account=new account();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        logout = (Button) findViewById(R.id.logOut);

        logout.setOnClickListener(this);

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
    @Override
    public void onClick(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login_activity.class));
        Toast.makeText(First_activity.this,"Sign Out Successful!",Toast.LENGTH_LONG).show();
    }
}