package com.romarioburke.amberheartfoodapp.Dataclasses;

import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register {
    private final String StudentID;
    private final String Name;
    private  String Email;
    private final String Password;
    private final Activity Current_Activity;
    private boolean Choice;

    public Register(String studentid, String name, TextInputEditText email, String password, Activity activity) {
        this.StudentID = studentid;
        this.Name = name;
        this.Email = email.getText().toString();
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
                Toast.makeText(Current_Activity.getApplicationContext(),  error.toString(), Toast.LENGTH_SHORT).show();
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
                Log.i("spenting", String.valueOf(params));
                return params;
            }
        };
        queue.add(request);
        return Choice;
    }
}
