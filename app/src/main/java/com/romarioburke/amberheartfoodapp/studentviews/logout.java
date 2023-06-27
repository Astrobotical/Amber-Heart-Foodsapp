package com.romarioburke.amberheartfoodapp.studentviews;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romarioburke.amberheartfoodapp.Authenticator.Auth;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.login;

public class logout extends Fragment {

    public logout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent nextactivity = new Intent(getContext(), login.class);
        startActivity(nextactivity);
       getActivity().overridePendingTransition(R.anim.sliderigt, R.anim.outleft);
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

 */
}