package com.romarioburke.amberheartfoodapp.Authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.romarioburke.amberheartfoodapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class reset_Email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_byemail);
        Button VerifyandsendToken = findViewById(R.id.validatetoken);
        TextInputEditText Email = findViewById(R.id.Email);
        ImageView Backbtn = findViewById(R.id.backbutton);
        Backbtn.setOnClickListener((view)->{
            Intent Tonextactivity = new Intent(getApplicationContext(), ForgetpasswordChooser.class);
            startActivity(Tonextactivity);
        });
        VerifyandsendToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Email.getText().toString().equals("")) {
                    postData(Email.getText().toString());
                    setContentView(R.layout.activity_reset_options);
                    TextInputEditText Tokeninput = findViewById(R.id.Email);
                    Button Validate = findViewById(R.id.validatetoken);
                    ImageView returnhome = findViewById(R.id.backbutton);
                    Validate.setOnClickListener((view)->{
                        if(!Tokeninput.getText().toString().equals("")) {
                            ValidateToken(Tokeninput.getText().toString());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Please enter something", Toast.LENGTH_LONG).show();
                        }
                    });

                    returnhome.setOnClickListener((view)->{
                        Intent Tonextactivity = new Intent(getApplicationContext(), ForgetpasswordChooser.class);
                        startActivity(Tonextactivity);
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Enter an Email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void ValidateToken(String Token) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.romarioburke.com/api/v1/verify/validatetoken";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String Message = obj.optString("message");
                            if(Message.equals("Token was validated successfully!")) {
                                Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
                                Intent Tonextactivity = new Intent(getApplicationContext(), Auth.class);
                                startActivity(Tonextactivity);
                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("token", Token);
                        return params;
                    }
                };
                queue.add(request);
            }
        });
    }
    private void postData(String Email) {
         runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.romarioburke.com/api/v1/verify/verifyEmail";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String Message = obj.optString("message");
                            Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }).start();
                        } catch (JSONException e) {

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", Email);
                        return params;
                    }
                };
                queue.add(request);
            }
        });
    }
}