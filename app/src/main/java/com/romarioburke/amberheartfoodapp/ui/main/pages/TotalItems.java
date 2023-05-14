package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Adapters.BreakFastAdapter;
import com.romarioburke.amberheartfoodapp.Adapters.DinnerAdapter;
import com.romarioburke.amberheartfoodapp.Adapters.LunchAdapter;
import com.romarioburke.amberheartfoodapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TotalItems extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> BImg = new ArrayList<>();
    ArrayList<String> Bname = new ArrayList<>();
    ArrayList<String> Bdesc = new ArrayList<>();
    ArrayList<String> Bcategory = new ArrayList<>();
    ArrayList<String> BFoodUID = new ArrayList<>();
    ArrayList<String> BTarget = new ArrayList<>();

    ArrayList<String> LImg = new ArrayList<>();
    ArrayList<String> Lname = new ArrayList<>();
    ArrayList<String> Ldesc = new ArrayList<>();
    ArrayList<String> Lcategory = new ArrayList<>();
    ArrayList<String> LFoodUID = new ArrayList<>();
    ArrayList<String> LTarget = new ArrayList<>();
    ArrayList<String> DImg = new ArrayList<>();
    ArrayList<String> Dname = new ArrayList<>();
    ArrayList<String> Ddesc = new ArrayList<>();
    ArrayList<String> Dcategory = new ArrayList<>();
    ArrayList<String> DFoodUID = new ArrayList<>();
    ArrayList<String> DTarget = new ArrayList<>();

    public TotalItems() {
        // Required empty public constructor
    }

    public static TotalItems newInstance(String param1, String param2) {
        TotalItems fragment = new TotalItems();
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
        return inflater.inflate(R.layout.cooks_total_items, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String RequestURL = "https://api.romarioburke.com/api/v1/catalogs";
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RequestURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject result = new JSONObject(response.toString());
                                JSONArray Product = result.getJSONArray("data");
                                for (int i = 0; i < Product.length(); i++) {
                                    JSONObject Productdata = Product.getJSONObject(i);
                                    if (Productdata.getString("ItemCategory").equals("Breakfast")) {
                                        BImg.add(Productdata.getString("ItemImage"));
                                        Bname.add(Productdata.getString("ItemName"));
                                        Bdesc.add(Productdata.getString("ItemDescription"));
                                        Bcategory.add(Productdata.getString("ItemCategory"));
                                        BFoodUID.add(Productdata.getString("ItemID"));
                                        BTarget.add(Productdata.getString("ItemTarget"));
                                    } else if (Productdata.getString("ItemCategory").equals("Lunch")) {
                                        LImg.add(Productdata.getString("ItemImage"));
                                        Lname.add(Productdata.getString("ItemName"));
                                        Ldesc.add(Productdata.getString("ItemDescription"));
                                        Lcategory.add(Productdata.getString("ItemCategory"));
                                        LFoodUID.add(Productdata.getString("ItemID"));
                                        LTarget.add(Productdata.getString("ItemTarget"));
                                    } else if (Productdata.getString("ItemCategory").equals("Dinner")) {
                                        DImg.add(Productdata.getString("ItemImage"));
                                        Dname.add(Productdata.getString("ItemName"));
                                        Ddesc.add(Productdata.getString("ItemDescription"));
                                        Dcategory.add(Productdata.getString("ItemCategory"));
                                        DFoodUID.add(Productdata.getString("ItemID"));
                                        DTarget.add(Productdata.getString("ItemTarget"));
                                    }
                                }
                            } catch (JSONException EX) {
                                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
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

            }).start();
        } catch (Exception ex) {
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        ListView BreakfastView =(ListView)getActivity().findViewById(R.id.BreakFastList);
        ListView LunchView = (ListView)getActivity().findViewById(R.id.LunchList);
        ListView DinnerView = (ListView) getActivity().findViewById(R.id.DinnerList);
        BreakFastAdapter BreakAdpt = new BreakFastAdapter();
        LunchAdapter LunchAdpt = new LunchAdapter();
        DinnerAdapter DinnerAdpt = new DinnerAdapter(getContext(),Dname,DImg,Ddesc,Dcategory,DFoodUID,DTarget,this);
    }
}