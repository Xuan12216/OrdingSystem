package com.example.foodordering.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_activity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView imageView2;
    private Button signIn,seller,buyer;
    private EditText editFullName,editEmail,editPassword;
    private ProgressBar progressBar;
    String identity = "none";
    String phone = "none";
    String address = "none";

    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myAuth = FirebaseAuth.getInstance();
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        signIn = (Button) findViewById(R.id.signIn);
        seller = (Button) findViewById(R.id.seller_lv);
        buyer = (Button) findViewById(R.id.buyer);
        editFullName = (EditText) findViewById(R.id.fullName);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        imageView2.setOnClickListener(this);
        signIn.setOnClickListener(this);
        seller.setOnClickListener(this);
        buyer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.imageView2:
                startActivity(new Intent(this, Login_activity.class));
                break;
            case R.id.signIn:
                if (identity == "none")
                {
                    Toast.makeText(Register_activity.this,"Please select a identity !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser();
                }
                break;
            case R.id.seller_lv:
                identity = "Seller";
                Toast.makeText(Register_activity.this,"Your Identity is Seller!",Toast.LENGTH_LONG).show();
                break;
            case R.id.buyer:
                identity = "Buyer";
                Toast.makeText(Register_activity.this,"Your Identity is Buyer!",Toast.LENGTH_LONG).show();
        }
    }

    private void registerUser()
    {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String fullname = editFullName.getText().toString().trim();
        if(fullname.isEmpty())
        {
            editFullName.setError("Username is required !");
            editFullName.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editEmail.setError("Email is required !");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editEmail.setError("Please provide valid Email !");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editPassword.setError("Password is required !");
            editPassword.requestFocus();
            return;
        }
        if (password.length()<6)
        {
            editPassword.setError("Min password lenght should be 6 characters !");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                User user = new User(fullname,email, identity,phone,address);

                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task1 ->
                {
                    if (task1.isSuccessful())
                    {
                        Toast.makeText(Register_activity.this,"Registered successfully!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                        if(identity == "Seller")
                        {
                            FirebaseDatabase.getInstance().getReference("Seller").child(fullname).child("UID").setValue(FirebaseAuth.getInstance().getUid());
                        }

                        startActivity(new Intent(Register_activity.this,Login_activity.class));
                    }
                    else
                    {
                        Toast.makeText(Register_activity.this,"Failed to register ! Try again !",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
            else
            {
                Toast.makeText(Register_activity.this,"Failed to register! Email was Exists!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}