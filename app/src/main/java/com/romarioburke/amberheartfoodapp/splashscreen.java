package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread splashscreenTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent loginscreen = new Intent(getApplicationContext(),Auth.class);
                    startActivity(loginscreen);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        splashscreenTimer.start();
    }
}