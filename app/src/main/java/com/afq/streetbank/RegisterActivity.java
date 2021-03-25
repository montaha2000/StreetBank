package com.afq.streetbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afq.streetbank.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private TextView textView;
    private TextInputLayout edtPassword;
    private TextInputLayout edtFullName;
    private TextInputLayout edtPhoneNumber;
    private TextInputLayout edtEmail;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("StreetBank");
    FirebaseUser user;

    String name;
    String email;
    String password;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        textView.setTextColor(getResources().getColor(R.color.purple_200));

        btnRegister.setOnClickListener(view -> {
            RegisterUser();
        });

    }

    private void initView() {
        btnRegister = findViewById(R.id.btnRegister);
        textView = findViewById(R.id.textView);
        edtPassword = findViewById(R.id.edtPassword);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
    }

    private void RegisterUser() {
        name = Objects.requireNonNull(edtFullName.getEditText()).getText().toString().trim();
        email = Objects.requireNonNull(edtEmail.getEditText()).getText().toString().trim();
        password = Objects.requireNonNull(edtPassword.getEditText()).getText().toString().trim();
        phone = Objects.requireNonNull(edtPhoneNumber.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please enter a valid email");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Minimum length of password is be 6");
            edtPassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            edtFullName.setError("Name is required");
            edtFullName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            edtPhoneNumber.setError("Phone is required");
            edtPhoneNumber.requestFocus();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        user = firebaseAuth.getCurrentUser();

                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            if (user != null) {

                                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("AFQ", "1");
                                        }
                                    }
                                });

                                User u = new User();
                                u.setName(name);
                                u.setEmail(user.getEmail());
                                u.setPhone(phone);

                                myRef.child("Users").child(user.getUid()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("AFQ", "1");
                                    }
                                });
                            }


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


}