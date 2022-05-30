
package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/*
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import java.io.IOException;
*/


public class AddMeals_activity extends AppCompatActivity {

    private String categoryName, description, price, pname, saveCurrentDate, saveCurrentTime, userID;
    private Button addNewProductButton;
    private ImageView inputProductImage, backButton;
    private EditText inputProductName, inputProductDescription, inputProductPrice;
    private static final int galleryPick = 1;
    private Uri imageFilePathUri;
    private String productKey, downloadImageUrl;
    private StorageReference productImageRef; // ProductImageRef
    private DatabaseReference productsRef, sellerRef; // ProductRef->added product, sellerref->seller Info
    private ProgressDialog loadingBar; // LoadingBar

    private String sName, sEmail, sPhone, sID, sAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meals);

        /*
        FirebaseDatabase.getInstance().getReference("users/randomUserId1") and
        FirebaseDatabase.getInstance().getReference().child("users/randomUserId1") and
        FirebaseDatabase.getInstance().getReference().child("users").child("randomUserId1")
        will have the same result, pointing at "your-project-name/users/randomUserId1"
         */

        // this will get category from selected category in other class if added
        //categoryName = getIntent().getExtras().get("category").toString();

        productImageRef = FirebaseStorage.getInstance().getReference().child("Meal Images");
        productsRef = FirebaseDatabase.getInstance().getReference("Meals"); // product folder in firebase
        sellerRef = FirebaseDatabase.getInstance().getReference("Users"); // user folder in firebase

        backButton = (ImageView) findViewById(R.id.back_button);
        addNewProductButton = (Button) findViewById(R.id.add_product_button);
        inputProductImage = (ImageView) findViewById(R.id.input_image);
        inputProductName = (EditText) findViewById(R.id.input_meal_name);
        inputProductDescription = (EditText) findViewById(R.id.input_meal_description);
        inputProductPrice = (EditText) findViewById(R.id.input_meal_price);
        loadingBar = new ProgressDialog(this);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        inputProductImage.setOnClickListener((view) -> {
            OpenGallery();
        });

        addNewProductButton.setOnClickListener((view) -> {
            ValidateProductData();
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // get seller user info
        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // get user info in the user folder using existing file information
                            //sName = dataSnapshot.child("fullName").getValue().toString();
                            sAddress = dataSnapshot.child("address").getValue().toString();
                            sEmail = dataSnapshot.child("email").getValue().toString();
                            sPhone = dataSnapshot.child("phone").getValue().toString();
                            // maybe i can add user id in and from user info
                            //sID = dataSnapshot.child("id").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==galleryPick && resultCode==RESULT_OK && data!=null)
        {
            imageFilePathUri = data.getData();
            inputProductImage.setImageURI(imageFilePathUri);
        }
    }


    private void ValidateProductData()
    {
        pname = inputProductName.getText().toString();
        description = inputProductDescription.getText().toString();
        price = inputProductPrice.getText().toString();

        if (imageFilePathUri == null)
        {
            Toast.makeText(this, "Product image is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pname))
        {
            Toast.makeText(this, "Please write meal name!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "Please write meal description!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price))
        {
            Toast.makeText(this, "Please write meal price!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeProductInformation();
        }
    }


    private void storeProductInformation()
    {
        loadingBar.setTitle("Add New Meal");
        loadingBar.setMessage("Please wait while we are adding new meal");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentDate = currentTime.format(calendar.getTime());

        // productKey = saveCurrentDate + saveCurrentTime;
        productKey = userID + " " + pname;

        final StorageReference filePath = productImageRef.child(imageFilePathUri.getLastPathSegment() + productKey);

        final UploadTask uploadTask = filePath.putFile(imageFilePathUri);


        uploadTask.addOnFailureListener((e) -> {
            String message = e.toString(); // AddMeal is only a activity, change it names later
            Toast.makeText(AddMeals_activity.this, "Error: " + message, Toast.LENGTH_SHORT).show();  //being cut on end
                    loadingBar.dismiss();
        }).addOnSuccessListener((OnSuccessListener) (taskSnapshot) -> {
            Toast.makeText(AddMeals_activity.this, "Meal Image Uploaded Successfully", Toast.LENGTH_SHORT).show();  // being cut on the end

            Task<Uri> urlTask = uploadTask.continueWithTask((task) -> {
                if (!task.isSuccessful())
                {
                    throw task.getException();
                }

                downloadImageUrl = filePath.getDownloadUrl().toString();
                return filePath.getDownloadUrl();
            }).addOnCompleteListener((task) -> {
                if (task.isSuccessful())
                {
                    downloadImageUrl = task.getResult().toString();

                    Toast.makeText(AddMeals_activity.this, "got the Meal information", Toast.LENGTH_SHORT).show(); //cut on the end

                    SaveProductInfoToDatabase();
                }
            });
        });
    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("meal id", productKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("meal description", description);
        productMap.put("image", downloadImageUrl);
        //productMap.put("category", categoryName);
        productMap.put("meal price", price);
        productMap.put("meal name", pname);


        //productMap.put("sellerName", sName);
        productMap.put("seller address", sAddress);
        productMap.put("seller phone", sPhone);
        productMap.put("seller email", sEmail);
        //productMap.put("seller id", sID);
        productMap.put("identify", "seller");


        productsRef.child(productKey).updateChildren(productMap)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful())
                    {
                        Intent intent = new Intent (AddMeals_activity.this, AddMeals_activity.class);

                        loadingBar.dismiss();
                        Toast.makeText(AddMeals_activity.this, "Meal is Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AddMeals_activity.this, "Error: " + message, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
