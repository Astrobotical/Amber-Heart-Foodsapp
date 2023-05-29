package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.romarioburke.amberheartfoodapp.Database.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Button Onregister = findViewById(R.id.Register);
        EditText Name = findViewById(R.id.Username);
        EditText Email = findViewById(R.id.Emailr);
        EditText StudentID = findViewById(R.id.STUDID);
        EditText Password = findViewById(R.id.PasswordL);
        EditText Confirmed_Password = findViewById(R.id.ConfirmPassword);
        Onregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checker = false;
                try {
                    if (Name.getText().toString().equals("") || Email.getText().toString().equals("") || StudentID.getText().toString().equals("") || Password.getText().toString().equals("") || Confirmed_Password.getText().toString().equals("")) {
                        Toast.makeText(register.this, "Please ensure all fields were filled!", Toast.LENGTH_SHORT).show();
                    }else if(!Password.getText().toString().equals(Confirmed_Password.getText().toString())){
                        Toast.makeText(getApplicationContext(),"The passwords arent the same", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Data obj = new Data(register.this);
                        PreparedStatement statement = obj.ConnectionString.prepareStatement("Select StudentID, Email FROM users WHERE StudentID = ? OR Email = ?");
                        statement.setString(1, StudentID.getText().toString());
                        statement.setString(2, Email.getText().toString());
                        ResultSet Query_result = statement.executeQuery();
                        //Log.i("Queryresult", "It came up to here");
                        if (Query_result.next()) {
                            String STUID = Query_result.getString("StudentID");
                            String EmailDB = Query_result.getNString("Email");
                            Log.i("Queryresult", "It came up to here 123");
                            //Log.i("result123",StudentID.getText().toString());
                            if (StudentID.getText().toString().equals(STUID)) {
                                Log.i("Queryresult", "Found this student ID");
                                Toast.makeText(register.this, "A student with this StudentID is already registered", Toast.LENGTH_SHORT).show();
                            } else if (Email.getText().toString().equals(EmailDB)) {
                                Log.i("Queryresult", "Found this Email");
                                Toast.makeText(register.this, "This email has already been registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i("Queryresult", "No account was found");
                            }
                            Query_result.close();
                        } else {
                            PreparedStatement statement1 = obj.ConnectionString.prepareStatement("INSERT into users (Name,StudentID,Email,Password,Email_verified,Status)VALUES(?,?,?,?,?,?)");
                            statement1.setString(1, Name.getText().toString());
                            statement1.setString(2, StudentID.getText().toString());
                            statement1.setString(3, Email.getText().toString());
                            statement1.setString(4, Password.getText().toString());
                            statement1.setString(5, "false");
                            statement1.setString(6, "Student");
                           int results = statement1.executeUpdate();
                            if(results > 0) {
                                // boolean y = false;
                                Log.i("Queryresult", "Record has been inserted");
                                Toast.makeText(getApplicationContext(), "You've been registered sucessfully ", Toast.LENGTH_SHORT).show();
                                new CountDownTimer(3000, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                    }
                                    public void onFinish() {
                                        Intent Loginscreen = new Intent(getApplicationContext(), login.class);
                                        startActivity(Loginscreen);
                                    }
                                }.start();
                            }
                        }
                        obj.ConnectionString.close();
                    }
                }catch (Exception ex) {
                    Log.i("Queryresult", ex.toString());
                }
            }
        });
        Button returning = findViewById(R.id.returning);
        returning.setOnClickListener((view)->{
            Intent loginpage = new Intent(getApplicationContext(),login.class);
            startActivity(loginpage);
        });
    }
    @Override
    protected void onStart(){
        super.onStart();

    }
}