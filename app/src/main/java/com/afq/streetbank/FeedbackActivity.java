package com.afq.streetbank;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afq.streetbank.Adapter.FeedbackRecyclerViewAdapter;
import com.afq.streetbank.Model.Feedback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private RecyclerView rvFeedback;
    private FloatingActionButton fabFeedback;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");
    FirebaseUser user;


    private FeedbackRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Feedback> mUploads = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();

        setTitle(R.string.feedback);
        setSupportActionBar(myToolbar);

        myToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        ShowData();
        BuildRecycler();

        fabFeedback.setOnClickListener(view ->
                BuildDialog());


    }


    private void initView() {
        myToolbar = findViewById(R.id.my_toolbar);
        rvFeedback = findViewById(R.id.rvFeedback);
        fabFeedback = findViewById(R.id.fabFeedback);
        user = firebaseAuth.getCurrentUser();
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FeedbackRecyclerViewAdapter(mUploads, FeedbackActivity.this);

    }

    public void BuildRecycler() {
        rvFeedback.setHasFixedSize(true);

        rvFeedback.setLayoutManager(mLayoutManager);
        rvFeedback.setAdapter(mAdapter);

    }

    public void ShowData() {

        myRef.child("Feedback").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Feedback upload = dataSnapshot.getValue(Feedback.class);

                    mUploads.add(upload);
                    mAdapter.notifyDataSetChanged();

                }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FeedbackActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void BuildDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_feedback, null);

        final EditText editText = dialogView.findViewById(R.id.edtFeedback);
        Button button1 = dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(view -> dialogBuilder.dismiss());
        button1.setOnClickListener(view -> {

            Feedback feedback = new Feedback(user.getDisplayName(),editText.getText().toString());

            myRef.child("Feedback").push().setValue(feedback).addOnSuccessListener(aVoid -> {

                Log.i("AFQ", "1");

            });


            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

}