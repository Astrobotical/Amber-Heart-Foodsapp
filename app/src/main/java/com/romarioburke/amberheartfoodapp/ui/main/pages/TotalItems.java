package com.romarioburke.amberheartfoodapp.ui.main.pages;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Adapters.DinnerAdapter;
import com.romarioburke.amberheartfoodapp.Adapters.emptyadapter;
import com.romarioburke.amberheartfoodapp.Adapters.griditems;
import com.romarioburke.amberheartfoodapp.Adapters.itemsadapter;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.SavedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TotalItems extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button PreviouslyClicked;
    Bundle bundler;
    HashMap<String, String> Selecteditems = new HashMap<String, String>();
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

    ArrayList<String> SImg = new ArrayList<>();
    ArrayList<String> Sname = new ArrayList<>();
    ArrayList<String> Sdesc = new ArrayList<>();
    ArrayList<String> Scategory = new ArrayList<>();
    ArrayList<String> SFoodUID = new ArrayList<>();
    ArrayList<String> STarget = new ArrayList<>();
    SavedData Viewmodeler;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cooks_total_items, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       getinfo();

    }
    private void getinfo()
    {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String RequestURL = "https://api.romarioburke.com/api/v1/catalogs";
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    Log.i("Listitems", "Here");
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RequestURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.i("Listitems", response.toString());
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
                                    } else if(Productdata.getString("ItemCategory").equals("Sides")) {
                                        SImg.add(Productdata.getString("ItemImage"));
                                        Sname.add(Productdata.getString("ItemName"));
                                        Sdesc.add(Productdata.getString("ItemDescription"));
                                        Scategory.add(Productdata.getString("ItemCategory"));
                                        SFoodUID.add(Productdata.getString("ItemID"));
                                        STarget.add(Productdata.getString("ItemTarget"));
                                    }
                                }
                                pulldata("Breakfast");

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
    void resetdata(){
        Dcategory =null;
        Ddesc = null;
        Dname= null;
        DImg = null;
        DTarget= null;
        DFoodUID= null;

        Lcategory= null;
        Ldesc =null;
        Lname=null;
        LFoodUID = null;
        LTarget=null;
        LImg=null;

        Scategory= null;
        Sdesc =null;
        Sname=null;
        SFoodUID = null;
        STarget=null;
        SImg=null;
        Bcategory= null;
        Bdesc =null;
        Bname=null;
        BFoodUID = null;
        BTarget=null;
        BImg=null;
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("Listitems", "Started");
        String[] listItems = {"Itme one", "Item two", "Item three"};
        ArrayAdapter<String> adapter;
        Button Breakfast = this.getActivity().findViewById(R.id.Breakfast);
        Button Lunch = this.getActivity().findViewById(R.id.lunch);
        Button Dinner = this.getActivity().findViewById(R.id.dinner);
        Button Sides = this.getActivity().findViewById(R.id.sides);
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        TextView featured = this.getActivity().findViewById(R.id.ViewModelELEMENET);
        TextView  Head = this.getActivity().findViewById(R.id.Head);
        if (PreviouslyClicked == null) {
            Breakfast.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
            Head.setText("Breakfast Menu Items");
            PreviouslyClicked = Breakfast;
        } else {
            Breakfast.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        }

        Breakfast.setOnClickListener((view) -> {
            Head.setText("Breakfast Menu Items");
            pulldata("Breakfast");
            ActiveButton(Breakfast);

        });
        Lunch.setOnClickListener((view) -> {

            Head.setText("Lunch Menu Items");
           // Toast.makeText(getActivity(), "This works, it is lunch", Toast.LENGTH_SHORT).show();
            pulldata("Lunch");
            ActiveButton(Lunch);
        });
        Dinner.setOnClickListener((view) -> {
            Head.setText("Dinner Menu Items");
            //Toast.makeText(getActivity(), "This works, it is Dinner", Toast.LENGTH_SHORT).show();
            pulldata("Dinner");
            ActiveButton(Dinner);
            //Dinner.setBackgroundTintList(ColorStateList.valueOf(R.color.menubtn));
        });
        Sides.setOnClickListener((view)->{
           // resetdata();
            Head.setText("Side Menu Items");
            pulldata("Sides");
            ActiveButton(Sides);
        });
    }

    void pulldata(String RequestType) {
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        emptyadapter emptygrid =new emptyadapter(this,getContext());
        if (RequestType.equals("Breakfast")) {
            itemsadapter Grid = new itemsadapter(this.getActivity().getApplicationContext(), Bname, BImg, Bdesc, Bcategory, BFoodUID, bundler, Selecteditems, BTarget, this);
            if(Bname.size() == 0){
                gridView.setAdapter(emptygrid);
            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(Grid);
                    }
                });
            }
        } else if (RequestType.equals("Lunch")) {
            itemsadapter Grid = new itemsadapter(this.getActivity().getApplicationContext(), Lname, LImg, Ldesc, Lcategory, LFoodUID, bundler, Selecteditems, LTarget, this);
            if(Lname.size() == 0){
                gridView.setAdapter(emptygrid);
            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(Grid);
                    }
                });
            }
        } else if (RequestType.equals("Dinner")) {

            itemsadapter Grid = new itemsadapter(this.getActivity().getApplicationContext(), Dname, DImg, Ddesc, Dcategory, DFoodUID, bundler, Selecteditems, DTarget, this);
            if(Dname.size() == 0){
                gridView.setAdapter(emptygrid);
            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(Grid);
                    }
                });
            }
        }else if( RequestType.equals("Sides"))
        {
            itemsadapter Grid = new itemsadapter(this.getActivity().getApplicationContext(), Sname, SImg, Sdesc, Scategory, SFoodUID, bundler, Selecteditems, STarget, this);
            if(Sname.size() == 0){
                gridView.setAdapter(emptygrid);
            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(Grid);
                    }
                });

            }
        }
    }

    private void ActiveButton(Button current) {
        if (PreviouslyClicked == null) {
            current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        } else {
            PreviouslyClicked.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }
        switch (current.getText().toString()) {
            case "Breakfast":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                PreviouslyClicked = current;
                break;
            case "Lunch":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                PreviouslyClicked = current;
                break;
            case "Dinner":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                PreviouslyClicked = current;
                break;
            case "Sides":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                PreviouslyClicked = current;
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    Viewmodeler = new ViewModelProvider(this).get(SavedData.class);
                    Viewmodeler.Saveimage(bitmap);
                }
            }
            break;
            case 1: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), imageUri);
                        Viewmodeler = new ViewModelProvider(this).get(SavedData.class);
                        Viewmodeler.Saveimage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }
}