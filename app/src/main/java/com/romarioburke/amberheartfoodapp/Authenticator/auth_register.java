package com.romarioburke.amberheartfoodapp.Authenticator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.romarioburke.amberheartfoodapp.Database.Data;
import com.romarioburke.amberheartfoodapp.R;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class auth_register extends Fragment {
    public auth_register() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_register, container, false);
    }
    private Boolean Inputs(EditText StudentID ,EditText Name,EditText Email, EditText Password,EditText Confirm_Password)
    {
        Data obj = new Data(getContext());
        boolean Response = true;
        //Checking if Name was entered and if the name contains a space which would mean it has 2 words
        if(Name.getText().toString().equals(""))
        {
            Name.setError("Please enter your name");
        }
        else if(!Name.getText().toString().equals(""))
        {
            if(!Name.getText().toString().contains(" "))
            {
                Name.setError("Please enter your first and last name and sperate it with a space");
                Response = false;
                if(!Response){return Response;}
            }
            else{
                Response = true;
            }
        }
        //Checking StudentID if its empty and if that is false then check if its already in the database
        if(StudentID.getText().toString().equals(""))
        {
            StudentID.setError("Please enter your student ID");
            Response = false;

        }
        else if(!StudentID.getText().toString().equals(""))
        {
            try {
                PreparedStatement Statement = obj.ConnectionString.prepareStatement("Select StudentID from students WHERE StudentID = ?");
                String ID = StudentID.getText().toString();
                Statement.setString(1,ID);
                ResultSet Result = Statement.executeQuery();
                if(Result.next()){
                    StudentID.setError("This Student ID already exsists");
                    Response = false;
                }
                else{
                    Response = true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //Checking if Email was entered and if the value is actually an Email
        if(Email.getText().toString().equals(""))
        {
            Email.setError("Please enter your email address");
        }else if(!Email.getText().toString().equals(""))
        {
            Pattern Pat = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher Match = Pat.matcher(Email.getText().toString());
            if(Match.matches())
            {
                try {
                    PreparedStatement Statement = obj.ConnectionString.prepareStatement("Select Email from students WHERE Email = ?");
                    Statement.setString(1,StudentID.getText().toString());
                    ResultSet Result = Statement.executeQuery();
                    if(Result.next()){
                        Email.setError("This Email already exsists");
                        Response = false;
                    }
                    else{
                        Response = true;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                Email.setError("This is not an valid email address");
            }
        }
        //Checking if Password was entered  the password was above is more than 6 characters and has 2 or more digits
        if(Password.getText().toString().equals(""))
        {
            Toast.makeText(getContext(),"Up here",Toast.LENGTH_SHORT).show();
            Password.setError("Ensure that it has atleast 6 or more letters with 2 numbers");
            Response = false;
        }
        else if(!Password.getText().toString().equals(""))
        {
            int numbers = 0;
            if(Password.getText().toString().length() > 6)
            {
                Password.setError("Please enter a password with 6 or more characters");
                Response = false;
            }else if(Password.getText().toString().length() >= 6)
            {
                char element;
                for(int index = 0; index < Password.getText().toString().length(); index++ ){
                    element = Password.getText().toString().charAt( index );
                    if( Character.isDigit(element) ){
                        numbers++;
                    }
                }
                if(numbers < 2)
                {
                    Password.setError("Invalid, ensure the password has 2 numbers in it");
                    Response = false;
                }else if(!Password.getText().toString().matches("[a-zA-Z0-9]+"))
                {
                    Password.setError("Invalid, ensure the password has only numbers and letters");
                    Response = false;
                }else{
                    Response = true;
                }
            }
        }
        //Checking if Confirm Password isnt null and equals to the Password
        if(Confirm_Password.getText().toString().equals(""))
        {
            Confirm_Password.setError("Please rewrite the password");
            Response = false;
        }else if(!Confirm_Password.getText().toString().equals("")&&Confirm_Password.getText().toString().equals(Password.getText().toString()))
        {
            Response = true;
            return Response;
        }
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        boolean checker = false;
        Button Register = getActivity().findViewById(R.id.Register);
        EditText Name = getActivity().findViewById(R.id.Username);
        EditText StudentID = getActivity().findViewById(R.id.STUDID);
        EditText Email = getActivity().findViewById(R.id.Email);
        EditText Password = (EditText) getActivity().findViewById(R.id.PasswordL);
        EditText Confirmed_Password = (EditText)  getActivity().findViewById(R.id.ConfirmPassword);
        ProgressBar bar = getActivity().findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);
        Register.setOnClickListener((view) -> {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Inputs(StudentID, Name, Email, Password, Confirmed_Password)) {
                        Data obj = new Data(getContext());
                        try {
                            PreparedStatement statement = obj.ConnectionString.prepareStatement("INSERT into students (Name,StudentID,Email,Password,Email_verified,Status,Account_verified)VALUES(?,?,?,?,?,?,?)");
                            statement.setString(1, Name.getText().toString());
                            statement.setString(2, StudentID.getText().toString());
                            statement.setString(3, Email.getText().toString());
                            statement.setString(4, Password.getText().toString());
                            statement.setInt(5, 0);
                            statement.setString(6, "Student");
                            statement.setInt(7, 0);
                                int results = statement.executeUpdate();
                                if (results > 0) {
                                    // boolean y = false;
                                    Log.i("Queryresult", "Record has been inserted");
                                    //Toast.makeText(getApplicationContext(), "You've been registered sucessfully ", Toast.LENGTH_SHORT).show();
                                    new CountDownTimer(3000, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                            bar.setVisibility(View.VISIBLE);
                                        }

                                        public void onFinish() {
                                            bar.setVisibility(View.GONE);
                                            Intent Loginscreen = new Intent(getContext(), Auth.class);
                                            startActivity(Loginscreen);
                                        }
                                    }.start();
                                    obj.ConnectionString.close();
                                }
                            } catch (SQLException e) {
                           Log.i("Queryresult","Error at registeration");
                        }
                    }
                }
            });
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Button Register = getActivity().findViewById(R.id.Register);
        EditText Name = getActivity().findViewById(R.id.Username);
        EditText StudentID = getActivity().findViewById(R.id.STUDID);
        EditText Email = getActivity().findViewById(R.id.Email);
        EditText Password = getActivity().findViewById(R.id.PasswordL);
        EditText Confirmed_Password = getActivity().findViewById(R.id.ConfirmPassword);
        Name.setText("");
        StudentID.setText("");
        Email.setText("");
        Password.setText("");
        Confirmed_Password.setText("");
    }
}