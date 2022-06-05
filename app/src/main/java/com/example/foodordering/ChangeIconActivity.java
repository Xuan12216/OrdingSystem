package com.example.foodordering;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodordering.user.User;
import com.example.foodordering.utils.ImageUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeIconActivity extends AppCompatActivity {

    private final int UPLOAD_IMAGE_REQ_CODE = 123;

    private Button btnChange;
    private Button btnSave;
    private Button btnCancel;

    private ImageView imgView;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String userID;
    private User user;

    private String iconBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_icon);

        btnChange = (Button) findViewById(R.id.icon_btn_change);
        btnSave = (Button) findViewById(R.id.icon_btn_save);
        btnCancel = (Button) findViewById(R.id.icon_btn_cancel);

        imgView = (ImageView) findViewById(R.id.icon_img_view);

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

                        String s = user.icon_base64;
                        Bitmap bitmapIcon = ImageUtils.imageFromBase64(s);

                        if (bitmapIcon != null) {
                            imgView.setImageBitmap(bitmapIcon);
                        }
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

    public void uploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, UPLOAD_IMAGE_REQ_CODE);
    }

    public void save(View view) {
        // Update database
        reference.child(userID).child("icon_base64").setValue(this.iconBase64);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == UPLOAD_IMAGE_REQ_CODE) {
                Uri dataURI = data.getData();
                imgView.setImageURI(dataURI);

                this.iconBase64 = ImageUtils.imageToBase64(this.getContentResolver(), dataURI);
            }
        }
    }
}