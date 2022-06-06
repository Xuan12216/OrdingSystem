package com.example.foodordering.account_information;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodordering.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class info_btn_edit extends AppCompatActivity implements View.OnClickListener
{
    private ImageView info_Iv;
    private Button info_Submit;
    private EditText info_et_Phone,info_et_Address;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_btn_edit);

        info_et_Phone = (EditText) findViewById(R.id.info_et_phone);
        info_et_Address = (EditText) findViewById(R.id.info_et_address);
        info_Submit = (Button) findViewById(R.id.info_submit);
        info_Iv = (ImageView) findViewById(R.id.info_iv);

        info_Iv.setOnClickListener(this);
        info_Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.info_iv:
                finish();
                break;
            case R.id.info_submit:
                updateData();
                break;
        }
    }

    private void updateData()
    {
        String Phone = info_et_Phone.getText().toString(); //edit text
        String Address = info_et_Address.getText().toString(); //edit text

        user = FirebaseAuth.getInstance().getCurrentUser(); //firebase Current User
        reference = FirebaseDatabase.getInstance().getReference("Users"); //目錄
        userID = user.getUid(); // Current User Uid

        if (!info_et_Phone.getText().toString().matches("")) // 如果edit text phone 有輸入的話
        {
            reference.child(userID).child("phoneNumber").setValue(Phone);//update phone的資料
        }
        if (!info_et_Address.getText().toString().matches("")) // 如果edit text address 有輸入的話
        {
            reference.child(userID).child("address").setValue(Address);//update address的資料
        }

        Toast.makeText(getApplicationContext(),"Update Sucessful!",Toast.LENGTH_LONG).show();
    }
}