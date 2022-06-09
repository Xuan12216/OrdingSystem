package com.example.foodordering.BottomNavigationView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.Restaurant;
import com.example.foodordering.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ArrayList<String> mealName = new ArrayList<String>();
        ArrayList<String> mealPrice = new ArrayList<String>();
        ArrayList<String> mealImage = new ArrayList<String>();

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Meals");

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot MealSnapshot:snapshot.getChildren())
                {
                    User userProfile = MealSnapshot.getValue(User.class);

                    if(!userProfile.equals(null))
                    {
                        mealName.add(userProfile.meal_name);
                        mealImage.add(userProfile.image_link);
                        mealPrice.add(userProfile.meal_price);

                        ListView search_lv =view.findViewById(R.id.search_lv);
                        if(mealName.size()>0 && getContext()!=null)
                        {
                            CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getContext(),mealName,mealImage,mealPrice);
                            search_lv.setAdapter(customBaseAdapter);
                            search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                {
                                }
                            });}
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something wrong !",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public class CustomBaseAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<String> meal_Name;
        ArrayList<String> meal_Images;
        ArrayList<String> meal_Prices;
        LayoutInflater inflater;

        public CustomBaseAdapter(Context ctx ,ArrayList<String> mealName, ArrayList<String> mealImages, ArrayList<String> mealPrices)
        {
            this.context = ctx;
            this.meal_Name = mealName;
            this.meal_Images = mealImages;
            this.meal_Prices = mealPrices;
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount()
        {
            return meal_Images.size();
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
            view = inflater.inflate(R.layout.search_layout,null);

            TextView textView_name = (TextView) view.findViewById(R.id.search_nameTv);
            TextView textView_price = (TextView) view.findViewById(R.id.search_priceTv);
            ImageView imageView = (ImageView) view.findViewById(R.id.search_iv);

            textView_name.setText("Meal Name: " + meal_Name.get(i));
            textView_price.setText("Meal Price: " + meal_Prices.get(i));
            String url = meal_Images.get(i);
            Picasso.get().load(url).into(imageView);

            return view;
        }
    }
}