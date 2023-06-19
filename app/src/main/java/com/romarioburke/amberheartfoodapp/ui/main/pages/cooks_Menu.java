package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;
import com.romarioburke.amberheartfoodapp.Dataclasses.Helpers.DatabaseHelper;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            String RequestURL = "https://api.romarioburke.com/api/v1/cart/getmenu";
            RequestQueue queue = Volley.newRequestQueue(this.getContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RequestURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject result = new JSONObject(response.toString());
                        String Message =  result.getString("response");
                        if(Message.equals("Success"))
                        {
                            String MenuID = result.getString("ActiveMenu");
                            Log.i("RESULTUID", MenuID);
                            SharedPreferences precheck = getActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);

                            //cartDB = DatabaseHelper.getInstance(context);
                            //Log.i("Query",cartDB.cartDAO().getCartItems(MenuID, CartID.toString()).get(0).toString());
                            //ArrayList<CartModel> seting = (ArrayList<CartModel>) cartDB.cartDAO().getCartItems(MenuID, CartID.toString());
                          //  Viewmodel.setCartItems(seting);
                 /*       Viewmodel.getCartItems().observe(main, items -> {
                            for(CartModel item : items)
                            {
                                Log.i("CartItems", item.getFoodName());
                            }
                        }); */
                        }
                        else
                        {
                            //Toast.makeText(context, "No Active Menu", Toast.LENGTH_SHORT).show();
                            Log.i("RESULTUID", "No Active Menu");
                        }
                    } catch (JSONException EX) {
                        Log.i("CustomError", EX.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("CustomError", error.toString());
                }
            });
            queue.add(request);
        });
        Alter_Menu_items.setOnClickListener((view)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new TotalItems()).commit();
        });
    }


}