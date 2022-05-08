package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/*
public class AddMeals extends AppCompatActivity {

    private String CategoryName, Description, Price, Pname, saveCurrentData, saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StrorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meals);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        AddNewProductButton = (Button) findViewById(R.id.add_product_button);
        InputProductImage = (ImageView) findViewById(R.id.input_image);
        InputProductName = (EditText) findViewById(R.id.input_meal_name);
        InputProductDescription = (EditText) findViewById(R.id.input_meal_description);
        InputProductPrice = (EditText) findViewById(R.id.input_meal_price);
        loadingBar = new ProgressDialog(this);

        InputProductImage.setOnClickListener((view) -> { OpenGallery(); } );

        AddNewProductButton.setOnClickListener((view) -> {
            ValidateProductData();
        });
    }


    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }


    private void ValidateProductData()
    {
        Pname = InputProductName.getText().toString();
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product price!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeProductInformation();
        }
    }


    private void storeProductInformation()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Please wait while we are adding new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
        saveCurrentData = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentData = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentData + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() * productRandomKey;

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener((e) -> {
            String message = e.toString(); // AddMeal is only a activity, change it names later
            Toast.makeText(AddMeals.this, "Error: " + message, Toast.  //being cut on end
                    loadingBar.dismiss();
        }).addOnSuccessListener((OnSuccessListener) (taskSnapshot) -> {
            Toast.makeText(AddMeals.this, "Product Image Uploaded Successfully");  // being cut on the end

            Task<Uri> urlTask = uploadTask.continueWithTask((task) -> {
                if (!task.isSuccessful())
                {
                    throw task.getException();
                }

                downloadImageUrl = filePath.getDownloadUrl().toString();
                return filePath.getDownloadUrl();

            })

                }


        );

    }

}
*/