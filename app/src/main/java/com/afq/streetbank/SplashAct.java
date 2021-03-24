package com.afq.streetbank;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class SplashAct extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        Thread thread = new Thread(() -> {
            try {
                sleep(2000);
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }) ;
        thread.start();
    }
}
