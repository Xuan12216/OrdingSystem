package com.example.foodordering.loginAndRegister;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.CourierActivity;
import com.example.foodordering.R;
import com.example.foodordering.sellerActivity.SellerStartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_activity extends AppCompatActivity implements View.OnClickListener
{
    private TextView register;
    private EditText editEmail,editPassword;
    private Button signIn;
    private FirebaseUser user;
    private FirebaseAuth myAuth;
    private DatabaseReference reference;
    private String userID;
    private ProgressBar progressBar;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        register = (TextView) findViewById(R.id.register);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        myAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(this);
        signIn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.register:
                startActivity(new Intent(this, Register_activity.class));
                break;
            case R.id.signIn:
                userLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgetPassword_activity.class));
                break;
        }
    }
    private void userLogin()
    {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
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
            editPassword.setError("Min password length should be 6 characters !");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {//redirect to First_activity
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    userID = user.getUid();

                    reference.child(userID).child("identity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task)
                        {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(Login_activity.this,"Error",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Login_activity.this,String.valueOf(task.getResult().getValue()),Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
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
                            }
                        }
                    });
                    Toast.makeText(Login_activity.this,"Successful Login !",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(Login_activity.this, First_activity.class));
                }
                else
                {
                    Toast.makeText(Login_activity.this,"Failed to Login ! Please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void jumpActivity(int i)
    {
        switch (i)
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
}