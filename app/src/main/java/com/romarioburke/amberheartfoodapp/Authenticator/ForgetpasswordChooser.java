package com.romarioburke.amberheartfoodapp.Authenticator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.romarioburke.amberheartfoodapp.R;


public class ForgetpasswordChooser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgetpasswordChooser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetpasswordChooser.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetpasswordChooser newInstance(String param1, String param2) {
        ForgetpasswordChooser fragment = new ForgetpasswordChooser();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgetpassword_chooser, container, false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        ImageView BackButton = getActivity().findViewById(R.id.BackButton);
        Button resetEmail = getActivity().findViewById(R.id.ResetByEmail);
        Button resetStudentID = getActivity().findViewById(R.id.ResetByStudentID);
        BackButton.setOnClickListener((view)->{
            Intent switchbackactivity = new Intent(getContext(), Auth.class);
            startActivity(switchbackactivity);
        });
        resetEmail.setOnClickListener((view)->{
            Intent switchbtoreset = new Intent(getContext(), Auth.class);
            startActivity(switchbtoreset);
        });
        resetStudentID.setOnClickListener((view)->
        {
            Intent switchbtostudentid = new Intent(getContext(), Auth.class);
            startActivity(switchbtostudentid);
        });
    }
}