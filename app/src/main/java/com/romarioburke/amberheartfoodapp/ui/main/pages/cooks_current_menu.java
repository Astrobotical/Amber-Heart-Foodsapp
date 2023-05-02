package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romarioburke.amberheartfoodapp.R;

public class cooks_current_menu extends Fragment {

    public cooks_current_menu() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cooks_current_menu_main, container, false);
    }
}