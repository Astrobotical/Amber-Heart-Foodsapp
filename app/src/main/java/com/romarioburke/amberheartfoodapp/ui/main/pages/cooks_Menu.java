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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cooks_Menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cooks_Menu.
     */
    // TODO: Rename and change types and number of parameters
    public static cooks_Menu newInstance(String param1, String param2) {
        cooks_Menu fragment = new cooks_Menu();
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