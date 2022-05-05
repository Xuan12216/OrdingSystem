package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class First_activity extends AppCompatActivity implements View.OnClickListener
{
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        logout = (Button) findViewById(R.id.logOut);

        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login_activity.class));
        Toast.makeText(First_activity.this,"Sign Out Successful!",Toast.LENGTH_LONG).show();
    }
}