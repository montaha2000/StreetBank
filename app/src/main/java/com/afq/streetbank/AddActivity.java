package com.afq.streetbank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.afq.streetbank.Model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class AddActivity extends AppCompatActivity {

    private Button btnAdd;
    private ImageView imgItem;
    private TextInputLayout edtPrice;
    private TextInputLayout edtDesc;
    private TextInputLayout edtName;
    private Toolbar myToolbar;
    private CheckBox cbBorrow;
    private CheckBox cbSell;
    private CheckBox cbRent;
    private Uri imageUri;

    String contactn ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

        setTitle(R.string.add_item);
        setSupportActionBar(myToolbar);

        myToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        btnAdd.setOnClickListener(view -> {
            AddItem();
            finish();
        });

        imgItem.setOnClickListener(view -> imagePicker());

    }

    private void imagePicker() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgItem.setImageURI(imageUri);
        }
    }

    private void imageUpload(String key) {

        StorageReference imgRef = storageRef.child("images/" + key);

        if (imageUri != null) {
            imgRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> Toast.makeText(AddActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(exception -> {
                        // Handle unsuccessful uploads
                        // ...
                    });
        } else
            Toast.makeText(this, "Upload an image and enter all fields", Toast.LENGTH_LONG).show();
    }

    private void initView() {
        btnAdd = findViewById(R.id.btnAdd);
        imgItem = findViewById(R.id.imgItem);
        edtPrice = findViewById(R.id.edtPrice);
        edtDesc = findViewById(R.id.edtDesc);
        edtName = findViewById(R.id.edtName);
        myToolbar = findViewById(R.id.my_toolbar);
        cbBorrow = findViewById(R.id.cbBorrow);
        cbSell = findViewById(R.id.cbSell);
        cbRent = findViewById(R.id.cbRent);
    }

    public void AddItem() {
        String name = null;
        String desc = null;
        String price = null;
        String userName = null;

        if (edtDesc.equals("") || edtPrice.equals("")) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        } else {
            name = edtName.getEditText().getText().toString();
            desc = edtDesc.getEditText().getText().toString();
            price = edtPrice.getEditText().getText().toString();
            userName = user.getDisplayName();
        }



        String itemId = myRef.push().getKey();
        Item i = new Item();

        i.setkey(itemId);
        i.setUserName(userName);
        i.setDesc(desc);
        i.setItem(name);
        i.setPrice(Integer.parseInt(price));
        imageUpload(itemId);

        myRef.child("Users").child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.getKey().equals("phone")) {
                    contactn = snapshot.getValue().toString().trim();
                    i.setContact(contactn);

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

        i.setContact(contactn);

        if (cbRent.isChecked()) {
            i.setRent(true);

        }

        if (cbBorrow.isChecked()) {
            i.setDonate(true);
        }

        if (cbSell.isChecked()) {
            i.setSell(true);
        }


        myRef.child("Items").child(itemId).setValue(i).addOnSuccessListener(aVoid ->
                Toast.makeText(AddActivity.this, "Item Added", Toast.LENGTH_SHORT).show());

    }

}