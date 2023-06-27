package com.romarioburke.amberheartfoodapp.Authenticator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.romarioburke.amberheartfoodapp.MainActivity;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.cooks_main;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class auth_login extends Fragment {
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private SharedPreferences preference;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    boolean checked;

    public auth_login() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_login, container, false);
    }

    String StudentID, Password = "";
    private void signin() {
        Intent Sign = gsc.getSignInIntent();
        startActivityForResult(Sign, 1000);
    }
    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        //updateUI(account);
        DisplayModes();
        CheckBox rememberme = this.getActivity().findViewById(R.id.rememberme);
        TextInputEditText StudentIDElement = this.getActivity().findViewById(R.id.tokenid);
        TextInputEditText PasswordElement = this.getActivity().findViewById(R.id.PasswordL);
        preference = getActivity().getPreferences(0);
        int loadtime = 0;
        SharedPreferences logs = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
        String IsSET = logs.getString("Stored_StudentID", "");
        if (IsSET.isEmpty() && loadtime <= 0) {
            StudentIDElement.setText("");
        } else {
            rememberme.setChecked(true);
            StudentIDElement.setText(IsSET);
        }
        TextView top = this.getActivity().findViewById(R.id.head);
        TextView ForgetPassword = this.getActivity().findViewById(R.id.forgetpassword);
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Your student ID will be stored", Toast.LENGTH_SHORT).show();
                    checked = true;
                } else {
                    Toast.makeText(getContext(), "Your student ID will not be saved", Toast.LENGTH_SHORT).show();
                    checked = false;
                }
            }
        });
        Button Login = this.getActivity().findViewById(R.id.Loginbtn);
      //  SignInButton signInButton = this.getActivity().findViewById(R.id.sign_in_button);
       // signInButton.setSize(SignInButton.SIZE_WIDE);
        //Google Method below
       // signInButton.setOnClickListener(new View.OnClickListener() {
            //@Override
          ///  public void onClick(View v) {
               // signin();
              /*  oneTapClient = Identity.getSignInClient(getContext());
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

               */
           // }
       // });
        ForgetPassword.setOnClickListener((view) -> {
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
                            StudentIDElement.setError("Please enter your StudentID");
                            Choice = false;
                        }
                        if (Password.equals("")) {
                            PasswordElement.setError("Please enter your Password");
                            Choice = false;
                        }
                        if (Choice) {
                            String url = "https://api.romarioburke.com/api/v1/auth/login";
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        if (checked) {
                                            SharedPreferences logs = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = logs.edit();
                                            myEdit.putString("Stored_StudentID", StudentIDElement.getText().toString());
                                            myEdit.apply();
                                        } else {
                                            SharedPreferences logs = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = logs.edit();
                                            myEdit.putString("Stored_StudentID", "");
                                            myEdit.apply();
                                        }
                                        JSONObject obj = new JSONObject(response);
                                        String Message = obj.optString("response");
                                        String Name = obj.optString("Name");
                                        String ID = obj.optString("StudentID");
                                        String Status = obj.optString("Status");
                                        String convert = obj.optString("Highestrating");
                                        JSONObject high = new JSONObject(convert);
                                       String Menu = high.optString("Menu");
                                       JSONObject Menuobj = new JSONObject(Menu);
                                        String Item_name = Menuobj.getString("Item_name");
                                        String Item_img = Menuobj.getString("Item_image");
                                        float Item_rating = 4.5f;
                                        if (Message.equals("Success")) {
                                            SharedPreferences logs = getActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = logs.edit();
                                            myEdit.putString("ID", ID);
                                            myEdit.putString("type", Status);
                                            myEdit.putString("Name", Name);
                                            myEdit.apply();
                                            Toast.makeText(getContext(), "Logged in successful", Toast.LENGTH_LONG).show();
                                            if (Status.equals("Student")) {
                                                Intent activity = new Intent(getActivity(), MainActivity.class);
                                                String prebroken = Name;
                                                String[] total_String = prebroken.split("\\s");
                                                String Usernamebuilder = total_String[0] + " " + total_String[1].substring(0, 1).toUpperCase() + ".";
                                                activity.putExtra("Username", Usernamebuilder);
                                                activity.putExtra("StudentID", ID);
                                                activity.putExtra("Item_name", Item_name);
                                                activity.putExtra("Item_img", Item_img);
                                                activity.putExtra("Item_rating", Item_rating);
                                                startActivity(activity);
                                            } else {
                                                Log.i("Queryresult", "Logged in");
                                                Intent activity = new Intent(getActivity(), cooks_main.class);
                                                startActivity(activity);
                                            }
                                        } else if (Message.equals("Error") || Message.equals("Error1")) {


                                        }
                                    } catch (JSONException e) {
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getContext().getApplicationContext(), "Invalid Password or StudentID", Toast.LENGTH_LONG).show();
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
                           /* Login Loginobj = new Login(StudentID,Password,getActivity());
                            if(Loginobj.UserLogin()) {
                                Toast.makeText(getContext(), "Logged in successful", Toast.LENGTH_LONG).show();
                                SharedPreferences logs = getActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE);
                                String Name = logs.getString("Name", "");
                                String Type = logs.getString("type", "");
                                if (Type.equals("Student")) {
                                    Intent activity = new Intent(getActivity(), MainActivity.class);
                                    String prebroken = Name;
                                    String[] total_String = prebroken.split("\\s");
                                    String Usernamebuilder = total_String[0] + " " + total_String[1].substring(0, 1).toUpperCase() + ".";
                                    activity.putExtra("Username", Usernamebuilder);
                                    startActivity(activity);
                                } else {
                                    Log.i("Queryresult", "Logged in");
                                    Intent activity = new Intent(getActivity(), cooks_main.class);
                                    startActivity(activity);
                                }
                            }
                            else {
                                Toast.makeText(getContext(),"Failed the login",Toast.LENGTH_LONG).show();
                            }
                            /*
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
                                                    SharedPreferences.Editor Sharedobj = ((SharedPreferences) preference).edit();
                                                    Sharedobj.putString("STUID",StudentID);
                                                    Sharedobj.commit();
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
                                }).start();*/
                            queue.add(request);
                        }
                    }
                });
            }
        });
    }

    /* private void signIn() {
         Intent signInIntent = new mmGoogleSignInClient.getSignInIntent();
         startActivityForResult(signInIntent, RC_SIGN_IN);
     }

     */
    private void Day() {/*
        int Black_color = getResources().getColor(R.color.black);
        int colorInt = getResources().getColor(R.color.white);
        TextView Header = getActivity().findViewById(R.id.head);
        Button Submit = getActivity().findViewById(R.id.Loginbtn);
        TextView Forgetpassword = getActivity().findViewById(R.id.forgetpassword);
        CheckBox Rememberme = (CheckBox) getActivity().findViewById(R.id.rememberme);
        Forgetpassword.setTextColor(Color.BLACK);
        Rememberme.setTextColor(Color.BLACK);
        Submit.setTextColor(ColorStateList.valueOf(Black_color));
        Header.setTextColor(ColorStateList.valueOf(Black_color));
        */
    }

    private void Night() {
        //RelativeLayout Container= getActivity().findViewById(R.id.container);
        int Black_color = getResources().getColor(R.color.black);
        int colorInt = getResources().getColor(R.color.white);
        CheckBox Rememberme = (CheckBox) getActivity().findViewById(R.id.rememberme);
        TextView Header = getActivity().findViewById(R.id.head);
        Button Submit = getActivity().findViewById(R.id.Loginbtn);
       // Container.setBackgroundColor(Color.GRAY);
        //TextView Forgetpassword = getActivity().findViewById(R.id.forgetpassword);
       // Forgetpassword.setTextColor(Color.WHITE);
        //Rememberme.setTextColor(Color.WHITE);
        // Rememberme.setTextColor(ColorStateList.valueOf(colorInt));
        //Submit.setTextColor(Color.parseColor("#FFFFFF"));
        //Submit.setTextColor(ColorStateList.valueOf(colorInt));
        // Submit.setLinkTextColor(ColorStateList.valueOf(colorInt));
        //Submit.setColor
        Header.setTextColor(ColorStateList.valueOf(colorInt));


    }

    private void DisplayModes() {
        int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES -> Day();

            case Configuration.UI_MODE_NIGHT_NO -> Night();

            case Configuration.UI_MODE_NIGHT_UNDEFINED -> Day();
        }
    }



    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode == 1000)
        {
            Task<GoogleSignInAccount> managedtask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(managedtask);
            try{
                managedtask.getResult(ApiException.class);

            }catch (ApiException ex)
            {
                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Toast.makeText(getContext(),"Signed in successfully",Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getContext(),"Failed to signed in",Toast.LENGTH_SHORT).show();
        }
    }
}