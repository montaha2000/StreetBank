package com.afq.streetbank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class DetailsActivity extends AppCompatActivity {

    private Button btnContact;
    private ImageView imgItem;
    private TextView txtName;
    private TextView txtDesc;
    private TextView txtPrice;
    private TextView txtItem;
    private TextView txtContact;
    private Toolbar myToolbar;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();

        setTitle(R.string.details);
        setSupportActionBar(myToolbar);

        myToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        Intent i = getIntent();
        String item = i.getStringExtra("item");
        String desc = i.getStringExtra("desc");
        int price = i.getIntExtra("price", 0);
        String name = i.getStringExtra("name");
        String key = i.getStringExtra("key");

        txtName.setText(name);
        txtItem.setText(item);
        txtDesc.setText(desc);
        txtPrice.setText(String.valueOf(price));
        txtContact.setText("");

        StorageReference imgRef = storageRef.child("images/" + key);

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
            File finalLocalFile = localFile;
            imgRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            Picasso.get().load(finalLocalFile).into(imgItem);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    Log.i("AFQ", exception.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        btnContact.setOnClickListener(view ->

                myRef.child("Items").child(key).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        if (snapshot.getKey().equals("contact")) {

                            String url = "https://api.whatsapp.com/send?phone=" + snapshot.getValue().toString().trim();

                            Intent i1 = new Intent(Intent.ACTION_VIEW);
                            i1.setData(Uri.parse(url));

                            startActivity(i1);
                            Log.i("AFQ", snapshot.getValue().toString());

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
                }));

    }

    private void initView() {
        btnContact = findViewById(R.id.btnContact);
        imgItem = findViewById(R.id.imgItem);
        txtName = findViewById(R.id.txtName);
        txtDesc = findViewById(R.id.txtDesc);
        txtPrice = findViewById(R.id.txtPrice);
        txtItem = findViewById(R.id.txtItem);
        txtContact = findViewById(R.id.txtContact);
        myToolbar = findViewById(R.id.my_toolbar);
    }
}