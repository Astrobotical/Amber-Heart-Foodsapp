package com.romarioburke.amberheartfoodapp.Authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.login;

public class ResetOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgetpassword_chooser);

    }
    @Override
    public void onStart()
    {
        super.onStart();
        ImageView BackButton = findViewById(R.id.BackButton);
        Button resetEmail = findViewById(R.id.ResetByEmail);
        Button resetStudentID = findViewById(R.id.ResetByStudentID);
        BackButton.setOnClickListener((view)->{
            Intent switchbackactivity = new Intent(this, login.class);
            startActivity(switchbackactivity);
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
        resetEmail.setOnClickListener((view)->{
            Intent switchbtoreset = new Intent(this, reset_Email.class);
            startActivity(switchbtoreset);
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
        resetStudentID.setOnClickListener((view)->
        {
            Intent switchbtostudentid = new Intent(this, reset_bystudentid.class);
            startActivity(switchbtostudentid);
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
    }
}