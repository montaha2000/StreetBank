package com.afq.streetbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {

    private Button btnContact;
    private ImageView imgItem;
    private TextView txtName;
    private TextView txtDesc;
    private TextView txtPrice;
    private TextView txtItem;
    private TextView txtContact;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();

        Intent i = getIntent();
        String img = i.getStringExtra("img");
        String item = i.getStringExtra("item");
        String desc = i.getStringExtra("desc");
        int price = i.getIntExtra("price",0);
        String name = i.getStringExtra("name");
        String UID = i.getStringExtra("UID");

        txtName.setText(name);
        txtItem.setText(item);
        txtDesc.setText(desc);
        txtPrice.setText(String.valueOf(price));
        txtContact.setText("");

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