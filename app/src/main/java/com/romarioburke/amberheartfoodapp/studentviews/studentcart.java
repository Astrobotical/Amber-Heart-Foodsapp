package com.romarioburke.amberheartfoodapp.studentviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Adapters.CartAdapter;
import com.romarioburke.amberheartfoodapp.Adapters.emptyadapter;
import com.romarioburke.amberheartfoodapp.AsyncTasks.GetCart;
import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;
import com.romarioburke.amberheartfoodapp.Dataclasses.Helpers.DatabaseHelper;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studentcart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentcart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<CartModel> cartItems;
    DatabaseHelper cartDB;
    Integer Counter = 0;
    public studentcart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studentcart.
     */
    // TODO: Rename and change types and number of parameters
    public static studentcart newInstance(String param1, String param2) {
        studentcart fragment = new studentcart();
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
    ArrayList<String>FoodName = new ArrayList<>();
    ArrayList<String>FoodImage = new ArrayList<>();
    ArrayList<String>FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
    ArrayList<String>SideUID = new ArrayList<>();
    ArrayList<String>SideName = new ArrayList<>();
    Fragment main = this;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cart, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Button Checkout = getActivity().findViewById(R.id.Checkout);
        String RequestURL = "https://api.romarioburke.com/api/v1/cart/getmenu";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                        String CartID = String.valueOf(precheck.getInt("CartID", 0));
                        ProductsModel Viewmodel = new ViewModelProvider(getActivity()).get(ProductsModel.class);
                        Viewmodel.setMenuID(MenuID);
                        cartDB = DatabaseHelper.getInstance(getContext());
                        //Log.i("Query",cartDB.cartDAO().getCartItems(MenuID, CartID.toString()).get(0).toString());
                        ArrayList<CartModel> seting = (ArrayList<CartModel>) cartDB.cartDAO().getCartItems(MenuID, CartID);
                        for(int i = 0; i < seting.size(); i++)
                        {
                           FoodName.add(seting.get(i).getFoodName());
                            FoodImage.add(seting.get(i).getFoodimg());
                            FoodCategory.add(seting.get(i).getFoodCategory());
                            FoodUID.add(seting.get(i).getFoodID());
                            SideUID.add(seting.get(i).getSideID());
                            SideName.add(seting.get(i).getSideName());
                        }
                        GridView GridLayout = getActivity().findViewById(R.id.cartgrid);
                        if(FoodName.size() == 0)
                        {
                            emptyadapter emptygrid =new emptyadapter(getParentFragment(),getContext());
                            GridLayout.setAdapter(emptygrid);
                        }
                        else
                        {
                            CartAdapter adapter = new CartAdapter(getContext(), main,FoodName, FoodImage, FoodCategory, FoodUID, SideUID, SideName,CartID);
                            GridLayout.setAdapter(adapter);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "No Active Menu", Toast.LENGTH_SHORT).show();
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