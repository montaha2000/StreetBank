package com.afq.streetbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtRegister;
    private TextView textView;
    private TextInputLayout edtPassword;
    private TextInputLayout edtEmail;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
        textView.setTextColor(getResources().getColor(R.color.purple_200));

        txtRegister.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener(view -> {
            LoginUser();
        });

    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        textView = findViewById(R.id.textView);
        txtRegister = findViewById(R.id.txtRegister);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);

        mAuth = FirebaseAuth.getInstance();
    }

    private void LoginUser() {
        String email = edtEmail.getEditText().getText().toString().trim();
        String password = edtPassword.getEditText().getText().toString().trim();


        if (password.isEmpty()) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Minimum length of password should be 6");
            edtPassword.requestFocus();
            return;
        }


        if (email.isEmpty()) {
            edtEmail.setError("email is required");
            edtEmail.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }



}