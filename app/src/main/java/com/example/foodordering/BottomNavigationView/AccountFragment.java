package com.example.foodordering.BottomNavigationView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.example.foodordering.account_information.Account_Information_activity;
import com.example.foodordering.loginAndRegister.MainActivity;
import com.example.foodordering.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button signOutBtn;
    ListView accountListview;
    String[] data = {"Account Information"};
    int dataImages[] = {R.drawable.user};

    public AccountFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment account.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2)
    {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        signOutBtn = v.findViewById(R.id.logout);
        signOutBtn.setOnClickListener(this);

        accountListview = (ListView) v.findViewById(R.id.accountListview);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getContext(),data,dataImages);
        accountListview.setAdapter(customBaseAdapter);
        accountListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                switch (i)
                {
                    case 0:
                        startActivity(new Intent(getActivity(), Account_Information_activity.class));
                        break;
                }
            }
        });
        return v;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),MainActivity.class));
                Toast.makeText(getContext(),"Sign Out Successful!",Toast.LENGTH_LONG).show();
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