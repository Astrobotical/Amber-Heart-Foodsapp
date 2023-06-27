package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.romarioburke.amberheartfoodapp.Authenticator.Auth;
import com.romarioburke.amberheartfoodapp.Database.Data;
import com.romarioburke.amberheartfoodapp.Dataclasses.Login;
import com.romarioburke.amberheartfoodapp.Dataclasses.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private boolean iserror = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Button redirect = findViewById(R.id.redirector);


        redirect.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Overview.class));
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });

        Button Onregister = findViewById(R.id.ActivityRegister);
        EditText Name = findViewById(R.id.rUsername);
        EditText Email = findViewById(R.id.rEmail);
        EditText StudentID = findViewById(R.id.rSTUDID);
        EditText Confirmed_Password = findViewById(R.id.RConfirmPassword);
        TextInputLayout passwordlayout = findViewById(R.id.rPasswordlayout);
        TextInputEditText passwordtext = findViewById(R.id.RPassword);
        ProgressBar progressBar = findViewById(R.id.rebar);
        Data obj = new Data(this);
       SavedData Actioncallor = new ViewModelProvider(this).get(SavedData.class);
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (Name.getText().toString().equals("")) {
                    Name.setError("Please enter your name");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Name.getText().toString().contains(" ")) {
                    Name.setError("Please enter your first and last name and separate it with a space");
                    Actioncallor.setError();
                } else {
                   Actioncallor.setErrorsuccess();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        StudentID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    PreparedStatement Statement = obj.ConnectionString.prepareStatement("Select StudentID from students WHERE StudentID = ?");
                    String ID = StudentID.getText().toString();
                    Statement.setString(1, ID);
                    ResultSet Result = Statement.executeQuery();
                    if (Result.next()) {
                        StudentID.setError("This Student ID already exists");
                        Actioncallor.setError();
                    } else {
                        Actioncallor.setErrorsuccess();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern Pat = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                Matcher Match = Pat.matcher(Email.getText().toString());
                if (Match.matches()) {
                    try {
                        PreparedStatement Statement = obj.ConnectionString.prepareStatement("Select Email from students WHERE Email = ?");
                        Statement.setString(1, Email.getText().toString());
                        ResultSet Result = Statement.executeQuery();
                        if (Result.next()) {
                            Email.setError("This Email already exists");
                            Actioncallor.setError();
                        } else {
                            Actioncallor.setErrorsuccess();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Email.setError("This is not an valid email address");
                    Actioncallor.setError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordlayout.requestFocus();
            }
        });
        passwordtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (passwordtext.getText().toString().equals("")) {
                    passwordtext.setError("Ensure that it has at least 6 or more letters with 2 numbers");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numbers = 0;
                if (passwordtext.getText().toString().length() >= 6) {
                    char element;
                    for (int index = 0; index < passwordtext.getText().toString().length(); index++) {
                        element = passwordtext.getText().toString().charAt(index);
                        if (Character.isDigit(element)) {
                            numbers++;
                        }
                    }
                    if (numbers < 2) {
                        passwordtext.setError("Invalid, ensure the password has 2 numbers in it");
                        Actioncallor.setError();
                    } else if (!passwordtext.getText().toString().matches("[a-zA-Z0-9]+")) {
                        passwordtext.setError("Invalid, ensure the password has only numbers and letters");
                        Actioncallor.setError();
                    }
                } else {
                    passwordtext.setError("Invalid, ensure the password has at least 6 or more letters with 2 numbers");
                    Actioncallor.setError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Onregister.setOnClickListener((view) -> {
            TextInputEditText got = findViewById(R.id.rEmail);
            Toast.makeText(getApplicationContext(), "Please wait", Toast.LENGTH_LONG).show();
            Actioncallor.AreErrors().observe(this, Error -> {
                this.iserror = Error;
            });
          //  com.romarioburke.amberheartfoodapp.Dataclasses.Register regis = new Register(StudentID.getText().toString(), Name.getText().toString(), got, passwordtext.getText().toString(), this);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (iserror == true) {
                        Toast.makeText(getApplicationContext(), "Here", Toast.LENGTH_LONG).show();
                        Log.i("register","here");
                    }
                        String url = "https://api.romarioburke.com/api/v1/auth/register";
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    String Message = obj.optString("response");
                                    if (Message.equals("Success")) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Successful registration", Toast.LENGTH_LONG).show();
                                        Intent Loginscreen = new Intent(getApplicationContext(), Login.class);
                                        startActivity(Loginscreen);
                                        overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
                                    } else if (Message.equals("Email already exists")) {
                                    }
                                } catch (JSONException e) {
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                String[] splited = Name.getText().toString().split("\\s");
                                String First = Character.toUpperCase(splited[0].charAt(0)) + splited[0].substring(1);
                                String Lastname = Character.toUpperCase(splited[1].charAt(0)) + splited[1].substring(1);
                                ;
                                String NewName = First + " " + Lastname;
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Name", NewName);
                                params.put("StudentID", StudentID.getText().toString());
                                params.put("Email", got.getText().toString());
                                params.put("Password", passwordtext.getText().toString());
                                params.put("Status", "Student");
                                Log.i("spenting", String.valueOf(params));
                                return params;
                            }
                        };
                        queue.add(request);
                    }
                });
            });
        }
}