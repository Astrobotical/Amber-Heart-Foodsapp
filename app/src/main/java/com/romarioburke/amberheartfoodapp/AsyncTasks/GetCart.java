package com.romarioburke.amberheartfoodapp.AsyncTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;
import com.romarioburke.amberheartfoodapp.Dataclasses.Helpers.DatabaseHelper;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetCart implements  Runnable{
    private Context context;
    Fragment main;
    private JSONArray Product;
    DatabaseHelper cartDB;
    public GetCart(Context context, Fragment Frag){
        this.context = context;
        this.main = Frag;
    }
    @Override
    public void run() {
        String RequestURL = "https://api.romarioburke.com/api/v1/cart/getmenu";
        RequestQueue queue = Volley.newRequestQueue(context);
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
                        SharedPreferences precheck = main.getActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
                        Integer CartID = precheck.getInt("CartID", 0);
                        ProductsModel Viewmodel = new ViewModelProvider(main).get(ProductsModel.class);
                        Viewmodel.setMenuID(MenuID);
                        cartDB = DatabaseHelper.getInstance(context);
                        //Log.i("Query",cartDB.cartDAO().getCartItems(MenuID, CartID.toString()).get(0).toString());
                        ArrayList<CartModel> seting = (ArrayList<CartModel>) cartDB.cartDAO().getCartItems(MenuID, CartID.toString());
                        Viewmodel.setCartItems(seting);
                 /*       Viewmodel.getCartItems().observe(main, items -> {
                            for(CartModel item : items)
                            {
                                Log.i("CartItems", item.getFoodName());
                            }
                        });

                  */
                    }
                     else
                      {
                          Toast.makeText(context, "No Active Menu", Toast.LENGTH_SHORT).show();
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
    }
}
