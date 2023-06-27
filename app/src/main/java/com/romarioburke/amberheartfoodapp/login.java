package com.romarioburke.amberheartfoodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.romarioburke.amberheartfoodapp.Authenticator.ResetOptions;
import com.romarioburke.amberheartfoodapp.Database.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    boolean checked;
     private String StudentIDv, Passwordv = "";
     ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button redirect = findViewById(R.id.redirectrl);
        Button signin = findViewById(R.id.signin);
        TextView ForgetPassword = findViewById(R.id.lForgotPassword);
        TextInputEditText StudentID = findViewById(R.id.ActivityStudentID);
        TextInputEditText Password = findViewById(R.id.ActivityPassword);
        CheckBox rememberme = findViewById(R.id.Arememberme);
        progressBar = findViewById(R.id.PB);
        SharedPreferences logs = getSharedPreferences("rememberme", Context.MODE_PRIVATE);
        String IsSET = logs.getString("Stored_StudentID", "");
        int loadtime = 0;
        if (IsSET.isEmpty() && loadtime <= 0) {
            StudentID.setText("");
        } else {
            rememberme.setChecked(true);
            StudentID.setText(IsSET);
        }
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Your student ID will be stored", Toast.LENGTH_SHORT).show();
                    checked = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Your student ID will not be saved", Toast.LENGTH_SHORT).show();
                    checked = false;
                }
            }
        });
        ForgetPassword.setOnClickListener((view) -> {
            Intent switchactivity = new Intent(getApplicationContext(), ResetOptions.class);
            startActivity(switchactivity);
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StudentIDv = StudentID.getText().toString();
            Passwordv = Password.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            Password.setVisibility(View.GONE);
            StudentID.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    boolean Choice = true;
                    if (StudentID.equals("")) {
                        StudentID.setError("Please enter your StudentID");
                        Choice = false;
                    }
                    if (Password.equals("")) {
                        Password.setError("Please enter your Password");
                        Choice = false;
                    }
                    if (Choice) {
                        String url = "https://api.romarioburke.com/api/v1/auth/login";
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (checked) {
                                        SharedPreferences logs = getSharedPreferences("rememberme", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = logs.edit();
                                        myEdit.putString("Stored_StudentID", StudentID.getText().toString());
                                        myEdit.apply();
                                    } else {
                                        SharedPreferences logs =getSharedPreferences("rememberme", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = logs.edit();
                                        myEdit.putString("Stored_StudentID", "");
                                        myEdit.apply();
                                    }
                                    JSONObject obj = new JSONObject(response);
                                    String Message = obj.optString("response");
                                    String Name = obj.optString("Name");
                                    String ID = obj.optString("StudentID");
                                    String Status = obj.optString("Status");
                                    String convert = obj.optString("Highestrating");
                                    JSONObject high = new JSONObject(convert);
                                    String Menu = high.optString("Menu");
                                    JSONObject Menuobj = new JSONObject(Menu);
                                    String Item_name = Menuobj.getString("Item_name");
                                    String Item_img = Menuobj.getString("Item_image");
                                    float Item_rating = 4.5f;
                                    if (Message.equals("Success")) {
                                        SharedPreferences logs = getSharedPreferences("Auth", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = logs.edit();
                                        myEdit.putString("ID", ID);
                                        myEdit.putString("type", Status);
                                        myEdit.putString("Name", Name);
                                        myEdit.apply();
                                        Toast.makeText(getApplicationContext(), "Logged in successful", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        if (Status.equals("Student")) {
                                            Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                                            String prebroken = Name;
                                            String[] total_String = prebroken.split("\\s");
                                            String Usernamebuilder = total_String[0] + " " + total_String[1].substring(0, 1).toUpperCase() + ".";
                                            SharedPreferences preferences = getSharedPreferences("Featured", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("Username", Usernamebuilder);
                                            editor.putString("StudentID", ID);
                                            editor.putString("Item_name", Item_name);
                                            editor.putString("Item_img", Item_img);
                                            editor.putFloat("Item_rating", Item_rating);
                                            editor.apply();
                                            activity.putExtra("Username", Usernamebuilder);
                                            activity.putExtra("StudentID", ID);
                                            activity.putExtra("Item_name", Item_name);
                                            activity.putExtra("Item_img", Item_img);
                                            activity.putExtra("Item_rating", Item_rating);
                                            startActivity(activity);
                                            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
                                        } else {
                                            Log.i("Queryresult", "Logged in");
                                            Intent activity = new Intent(getApplicationContext(), cooks_main.class);
                                            startActivity(activity);
                                            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
                                        }
                                    } else if (Message.equals("Error") || Message.equals("Error1")) {


                                    }
                                } catch (JSONException e) {
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Password.setVisibility(View.VISIBLE);
                                StudentID.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Invalid Password or StudentID", Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("StudentID", StudentIDv);
                                params.put("Password", Passwordv);
                                return params;
                            }
                        };
                        queue.add(request);
                    }
                }
            });
            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Overview.class));
                overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
            }
        });
    }
}