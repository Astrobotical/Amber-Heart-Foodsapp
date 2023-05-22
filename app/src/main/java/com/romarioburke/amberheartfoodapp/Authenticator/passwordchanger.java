package com.romarioburke.amberheartfoodapp.Authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
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

public class passwordchanger extends AppCompatActivity {
    SharedPreferences Saved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordchanger);

    }
    @Override
    public void onStart()
    {
        super.onStart();
       Bundle savedInstanceState = getIntent().getExtras();
        SharedPreferences logs =getSharedPreferences("change", Context.MODE_PRIVATE);
        String StudentID = logs.getString("ID", "");
        Log.i("Toroad", StudentID);
        TextInputEditText Password = findViewById(R.id.Password);
        TextInputEditText ConfirmedPassword = findViewById(R.id.ConfirmPassword);
        Button Submit = findViewById(R.id.onsubmit);
        Submit.setOnClickListener((view)->{
            if(Password.getText().toString().equals(""))
            {
                Password.setError("Please enter your password");
            }
            else if(ConfirmedPassword.getText().toString().equals("")|| !ConfirmedPassword.getText().toString().equals(Password.getText().toString()))
            {
                ConfirmedPassword.setError("Please that ensure that youve re-entered the correct password to validate this.");
            }
            else{
                SharedPreferences sharedobj = getPreferences(0);
                ResetPassword(Password.getText().toString(),StudentID);
            }
        });
    }
    private void ResetPassword(String NewPassword, String StudentID) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.romarioburke.com/api/v1/auth/updatepassword";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String Message = obj.optString("message");
                            if(Message.equals("The password was changed  successfully!")) {
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
                        Toast.makeText(getApplicationContext(),  error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("NewPassword", NewPassword);
                        params.put("StudentID",StudentID);
                        return params;
                    }
                };
                queue.add(request);
            }
        });
    }
}