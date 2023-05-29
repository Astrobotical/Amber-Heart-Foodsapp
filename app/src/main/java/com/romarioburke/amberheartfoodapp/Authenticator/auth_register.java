package com.romarioburke.amberheartfoodapp.Authenticator;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.romarioburke.amberheartfoodapp.Database.Data;
import com.romarioburke.amberheartfoodapp.Dataclasses.Register;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.SavedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class auth_register extends Fragment {
    private boolean Response;
    SavedData Actioncallor;
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
    private Boolean Inputs(EditText StudentID ,EditText Name,TextInputEditText Email, TextInputEditText Password,EditText Confirm_Password)
    {
        Data obj = new Data(getContext());
        boolean Response;
        //Checking if Name was entered and if the name contains a space which would mean it has 2 words
        if(Name.getText().toString().equals(""))
        {
            Name.setError("Please enter your name");
        }
        else if(!Name.getText().toString().equals(""))
        {
            if(!Name.getText().toString().contains(" "))
            {
                Name.setError("Please enter your first and last name and separate it with a space");
                Response = false;
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
                    StudentID.setError("This Student ID already exists");
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
                        Email.setError("This Email already exists");
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
            Password.setError("Ensure that it has at least 6 or more letters with 2 numbers");
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
        //Checking if Confirm Password isn't null and equals to the Password
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
        boolean Response = false;

        ConstraintLayout Container = getActivity().findViewById(R.id.Container);
        //Container.setBackgroundColor(Color.GRAY);
        Button Register = getActivity().findViewById(R.id.Register);
        EditText Name = getActivity().findViewById(R.id.Username);
        EditText StudentID = getActivity().findViewById(R.id.STUDID);
        TextInputEditText Email = getActivity().findViewById(R.id.Emailrr);
        TextInputLayout passwordlayout = getActivity().findViewById(R.id.Textinputlayout);
        TextInputEditText passwordtext = getActivity().findViewById(R.id.Password);
        EditText Confirmed_Password = (EditText) getActivity().findViewById(R.id.ConfirmPassword);
        ProgressBar bar = getActivity().findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);
        Data obj = new Data(getContext());
        Confirmed_Password.requestFocus();
        Actioncallor = new ViewModelProvider(this).get(SavedData.class);
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(Name.getText().toString().equals(""))
                {
                    Name.setError("Please enter your name");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Name.getText().toString().contains(" "))
                {
                    Name.setError("Please enter your first and last name and separate it with a space");
                    Actioncallor.setError();
                }
                else{
                    Actioncallor.setErrorsuccess();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        StudentID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    PreparedStatement Statement = obj.ConnectionString.prepareStatement("Select StudentID from students WHERE StudentID = ?");
                    String ID = StudentID.getText().toString();
                    Statement.setString(1,ID);
                    ResultSet Result = Statement.executeQuery();
                    if(Result.next()){
                        StudentID.setError("This Student ID already exists");
                        Actioncallor.setError();
                    }
                    else{
                        Actioncallor.setErrorsuccess();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern Pat = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                Matcher Match = Pat.matcher(Email.getText().toString());
                if(Match.matches())
                {
                    try {
                        PreparedStatement Statement = obj.ConnectionString.prepareStatement("Select Email from students WHERE Email = ?");
                        Statement.setString(1,Email.getText().toString());
                        ResultSet Result = Statement.executeQuery();
                        if(Result.next()){
                            Email.setError("This Email already exists");
                            Actioncallor.setError();
                        }
                        else{
                            Actioncallor.setErrorsuccess();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Email.setError("This is not an valid email address");
                    Actioncallor.setError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordlayout.requestFocus();
            }
        });
        passwordtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(passwordtext.getText().toString().equals(""))
                {
                    passwordtext.setError("Ensure that it has at least 6 or more letters with 2 numbers");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numbers = 0;
                if (passwordtext.getText().toString().length() >= 6) {
                    char element;
                    for (int index = 0; index < passwordtext.getText().toString().length(); index++) {
                        element = passwordtext.getText().toString().charAt(index);
                        if (Character.isDigit(element)) {
                            numbers++;
                        }
                    }
                    if (numbers < 2) {
                        passwordtext.setError("Invalid, ensure the password has 2 numbers in it");
                        Actioncallor.setError();
                    } else if (!passwordtext.getText().toString().matches("[a-zA-Z0-9]+")) {
                        passwordtext.setError("Invalid, ensure the password has only numbers and letters");
                        Actioncallor.setError();
                    }
                } else{
                    Actioncallor.setErrorsuccess();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Register.setOnClickListener((view) -> {
            TextInputEditText got = getActivity().findViewById(R.id.Emailrr);
            com.romarioburke.amberheartfoodapp.Dataclasses.Register regis = new Register(StudentID.getText().toString(), Name.getText().toString(), got, passwordtext.getText().toString(), getActivity());
            Actioncallor.AreErrors().observe(this, Error -> {
                if (Error.equals(true)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String url = "https://api.romarioburke.com/api/v1/auth/register";
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        String Message = obj.optString("response");
                                        String Errors = obj.getString("errors");
                                        if (Message.equals("Success")) {

                                            bar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), "Successful registration", Toast.LENGTH_LONG).show();
                                            Intent Loginscreen = new Intent(getActivity(), Auth.class);
                                            startActivity(Loginscreen);

                                        } else if (Message.equals("Email already exists")) {

                                        } else if (!Errors.equals("")) {
                                            Toast.makeText(getActivity().getApplicationContext(), Errors, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    String[] splited = Name.getText().toString().split("\\s");
                                    String First = Character.toUpperCase(splited[0].charAt(0)) + splited[0].substring(1);
                                    String Lastname = Character.toUpperCase(splited[1].charAt(0)) + splited[1].substring(1);
                                    ;
                                    String NewName = First + " " + Lastname;
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Name", NewName);
                                    params.put("StudentID", StudentID.getText().toString());
                                    params.put("Email", got.getText().toString());
                                    params.put("Password", passwordtext.getText().toString());
                                    params.put("Status", "Student");
                                    Log.i("spenting", String.valueOf(params));
                                    return params;
                                }
                            };
                            queue.add(request);
                        }
                    });
                }
            });
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        EditText Name = getActivity().findViewById(R.id.Username);
        EditText StudentID = getActivity().findViewById(R.id.STUDID);
        TextInputEditText Email = getActivity().findViewById(R.id.Emailr);
        TextInputEditText Password = getActivity().findViewById(R.id.PasswordL);
        EditText Confirmed_Password = getActivity().findViewById(R.id.ConfirmPassword);
        Name.setText("");
        StudentID.setText("");
        //Email.setText("");
      //  Confirmed_Password.setText("");
    }
}