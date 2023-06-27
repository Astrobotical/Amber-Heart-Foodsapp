package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Button Login = findViewById(R.id.OverviewLoginbtn);
        Button Register = findViewById(R.id.OverviewSignupbtn);
        Login.setOnClickListener(v -> {
           startActivity(new Intent(getApplicationContext(), login.class));
           overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
        Register.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), register.class));
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
    }
}