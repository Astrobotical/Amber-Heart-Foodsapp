package com.romarioburke.amberheartfoodapp.Dataclasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.MainActivity;
import com.romarioburke.amberheartfoodapp.login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login {
    String StudentID;
    String Password;
    Activity Current_Activity;
    boolean isLoggedin;
    public Login(String studentid, String password, Activity CalledActivity)
    {
        this.StudentID = studentid;
        this.Password = password;
        this.Current_Activity = CalledActivity;
    }
    public Boolean UserLogin()
    {
        String url = "https://api.romarioburke.com/api/v1/auth/login";
        RequestQueue queue = Volley.newRequestQueue(Current_Activity);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Message = obj.optString("response");
                    String Name = obj.optString("Name");
                    String ID = obj.optString("StudentID");
                    String Status = obj.optString("Status");
                   // String Errors = obj.getString("errors");
                    if (Message.equals("Success")) {
                       setLoggedin(true);
                        SharedPreferences logs = Current_Activity.getSharedPreferences("Auth", Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = logs.edit();
                        myEdit.putString("ID",ID);
                        myEdit.putString("type",Status);
                        myEdit.putString("Name",Name);
                        myEdit.apply();

                    } else if (Message.equals("Error")) {
                        setLoggedin(false);
                        Toast.makeText(Current_Activity.getApplicationContext(), "Invalid Password or StudentID", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    setLoggedin(false);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Current_Activity.getApplicationContext(),  error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("StudentID", StudentID);
                params.put("Password", Password);
                return params;
            }
        };
        queue.add(request);
        return isLoggedin();
    }
    public boolean isLoggedin() {
        return isLoggedin;
    }

    public void setLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }

}
