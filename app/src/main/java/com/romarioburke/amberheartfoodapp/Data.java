package com.romarioburke.amberheartfoodapp;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import java.sql.*;
class Data {
 private Connection ConnectionString = null;
 public Data() {
  this.ConnectionString = Connectionstring();
 }
 private Connection Connectionstring()
 {
  Connection conenctionstring = null;
  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
  StrictMode.setThreadPolicy(policy);
  try{
   String DatabaseUsername = BuildConfig.DBuser;
   String DatabasePassword = BuildConfig.DBPASS;
   String URL = "jdbc:mariadb://sql652.main-hosting.eu:3306/"+BuildConfig.DBname;
   conenctionstring = DriverManager.getConnection(URL,DatabaseUsername,DatabasePassword);
   Log.d("Database_status", "Connection made");
  }
  catch(Exception ex)
  {
   Log.d("Database_status", "Connection status failed");
  }
  return conenctionstring;
 }
}
