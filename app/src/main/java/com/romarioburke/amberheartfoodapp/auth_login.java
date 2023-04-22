package com.romarioburke.amberheartfoodapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TooManyListenersException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link auth_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class auth_login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public auth_login() {
        // Required empty public constructor
    }

    public static auth_login newInstance(String param1, String param2) {
        auth_login fragment = new auth_login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_login, container, false);
    }

    String StudentID, Password = "";

    @Override
    public void onStart() {
        super.onStart();
        EditText StudentIDElement = this.getActivity().findViewById(R.id.SID);
        EditText PasswordElement = this.getActivity().findViewById(R.id.PasswordL);
        TextView top = this.getActivity().findViewById(R.id.head);
        Button Login = this.getActivity().findViewById(R.id.Loginbtn);
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
                            Toast.makeText(getContext(), "Please enter your StudentID", Toast.LENGTH_SHORT).show();
                            Choice = false;
                        }
                        if (Password.equals("")) {
                            Toast.makeText(getContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
                            Choice = false;
                        }
                        if (Choice) {
                            try {
                                Data obj = new Data(getContext());
                                PreparedStatement statement = null;
                                statement = obj.ConnectionString.prepareStatement("Select StudentID, Password, STATUS,Name FROM users WHERE StudentID = ? AND Password = ?");
                                statement.setString(1, StudentID);
                                statement.setString(2, Password);
                                Log.i("Queryresult", "Up to here");
                                ResultSet result = statement.executeQuery();
                                if (result.next()) {
                                    if (result.getString(3).equals("Student")) {
                                        Log.i("Queryresult", "Logged in");
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
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });
    }
}