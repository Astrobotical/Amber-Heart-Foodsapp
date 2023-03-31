package com.romarioburke.amberheartfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        EditText Password = findViewById(R.id.Password);
        EditText StudentID = findViewById(R.id.SID);
        Button Login = findViewById(R.id.Login12);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StudentID.getText().toString().equals("")|| Password.getText().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Data obj = new Data(login.this);
                    PreparedStatement statement = null;
                    try {
                        statement = obj.ConnectionString.prepareStatement("Select StudentID, Password FROM users WHERE StudentID = ? AND Password = ?");
                        statement.setString(1, StudentID.getText().toString());
                        statement.setString(2, Password.getText().toString());
                        Log.i("Queryresult", "Up to here");
                        ResultSet result = statement.executeQuery();
                        if(result.next())
                        {
                            Log.i("Queryresult", "Logged in");
                            Intent activity = new Intent(login.this,MainActivity.class);
                            startActivity(activity);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

    }
}