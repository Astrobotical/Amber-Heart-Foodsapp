package com.romarioburke.amberheartfoodapp.Authenticator;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.romarioburke.amberheartfoodapp.Database.Data;
import com.romarioburke.amberheartfoodapp.MainActivity;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.cooks_main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class auth_login extends Fragment {
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    public auth_login() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_login, container, false);
    }

    String StudentID, Password = "";

    @Override
    public void onStart() {
        super.onStart();
        int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES-> Day();

            case Configuration.UI_MODE_NIGHT_NO -> Night();

            case Configuration.UI_MODE_NIGHT_UNDEFINED-> Day();
        }
        TextInputEditText StudentIDElement = this.getActivity().findViewById(R.id.Token);
        TextInputEditText PasswordElement = this.getActivity().findViewById(R.id.PasswordL);
        TextView top = this.getActivity().findViewById(R.id.head);
        TextView ForgetPassword = this.getActivity().findViewById(R.id.forgetpassword);

        Button Login = this.getActivity().findViewById(R.id.Loginbtn);
        SignInButton signInButton = this.getActivity().findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        //Google Method below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
       // mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTapClient = Identity.getSignInClient(getContext());
                signInRequest = BeginSignInRequest.builder()
                        .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                                .setSupported(true)
                                .build())
                        .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                // Your server's client ID, not your Android client ID.
                                .setServerClientId(getString(R.string.authid))
                                // Only show accounts previously used to sign in.
                                .setFilterByAuthorizedAccounts(true)
                                .build())
                        // Automatically sign in when exactly one credential is retrieved.
                        .setAutoSelectEnabled(true)
                        .build();
            }
        });
        ForgetPassword.setOnClickListener((view)->{
            Intent switchactivity = new Intent(getContext(), ResetOptions.class);
            startActivity(switchactivity);
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentID = StudentIDElement.getText().toString();
                Password = PasswordElement.getText().toString();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean Choice = true;
                        if (StudentID.equals("")) {
                            //Toast.makeText(getContext(), "Please enter your StudentID", Toast.LENGTH_SHORT).show();
                            StudentIDElement.setError("Please enter your StudentID");
                            Choice = false;
                        }
                        if (Password.equals("")) {
                            //Toast.makeText(getContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
                            PasswordElement.setError("Please enter your Password");
                            Choice = false;
                        }
                        if (Choice) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Data obj = new Data(getContext());
                                            PreparedStatement statement = null;
                                            statement = obj.ConnectionString.prepareStatement("Select StudentID, Password, Status,Name FROM students WHERE StudentID = ? AND Password = ?");
                                            statement.setString(1, StudentID);
                                            statement.setString(2, Password);
                                            Log.i("Queryresult", "Up to here");
                                            ResultSet result = statement.executeQuery();
                                            if (result.next()) {
                                                if (result.getString(3).equals("Student")) {
                                                    Log.i("Queryresult", "Account found, User is Logged in");
                                                    Intent activity = new Intent(getContext(), MainActivity.class);
                                                    String prebroken = result.getString(4);
                                                    String[] total_String = prebroken.split("\\s");
                                                    String Usernamebuilder = total_String[0] + " " + total_String[1].substring(0, 1).toUpperCase() + ".";
                                                    activity.putExtra("Username", Usernamebuilder);
                                                    startActivity(activity);
                                                } else {
                                                    Log.i("Queryresult", "Logged in");
                                                    Intent activity = new Intent(getContext(), cooks_main.class);
                                                    startActivity(activity);
                                                }
                                            }

                                            else{
                                                Snackbar.make(getView(),"Invalid Account details", Snackbar.LENGTH_LONG).show();
                                            }
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }).start();
                            }}
                });
            }
        });
    }
   /* private void signIn() {
        Intent signInIntent = new mmGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    */
    private void Day()
    {
        int Black_color = getResources().getColor(R.color.black);
        int colorInt = getResources().getColor(R.color.white);
        TextView Header = getActivity().findViewById(R.id.head);
        Button Submit = getActivity().findViewById(R.id.Loginbtn);
        Submit.setTextColor(ColorStateList.valueOf(Black_color));
        Header.setTextColor(ColorStateList.valueOf(colorInt));
    }
    private void Night(){
        int Black_color = getResources().getColor(R.color.black);
        int colorInt = getResources().getColor(R.color.white);
        CheckBox Rememberme = getActivity().findViewById(R.id.rememberme);
        TextView Header = getActivity().findViewById(R.id.head);
        Button Submit = getActivity().findViewById(R.id.Loginbtn);
       // Rememberme.setTextColor(ColorStateList.valueOf(colorInt));
        //Submit.setTextColor(Color.parseColor("#FFFFFF"));
        //Submit.setTextColor(ColorStateList.valueOf(colorInt));
       // Submit.setLinkTextColor(ColorStateList.valueOf(colorInt));
        //Submit.setColor
        Header.setTextColor(ColorStateList.valueOf(colorInt));
    }

}