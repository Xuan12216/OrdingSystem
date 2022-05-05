package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn2;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser mAuthUser = myAuth.getCurrentUser();

        if (mAuthUser!=null)
        {
            startActivity(new Intent(this,First_activity.class));
        }
    }

    @Override
    public void onClick(View view)
    {
        startActivity(new Intent(this,Login_activity.class));
    }
}