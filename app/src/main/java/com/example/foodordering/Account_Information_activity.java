package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodordering.BottomNavigationView.account;
import com.example.foodordering.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Account_Information_activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private ImageView information_iv;
    private ListView information_lv;

    private final int INFO_ACTIVITY_CODE = 123;

    private final String[] dataKeys = {
            "Name",
            "Email",
            "Identity",
            "Phone",
            "Address"
    };

    private HashMap<String, String> info = new HashMap<>();

    private String identity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        information_iv = (ImageView) findViewById(R.id.infomation_iv);
        information_lv = (ListView) findViewById(R.id.infomation_lv);

        ArrayList<String> dataList = new ArrayList<>();
        for (String s : dataKeys) {
            info.put(s, "");
            dataList.add(s + ": ");
        }
        String[] data = dataList.toArray(new String[0]);

        ArrayAdapter<String>adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data
        );
        information_lv.setAdapter(adapter);
        information_lv.setOnItemClickListener(this);

        information_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        // System.out.println(index);
        final String key = this.dataKeys[index];

        // Avoid user to edit identity (?)
        if (key.equals("Identity")) {
            return;
        }

        Intent intent = new Intent();
        intent.setClass(this, EditActivity.class);

        intent.putExtra("dataKey", key);

        startActivityForResult(intent, this.INFO_ACTIVITY_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == this.INFO_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String key = intent.getStringExtra("Key");
                String input = intent.getStringExtra("Input");


            }
        }
    }

}