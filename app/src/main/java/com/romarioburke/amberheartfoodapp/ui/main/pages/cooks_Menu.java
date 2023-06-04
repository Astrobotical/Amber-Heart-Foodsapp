package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.romarioburke.amberheartfoodapp.R;

public class cooks_Menu extends Fragment {
    Fragment Frag = null;
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
        ImageButton Alter_Menu_items = getActivity().findViewById(R.id.Edititems);
        Additem.setOnClickListener((view)->{
            // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new food_add()).commit();
            AlertDialog.Builder prompt = new AlertDialog.Builder(getContext());
            prompt.setView(R.layout.cooks_addingfoodtype);
            AlertDialog alertDialog = prompt.create();
            alertDialog.show();
            Button Backbtn = alertDialog.findViewById(R.id.backbtn);
            Button Confirmbtn = alertDialog.findViewById(R.id.forwardbtn);
            RadioButton AddRegularItem = alertDialog.findViewById(R.id.option1);
            RadioButton AddSide = alertDialog.findViewById(R.id.option2);



            Backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.onBackPressed();
                }
            });

            Confirmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AddRegularItem.isChecked())
                    {
                        Frag = new food_add();
                    }
                    if(AddSide.isChecked())
                    {
                        Frag = new Sides_add();
                    }
                    if(Frag == null )
                    {
                        Toast.makeText(getContext(),"Please select one of the options",Toast.LENGTH_LONG).show();
                    }else {
                        alertDialog.onBackPressed();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, Frag).commit();
                    }
                }
            });
        });
        Menu_create.setOnClickListener((view)->{
            //getActivity().getSupportFragmentManager().beginTransaction().replace().commit();
        });
        Alter_Menu_items.setOnClickListener((view)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new TotalItems()).commit();
        });
    }


}