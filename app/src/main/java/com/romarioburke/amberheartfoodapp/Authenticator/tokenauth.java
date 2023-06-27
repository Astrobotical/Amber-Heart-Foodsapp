package com.romarioburke.amberheartfoodapp.Authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class tokenauth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tokenauthmain);
        TextInputEditText Tokeninput = findViewById(R.id.atokenid);
        Button Validate = findViewById(R.id.atoken);
        ProgressBar loading = findViewById(R.id.loading);
        Validate.setOnClickListener((view) -> {
                    if (!Tokeninput.getText().toString().equals("")) {
                        loading.setVisibility(view.VISIBLE);
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
                                            if (Message.equals("Token was validated successfully!")) {
                                                Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
                                                Intent Tonextactivity = new Intent(getApplicationContext(), passwordchanger.class);
                                                SharedPreferences logs = getSharedPreferences("change", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor myEdit = logs.edit();
                                                myEdit.putString("ID", StudentID);
                                                myEdit.apply();
                                                loading.setVisibility(view.INVISIBLE);
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
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("token", Tokeninput.getText().toString());
                                        return params;
                                    }
                                };
                                queue.add(request);
                            }
                        });
                    }
                });
        ImageView returnhome = findViewById(R.id.backb);
        returnhome.setOnClickListener((v)->{
            Intent Tonextactivity = new Intent(getApplicationContext(), reset_bystudentid.class);
            startActivity(Tonextactivity);
            overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
        });
    }
}