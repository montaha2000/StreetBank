package com.afq.streetbank.ui.main;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afq.streetbank.Adapter.RecyclerViewAdapter;
import com.afq.streetbank.Model.Item;
import com.afq.streetbank.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BorrowFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");

    public static BorrowFragment newInstance(int index) {
        BorrowFragment fragment = new BorrowFragment();
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

        myRef.child("Items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snap : snapshot.getChildren()) {

                    if (snap.getKey().equals("borrow") && snap.getValue().equals(true)) {

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