package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.romarioburke.amberheartfoodapp.R;


public class adder extends Fragment {


    public adder() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adder, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

    }
}