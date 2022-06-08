
package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;

import com.example.foodordering.loginAndRegister.First_activity;
import com.example.foodordering.loginAndRegister.Login_activity;
import com.example.foodordering.restaurant.RestaurantInfoActivity;
import com.example.foodordering.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class AddMeals_activity extends AppCompatActivity {

    private String categoryName, description, price, pname, saveCurrentDate, saveCurrentTime, userID;
    private Button addNewProductButton, chooseCategoryButton;
    private ImageView inputProductImage, backButton;
    private EditText inputProductName, inputProductDescription, inputProductPrice;
    private static final int galleryPick = 1;
    private Uri imageFilePathUri;
    private String productKey, downloadImageUrl;
    private StorageReference productImageRef; // ProductImageRef
    private DatabaseReference productsRef, sellerRef, sellerMealsRef; // ProductRef->added product, sellerRef->seller Info
    private ProgressDialog loadingBar; // LoadingBar
    private FirebaseUser user;

    private String sName, sEmail, sPhone, sAddress, sTotalMeals;


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

        user = FirebaseAuth.getInstance().getCurrentUser();

        sellerRef = FirebaseDatabase.getInstance().getReference("Users"); // user folder in firebase
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        // put meals inside user folder
        // productsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Meals"); // product folder in firebase
        productsRef = FirebaseDatabase.getInstance().getReference().child("Meals"); // product folder in firebase
        sellerMealsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("SellerMeals");

        // Affect how the image location is saved in storage database
        // productImageRef = FirebaseStorage.getInstance().getReference().child("Meals").child("Meal Images");
        productImageRef = FirebaseStorage.getInstance().getReference().child("Users").child(userID).child("Meal Images");


        backButton = (ImageView) findViewById(R.id.button_back_add_meal);
        chooseCategoryButton = (Button) findViewById(R.id.btn_to_add_category);
        addNewProductButton = (Button) findViewById(R.id.add_meal_button);
        inputProductImage = (ImageView) findViewById(R.id.input_image);
        inputProductName = (EditText) findViewById(R.id.input_meal_name);
        inputProductDescription = (EditText) findViewById(R.id.input_meal_description);
        inputProductPrice = (EditText) findViewById(R.id.input_meal_price);
        loadingBar = new ProgressDialog(this);

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

        chooseCategoryButton.setOnClickListener(view -> {
            startActivity(new Intent(this, AddMealCategoryActivity.class));
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
                            sPhone = dataSnapshot.child("phoneNumber").getValue().toString();

                            //sTotalMeals = dataSnapshot.child("totalMeals").getValue().toString();

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

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        // productKey = saveCurrentDate + saveCurrentTime;
        productKey = userID + " " + pname;

        //final StorageReference filePath = productImageRef.child(imageFilePathUri.getLastPathSegment() + productKey);
        final StorageReference filePath = productImageRef.child(productKey + " " + saveCurrentTime);

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
        productMap.put("meal_id", productKey);
        productMap.put("upload_date", saveCurrentDate);
        productMap.put("upload_time", saveCurrentTime);
        productMap.put("meal_description", description);
        productMap.put("image_link", downloadImageUrl);
        //productMap.put("category", categoryName);
        productMap.put("meal_price", price);
        productMap.put("meal_name", pname);
        //productMap.put("total meals", totalMeal);


        //productMap.put("sellerName", sName);
        productMap.put("seller_address", sAddress);
        productMap.put("seller_phone", sPhone);
        productMap.put("seller_email", sEmail);
        productMap.put("seller_id", userID);
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



/*
        int new_meal_count = Integer.parseInt(sTotalMeals);
        new_meal_count+=1;
        sTotalMeals = Integer.toString(new_meal_count);
        productMap.put("meal_" + new_meal_count, productKey);
        sellerMealsRef.child("sellerMeals").updateChildren(productMap);


 */



    }
}
