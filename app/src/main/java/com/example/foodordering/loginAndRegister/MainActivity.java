package com.example.foodordering.loginAndRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodordering.CourierActivity;
import com.example.foodordering.R;
import com.example.foodordering.sellerActivity.SellerStartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.foodordering.loginAndRegister.First_activity;
import com.example.foodordering.loginAndRegister.Login_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn2;
    //private FirebaseAuth myAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ProgressBar progressBar;
    //private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        //myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        progressBar.setVisibility(View.VISIBLE);

        super.onStart();

        if (user == null)
        {
            progressBar.setVisibility(View.GONE);
        }
        if (user != null)
        {
            userID = user.getUid();
            reference.child(userID).child("identity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task)
                {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,String.valueOf(task.getResult().getValue()),Toast.LENGTH_LONG).show();
                        //FirebaseUser mAuthUser = myAuth.getCurrentUser();

                        if (String.valueOf(task.getResult().getValue()).matches("Seller"))
                        {
                            jumpActivity(1);
                        }
                        else if (String.valueOf(task.getResult().getValue()).matches("Buyer"))
                        {
                            jumpActivity(2);
                        }
                        else if (String.valueOf(task.getResult().getValue()).matches("Courier"))
                        {
                            jumpActivity(3);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void jumpActivity(int i)
    {
        switch (i)
        //if (mAuthUser!=null)
        {
            case 1:
                startActivity(new Intent(this, SellerStartActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, First_activity.class));
                break;
            case 3:
                startActivity(new Intent(this, CourierActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        startActivity(new Intent(this, Login_activity.class));
    }
}