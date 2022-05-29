package com.example.foodordering.sellerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.AddMeals_activity;
import com.example.foodordering.BottomNavigationView.account;
import com.example.foodordering.R;
import com.example.foodordering.account_information.Account_Information_activity;
import com.example.foodordering.loginAndRegister.MainActivity;
import com.example.foodordering.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerStartActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView sellerStart_Tv;
    private Button sellerLogout_Btn;
    ListView seller_Lv;
    String[] data = {"Account Information", "Add Meal", "Restaurant Information", "Order Details"};
    int dataImages[] = {R.drawable.user,R.drawable.add,R.drawable.store,R.drawable.order};

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_start);

        sellerLogout_Btn = (Button) findViewById(R.id.sellerLogout_btn);
        sellerStart_Tv = (TextView) findViewById(R.id.sellerStart_tv);
        sellerStart_Tv.setTypeface(null, Typeface.BOLD);
        seller_Lv = (ListView) findViewById(R.id.seller_lv);

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),data,dataImages);
        seller_Lv.setAdapter(customBaseAdapter);
        seller_Lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                jumpActivity(i);
            }
        });

        sellerLogout_Btn.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null)
                {
                    sellerStart_Tv.setText("Hello Seller : " + userProfile.fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getApplicationContext(),"Something wrong !",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void jumpActivity(int i)
    {
        switch (i)
        {
            case 0:
                startActivity(new Intent(this,Account_Information_activity.class));
                break;
            case 1:
                startActivity(new Intent(this, AddMeals_activity.class));
                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.sellerLogout_btn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this,"Sign Out Successful!",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class CustomBaseAdapter extends BaseAdapter
    {
        Context context;
        String data[];
        int dataImages[];
        LayoutInflater inflater;

        public CustomBaseAdapter(Context ctx ,String[] data, int[] dataImages)
        {
            this.context = ctx;
            this.data = data;
            this.dataImages = dataImages;
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount()
        {
            return data.length;
        }

        @Override
        public Object getItem(int i)
        {
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            view = inflater.inflate(R.layout.simple_list_item_1,null);
            TextView textView = (TextView) view.findViewById(R.id.account_tv);
            ImageView imageView = (ImageView) view.findViewById(R.id.account_iv);
            textView.setText(data[i]);
            imageView.setImageResource(dataImages[i]);
            return view;
        }
    }
}