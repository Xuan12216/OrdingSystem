package com.example.foodordering.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> shop = new ArrayList<String>();
        ArrayList<String> shopid = new ArrayList<String>();
        ArrayList<String> shopimg = new ArrayList<>();
        //int[] shopimg={R.drawable.kfc,R.drawable.kfc};
        // Intent intent=new Intent();
        String shopname[]={"KFC","KFC2"};
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot shopsnapshot:snapshot.getChildren()) {
                    User userProfile = shopsnapshot.getValue(User.class);

                    if(userProfile.identity.equals("Seller") && !userProfile.equals(null)){

                        shop.add(userProfile.restaurantName);
                        shopimg.add(userProfile.icon_base64);
                        shopid.add(shopsnapshot.getKey());

                        ListView lv =v.findViewById(R.id.mainlv);
                        if(shop.size()>0 && getContext()!=null){
                            MainAdapter mainAdapter=new MainAdapter(getContext(),shopimg,shop);
                            lv.setAdapter(mainAdapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent innerIntent = new Intent(getActivity(), Restaurant.class);

                                    innerIntent.putExtra("sellerID", shopid.get(i));
                                    innerIntent.putExtra("index", i);
                                    innerIntent.putExtra("userID",shopid.get(i));

                                    startActivity(innerIntent);
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
        //System.out.println(shop+"#");

        return v;
    }
}

// Creating key value pair (Seller full name : Seller UID)
        /*
        HashMap<String, String> sellerUID = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Seller");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // System.out.println(snapshot.getChildren());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        String k = dataSnapshot.getKey();

                        // getValue() returns a HashMap...
                        // Example: {UID=0iZUvl7fKNTLE9QCyChBAM673QI2}
                        HashMap<String, String> temp = (HashMap<String, String>) dataSnapshot.getValue();
                        String v = temp.get("UID");

                        sellerUID.put(k, v);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // pass
            }
        });
         */