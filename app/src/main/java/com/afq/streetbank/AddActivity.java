package com.afq.streetbank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.afq.streetbank.Model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

        setTitle(R.string.add_item);
        setSupportActionBar(myToolbar);

        myToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));


        btnAdd.setOnClickListener(view -> AddItem());

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
        String name = edtName.getEditText().getText().toString();
        String desc = edtDesc.getEditText().getText().toString();
        String price = edtPrice.getEditText().getText().toString();
        String UID = user.getUid();
        String userName = user.getDisplayName();

        Item i = new Item();
        i.setUID(UID);
        i.setUserName(userName);
        i.setDesc(desc);
        i.setItem(name);
        i.setPrice(Double.parseDouble(price));

        if(cbRent.isChecked()){
            i.setRent(true);
        }

        if(cbBorrow.isChecked()){
            i.setBorrow(true);
        }

        if(cbSell.isChecked()){
            i.setSell(true);
        }


        myRef.child("Items").push().setValue(i).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}