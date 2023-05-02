package com.romarioburke.amberheartfoodapp.Database;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.romarioburke.amberheartfoodapp.BuildConfig;

import java.sql.*;
public class Data {
 Context Contextural;
 public Connection ConnectionString = null;

 public Data(Context context) {
  this.ConnectionString = Connectionstring();
  this.Contextural = context;
 }

 private Connection Connectionstring() {
  Connection conenctionstring = null;
  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
  StrictMode.setThreadPolicy(policy);
  try {
   String DatabaseUsername = BuildConfig.DBuser;
   String DatabasePassword = BuildConfig.DBPASS;
   String URL = "jdbc:mariadb://sql652.main-hosting.eu:3306/" + BuildConfig.DBname;
   conenctionstring = DriverManager.getConnection(URL, DatabaseUsername, DatabasePassword);
   Log.d("Database_status", "Connection made");
  } catch (Exception ex) {
   Log.d("Database_status", "Connection status failed");
  }
  return conenctionstring;
 }

 public ResultSet runQuery(String query) {
  ResultSet result = null;
  try {
   Statement statement = ConnectionString.createStatement();
   result = statement.executeQuery(query);
   while (result.next()) {
    Log.i("Queryran", "Query was ran successfully");
   }
  } catch (SQLException ex) {
   Log.i("Queryran", String.valueOf(ex));
  }
  return result;
 }

 public void Datastore(String keyvalue, String Value) {
  SharedPreferences sharedPreferences = Contextural.getSharedPreferences("loaded", 0);
  SharedPreferences.Editor editor = sharedPreferences.edit();
  editor.putString(keyvalue, Value);
  editor.apply();
 }

 public String Datarelease(String key) {
  SharedPreferences sharedPreferences = Contextural.getSharedPreferences("loaded", 0);
  if (sharedPreferences.contains(key)) {
   return sharedPreferences.getString(key, null);
  } else {
   return null;
  }
 }
}