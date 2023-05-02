package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.romarioburke.amberheartfoodapp.R;

public class cooks_Menu extends Fragment {
    public cooks_Menu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cooks__menu, container, false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        ImageButton Menu_create = getActivity().findViewById(R.id.MenuCreate_btn);
        ImageButton Additem = getActivity().findViewById(R.id.additem);
        Additem.setOnClickListener((view)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new food_add()).commit();
        });
        Menu_create.setOnClickListener((view)->{
            //getActivity().getSupportFragmentManager().beginTransaction().replace().commit();
        });
    }


}