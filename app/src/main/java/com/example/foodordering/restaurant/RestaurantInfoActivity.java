package com.example.foodordering.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodordering.EditActivity;
import com.example.foodordering.R;
import com.example.foodordering.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantInfoActivity extends AppCompatActivity {

    private final int ACTIVITY_CODE = 123;

    // Dont change the order
    private final String[] keys = {
            "Name", "Address", "Phone number", "Opening hours"
    };
    private String[] values;

    private List<String> infoList;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;
    private User user;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        ctx = this;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = firebaseUser.getUid();

        reference.child(userID).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User u = dataSnapshot.getValue(User.class);

                    // All these has to be done in here
                    // Since this is an async call
                    if (u != null) {
                        user = u;

                        // Length of keys & values should be the same
                        // Like HashMap / Dict in Python
                        values = new String[] {
                                u.restaurantName, u.restaurantAddress,
                                u.restaurantPhoneNumber, u.restaurantOpeningHours
                        };

                        // Merge keys & values into 1 line for the ListView
                        infoList = new ArrayList<>();
                        for (int i = 0; i < keys.length; i++) {
                            String k = keys[i];

                            // Set empty string to "-" (For displaying)
                            String v = values[i].equals("") ? "-" : values[i];
                            v = v.replace("\n", " ");

                            infoList.add(k + ": " + v);
                        }

                        // Set adapter
                        adapter = new ArrayAdapter<String>(
                                ctx,
                                android.R.layout.simple_list_item_1,
                                infoList
                        );

                        listView = (ListView) findViewById(R.id.restaurant_info_lv);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    onListViewItemClick(i);
                                }
                            }
                        );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Something went wrong !",
                            Toast.LENGTH_LONG
                    ).show();
                    // System.out.println("ERROR");
                }
            }
        );
    }

    private void onListViewItemClick(int index) {
        String key = keys[index];
        String value = values[index];

        Intent intent = new Intent();
        intent.putExtra("Key", key);
        intent.putExtra("Value", value);

        intent.setClass(this, EditActivity.class);
        startActivityForResult(intent, ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String key = intent.getStringExtra("Key");
                String newValue = intent.getStringExtra("Input");
                newValue = newValue.trim();

                updateInfo(key, newValue);
            }
        }
    }

    private void updateInfo(String key, String newValue) {
        String k = null;
        if (key.equals(keys[0])) {
            k = "restaurantName";
            user.restaurantName = newValue;
        }
        else if (key.equals(keys[1])) {
            k = "restaurantAddress";
            user.restaurantAddress = newValue;
        }
        else if (key.equals(keys[2])) {
            k = "restaurantPhoneNumber";
            user.restaurantPhoneNumber = newValue;
        }
        else if (key.equals(keys[3])) {
            k = "restaurantOpeningHours";
            user.restaurantOpeningHours = newValue;
        }

        // Some unknown key?
        // Add this if check just in case any unknown key were generated
        if (k == null) {
            return;
        }

        // Update adapter's data
        // There should be a better way to but just leave it like this for now.
        values = new String[] {
                user.restaurantName, user.restaurantAddress,
                user.restaurantPhoneNumber, user.restaurantOpeningHours
        };

        infoList = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            String k2 = keys[i];

            String v = values[i].equals("") ? "-" : values[i];
            v = v.replace("\n", " ");

            String s = k2 + ": " + v;
            infoList.add(s);
            System.out.println(s);
        }

        // Set adapter
        adapter = new ArrayAdapter<String>(
                ctx,
                android.R.layout.simple_list_item_1,
                infoList
        );
        listView.setAdapter(adapter);

        // Update database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = firebaseUser.getUid();

        reference.child(userID).child(k).setValue(newValue);
    }
}