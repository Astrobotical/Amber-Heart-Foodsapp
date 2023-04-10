package com.romarioburke.amberheartfoodapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringBufferInputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Products#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Products extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> DefaultImg = new ArrayList<>();
    ArrayList<String> Defaultname = new ArrayList<>();
    ArrayList<String> Defaultdesc = new ArrayList<>();
    ArrayList<String> Defaultcategory = new ArrayList<>();
    ArrayList<String> DefaultFoodUID = new ArrayList<>();

    ArrayList<String> BImg = new ArrayList<>();
    ArrayList<String> Bname = new ArrayList<>();
    ArrayList<String> Bdesc = new ArrayList<>();
    ArrayList<String> Bcategory = new ArrayList<>();
    ArrayList<String> BFoodUID = new ArrayList<>();

    ArrayList<String> LImg = new ArrayList<>();
    ArrayList<String> Lname = new ArrayList<>();
    ArrayList<String> Ldesc = new ArrayList<>();
    ArrayList<String> Lcategory = new ArrayList<>();
    ArrayList<String> LFoodUID = new ArrayList<>();

    ArrayList<String> DImg = new ArrayList<>();
    ArrayList<String> Dname = new ArrayList<>();
    ArrayList<String> Ddesc = new ArrayList<>();
    ArrayList<String> Dcategory = new ArrayList<>();
    ArrayList<String> DFoodUID = new ArrayList<>();

    public Products() {
        // Required empty public constructor
    }
    public static Products newInstance(String param1, String param2) {
        Products fragment = new Products();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    String RequestURL = "https://api.romarioburke.com/api/v1/catalogs";
    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RequestURL,null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                Toast.makeText(getActivity().getApplicationContext(), "Response :" +response.toString(), Toast.LENGTH_LONG).show();
                JSONObject result = new JSONObject(response.toString());
                JSONArray Product = result.getJSONArray("data");
                for (int i = 0; i < Product.length(); i++) {
                    JSONObject Productdata = Product.getJSONObject(i);
                    DefaultImg.add(Productdata.getString("ItemImage"));
                    Defaultname.add(Productdata.getString("ItemName"));
                    Defaultdesc.add(Productdata.getString("ItemDescription"));
                    Defaultcategory.add(Productdata.getString("ItemCategory"));
                    DefaultFoodUID.add(Productdata.getString("ItemID"));
                    if(Productdata.getString("ItemCategory").equals("Breakfast"))
                    {
                        BImg.add(Productdata.getString("ItemImage"));
                        Bname.add(Productdata.getString("ItemName"));
                        Bdesc.add(Productdata.getString("ItemDescription"));
                        Bcategory.add(Productdata.getString("ItemCategory"));
                        BFoodUID.add(Productdata.getString("ItemID"));  
                    }else if(Productdata.getString("ItemCategory").equals("Lunch"))
                    {
                        LImg.add(Productdata.getString("ItemImage"));
                        Lname.add(Productdata.getString("ItemName"));
                        Ldesc.add(Productdata.getString("ItemDescription"));
                        Lcategory.add(Productdata.getString("ItemCategory"));
                        LFoodUID.add(Productdata.getString("ItemID"));
                    }else if(Productdata.getString("ItemCategory").equals("Dinner"))
                    {
                        DImg.add(Productdata.getString("ItemImage"));
                        Dname.add(Productdata.getString("ItemName"));
                        Ddesc.add(Productdata.getString("ItemDescription"));
                        Dcategory.add(Productdata.getString("ItemCategory"));
                        DFoodUID.add(Productdata.getString("ItemID"));
                    }
                }
                pulldata("All");
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
        return inflater.inflate(R.layout.fragment_products, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

    }
    void pulldata(String RequestType){
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        if(RequestType.equals("All")){
            griditems Grid = new griditems(this.getActivity().getApplicationContext(),Defaultname,DefaultImg,Defaultdesc,Defaultcategory,DefaultFoodUID);
            gridView.setAdapter(Grid);
        }else if(RequestType.equals("Breakfast"))
        {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(),Bname,BImg,Bdesc,Bcategory,BFoodUID);
            gridView.setAdapter(Grid);
        }else if(RequestType.equals("Lunch"))
        {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(),Lname,LImg,Ldesc,Lcategory,LFoodUID);
            gridView.setAdapter(Grid);
        }else if(RequestType.equals("Dinner"))
        {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(),Dname,DImg,Ddesc,Dcategory,DFoodUID);
            gridView.setAdapter(Grid);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Button Breakfast = this.getActivity().findViewById(R.id.Breakfast);
        Button Lunch = this.getActivity().findViewById(R.id.lunch);
        Button Dinner = this.getActivity().findViewById(R.id.dinner);
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        Button All = this.getActivity().findViewById(R.id.all);
        All.setOnClickListener((view)->{
            pulldata("All");
        });
        Breakfast.setOnClickListener((view)->{
            pulldata("Breakfast");
        });
        Lunch.setOnClickListener((view)->{
            Toast.makeText(getActivity(),"This works, it is lunch",Toast.LENGTH_SHORT).show();
            pulldata("Lunch");
        });
        Dinner.setOnClickListener((view)->{
            Toast.makeText(getActivity(),"This works, it is Dinner",Toast.LENGTH_SHORT).show();
            pulldata("Dinner");
            Dinner.setBackgroundTintList(ColorStateList.valueOf(R.color.menubtn));
        });
    }

}