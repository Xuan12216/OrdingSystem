package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.foodordering.BottomNavigationView.account;

public class Account_Information_activity extends AppCompatActivity implements View.OnClickListener
{
    ImageView information_iv;
    ListView information_lv;
    String[] data = {"Name :","Email :","Identidy :","Phone :","Address :"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        information_iv = (ImageView) findViewById(R.id.infomation_iv);
        information_lv = (ListView) findViewById(R.id.infomation_lv);

        ArrayAdapter<String>adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
        information_lv.setAdapter(adapter);

        information_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        finish();
    }
}