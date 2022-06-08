package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodordering.loginAndRegister.First_activity;
import com.example.foodordering.loginAndRegister.Login_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.FunctionReference;

public class AddMealCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private String category[];
    private Integer i = 0;
    private DatabaseReference categoryRef, databaseReference;

    private ImageView backButton;
    private ListView listView;
    private AddMealCategoryAdapterActivity categoryAdapterActivity;

    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_category);

        arrayList.add("test");

        categoryRef = FirebaseDatabase.getInstance().getReference().child("MealCategory");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        backButton = (ImageView) findViewById(R.id.button_back_add_category);
        listView = (ListView) findViewById(R.id.list_view_add_category);

        // list view adapter
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


        categoryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String categoryValue = dataSnapshot.getValue(AddMealCategoryAdapterActivity.class).toString();
                arrayList.add(categoryValue);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddMealCategoryActivity.this, "Category "+i+"choosen : " + arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        /*
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference query = mDatabase.child("MealCategory");
        query.orderByChild("categoryName").startAt("category1");
        query.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            String name = (String) messageSnapshot.child("name").getValue();
                            Log.d("TAG", "category name is :"+name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

         */


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onClick(View view) {

    }
}