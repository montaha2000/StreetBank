package com.afq.streetbank.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afq.streetbank.Adapter.RecyclerViewAdapter;
import com.afq.streetbank.DetailsActivity;
import com.afq.streetbank.Model.Item;
import com.afq.streetbank.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BuyFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();


    public static BuyFragment newInstance(int index) {
        BuyFragment fragment = new BuyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        final RecyclerView rv = root.findViewById(R.id.rv);

        RecyclerViewAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        ArrayList<Item> mUploads = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new RecyclerViewAdapter(mUploads, getActivity());

        rv.setHasFixedSize(true);

        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(mAdapter);

        mAdapter.OnItemClickListener(position -> {

            Intent detailIntent = new Intent(getActivity(), DetailsActivity.class);
            Item clickedItem = mUploads.get(position);

            detailIntent.putExtra("item", clickedItem.getItem());
            detailIntent.putExtra("desc", clickedItem.getDesc());
            detailIntent.putExtra("price", clickedItem.getPrice());
            detailIntent.putExtra("name", clickedItem.getUserName());
            detailIntent.putExtra("key",clickedItem.getkey());

            Log.i("AFQ","clickedItem.getPrice()" + clickedItem.getPrice());

            startActivity(detailIntent);

        });

        myRef.child("Items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snap : snapshot.getChildren()) {

                    if (snap.getKey().equals("sell") && snap.getValue().equals(true)) {
                        Item i = snapshot.getValue(Item.class);

                        mUploads.add(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}