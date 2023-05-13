package com.romarioburke.amberheartfoodapp.Dataclasses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Register {
    private final String StudentID;
    private final String Name;
    private final String Email;
    private final String Password;
    private final Activity Current_Activity;
    private boolean Choice;

    public Register(String studentid, String name, String email, String password, Activity activity) {
        this.StudentID = studentid;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Current_Activity = activity;
    }
    public boolean IsRegistered() {
        String url = "https://api.romarioburke.com/api/v1/auth/register";
        RequestQueue queue = Volley.newRequestQueue(Current_Activity);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Message = obj.optString("response");
                    String Errors = obj.getString("errors");
                    if (Message.equals("Success")) {

                        Choice = true;
                    } else if (!Errors.equals("")) {
                       Choice = false;
                        Toast.makeText(Current_Activity.getApplicationContext(), Errors, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Choice = false;

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Current_Activity.getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name", Name);
                params.put("StudentID", StudentID);
                params.put("Email", Email);
                params.put("Password", Password);
                params.put("Status", "Student");
                return params;
            }
        };
        queue.add(request);
        return Choice;
    }
}
