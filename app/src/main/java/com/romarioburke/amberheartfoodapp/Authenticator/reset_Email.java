package com.romarioburke.amberheartfoodapp.Authenticator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class reset_Email extends AppCompatActivity {
    String SavedID;
    ProgressBar Progress = null;
    TextInputEditText Emaile=null;
    CardView emailcardview = null ;
    Button VerifyandsendToken = null;
    private boolean wassent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_byemail);
        VerifyandsendToken = findViewById(R.id.validatetoken);
        Emaile = findViewById(R.id.tokenid);
        ImageView Backbtn = findViewById(R.id.backbutton);
        TextView gotologin = findViewById(R.id.redirect);
        Progress = findViewById(R.id.emailprogresssbar);
         emailcardview = findViewById(R.id.emailcardview);
        Backbtn.setOnClickListener((view)->{
            Intent Tonextactivity = new Intent(getApplicationContext(), ResetOptions.class);
            startActivity(Tonextactivity);
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
        gotologin.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), login.class));
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
        VerifyandsendToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Emaile.getText().toString().equals("")) {
                    Emaile.setVisibility(View.GONE);
                    emailcardview.setVisibility( View.GONE);
                    VerifyandsendToken.setVisibility(View.GONE);
                    postData(Emaile.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please eronter an Email", Toast.LENGTH_LONG).show();
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
                            String StudentID = obj.optString("StudentID");
                            if(Message.equals("Token was validated successfully!")) {
                                Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
                                Intent Tonextactivity = new Intent(getApplicationContext(), passwordchanger.class);
                                SharedPreferences logs = getSharedPreferences("change", Context.MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = logs.edit();
                                myEdit.putString("ID", StudentID);
                                myEdit.apply();

                                startActivity(Tonextactivity);
                                overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
        Progress.setVisibility(View.VISIBLE);
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
                            Progress.setVisibility(View.GONE);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        Progress.setVisibility(View.GONE);
                                       startActivity(new Intent(getApplicationContext(), tokenauth.class));
                                        overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
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
                        Progress.setVisibility(View.GONE);
                        Emaile.setVisibility(View.VISIBLE);
                        emailcardview.setVisibility(View.VISIBLE);
                        VerifyandsendToken.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        SavedID = Email;
                        params.put("email", Email);
                        return params;
                    }
                };
                queue.add(request);
            }
        });
    }
}