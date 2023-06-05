package com.romarioburke.amberheartfoodapp.studentviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Adapters.emptyadapter;
import com.romarioburke.amberheartfoodapp.Adapters.griditems;
import com.romarioburke.amberheartfoodapp.AsyncTasks.GetProducts;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.SavedData;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Products#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Products extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ArrayList<String> DefaultImg = new ArrayList<>();
    ArrayList<String> Defaultname = new ArrayList<>();
    ArrayList<String> Defaultdesc = new ArrayList<>();
    ArrayList<String> Defaultcategory = new ArrayList<>();
    ArrayList<String> DefaultFoodUID = new ArrayList<>();
    ArrayList<String> DefaultTarget = new ArrayList<>();


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
    Bundle bundler;
    HashMap<String, String> Selecteditems = new HashMap<String, String>();
    SavedData Datathatwassaved;
    CardView OuterContainer,HeadingContainer;
    Button PreviouslyClicked,currentbtn;
    String CurrentClicked;

    public Products() {
        // Required empty public constructor
    }

    public static Products newInstance(String param1, String param2, String T) {
        Products fragment = new Products();
        Bundle args = new Bundle();
        args.putString("test", T);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Products newInstance() {
        Products fragment = new Products();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        GetProducts data = new GetProducts(context,this);
        Thread currentdata = new Thread(data);
        currentdata.start();
        ProductsModel Products = new ViewModelProvider(this).get(ProductsModel.class);
        Products.getProducts().observe(this, saved -> {
            cleardata();
            for (int i = 0; i < saved.length(); i++) {
                JSONObject Productdata = null;
                try {
                    Productdata = saved.getJSONObject(i);
                    if (!Productdata.getString("ItemCategory").equals("Sides")) {
                        DefaultImg.add(Productdata.getString("ItemImage"));
                        Defaultname.add(Productdata.getString("ItemName"));
                        Defaultdesc.add(Productdata.getString("ItemDescription"));
                        Defaultcategory.add(Productdata.getString("ItemCategory"));
                        DefaultFoodUID.add(Productdata.getString("ItemID"));
                        DefaultTarget.add(Productdata.getString("ItemTarget"));
                    }
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
                    else if(Productdata.getString("ItemCategory").equals("Sides")) {
                        SImg.add(Productdata.getString("ItemImage"));
                        Sname.add(Productdata.getString("ItemName"));
                        Sdesc.add(Productdata.getString("ItemDescription"));
                        Scategory.add(Productdata.getString("ItemCategory"));
                        SFoodUID.add(Productdata.getString("ItemID"));
                        STarget.add(Productdata.getString("ItemTarget"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            if(CurrentClicked == null) {
               pulldata("All");
                //ActiveButton(All);
            }else{
                pulldata(CurrentClicked);
                ActiveButton(currentbtn);
            }
        });
        /*
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
                                    if(!Productdata.getString("ItemCategory").equals("Sides")) {
                                        DefaultImg.add(Productdata.getString("ItemImage"));
                                        Defaultname.add(Productdata.getString("ItemName"));
                                        Defaultdesc.add(Productdata.getString("ItemDescription"));
                                        Defaultcategory.add(Productdata.getString("ItemCategory"));
                                        DefaultFoodUID.add(Productdata.getString("ItemID"));
                                        DefaultTarget.add(Productdata.getString("ItemTarget"));
                                    }
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

            }).start();
        }
        catch(Exception ex){

        }

         */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ViewModel Logic


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void pulldata(String RequestType) {
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        emptyadapter emptygrid =new emptyadapter(this,getContext());
        if (RequestType.equals("All")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Defaultname, DefaultImg, Defaultdesc, Defaultcategory, DefaultFoodUID, bundler, Selecteditems,DefaultTarget, this,Sname,SImg,SFoodUID);
            if(Defaultname.size()== 0)
            {
              gridView.setAdapter(emptygrid);
            }else {
                gridView.setAdapter(Grid);
            }
        } else if (RequestType.equals("Breakfast")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Bname, BImg, Bdesc, Bcategory, BFoodUID, bundler, Selecteditems,BTarget, this,Sname,SImg,SFoodUID);
            if(Bname.size()== 0)
            {
                gridView.setAdapter(emptygrid);
            }else {
                gridView.setAdapter(Grid);
            }
        } else if (RequestType.equals("Lunch")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Lname, LImg, Ldesc, Lcategory, LFoodUID, bundler, Selecteditems,LTarget, this,Sname,SImg,SFoodUID);
            if(Lname.size()== 0)
            {
                gridView.setAdapter(emptygrid);
            }else {
                gridView.setAdapter(Grid);
            }
        } else if (RequestType.equals("Dinner")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Dname, DImg, Ddesc, Dcategory, DFoodUID, bundler, Selecteditems,DTarget, this,Sname,SImg,SFoodUID);
            if(Dname.size()== 0)
            {
                gridView.setAdapter(emptygrid);
            }else {
                gridView.setAdapter(Grid);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES-> Day();

            case Configuration.UI_MODE_NIGHT_NO -> Night();

            case Configuration.UI_MODE_NIGHT_UNDEFINED-> Day();
        }
        super.onStart();
        Button Breakfast = this.getActivity().findViewById(R.id.Breakfast);
        Button Lunch = this.getActivity().findViewById(R.id.lunch);
        Button Dinner = this.getActivity().findViewById(R.id.dinner);
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        Button All = this.getActivity().findViewById(R.id.all);
        OuterContainer = this.getActivity().findViewById(R.id.MainOuterContainer);
        HeadingContainer = this.getActivity().findViewById(R.id.HeaderContainer);
        TextView featured = this.getActivity().findViewById(R.id.ViewModelELEMENET);
        if(PreviouslyClicked == null) {
            All.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menubtn)));
            PreviouslyClicked = All;
        }
        else{
            All.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        }
        Datathatwassaved = new ViewModelProvider(this).get(SavedData.class);
        Datathatwassaved.TotalItems().observe(this, item -> {
            featured.setText(Integer.toString(item));
        });


                All.setOnClickListener((view) -> {
                    pulldata("All");
                    ActiveButton(All);

                });
        Breakfast.setOnClickListener((view) -> {
            pulldata("Breakfast");
            ActiveButton(Breakfast);
        });
        Lunch.setOnClickListener((view) -> {
            pulldata("Lunch");
            ActiveButton(Lunch);
        });
        Dinner.setOnClickListener((view) -> {
            pulldata("Dinner");
            ActiveButton(Dinner);
        });
    }
    private void Day(){

    }
    private void Night(){

    }
    private void ActiveButton(Button current)
    {
        if(PreviouslyClicked == null)
        {

        }else{
            PreviouslyClicked.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        }
        switch (current.getText().toString())
        {
            case "All":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menubtn)));
                CurrentClicked = "All";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
            case "Breakfast":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menubtn)));
                CurrentClicked = "Breakfast";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
            case "Lunch":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menubtn)));
               CurrentClicked = "Lunch";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
            case "Dinner":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menubtn)));
                CurrentClicked = "Dinner";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
        }
    }
    void cleardata(){
        Defaultname.clear();
        DefaultImg.clear();
        Defaultdesc.clear();
        Defaultcategory.clear();
        DefaultFoodUID.clear();
        DefaultTarget.clear();
        Bname.clear();
        BImg.clear();
        Bdesc.clear();
        Bcategory.clear();
        BFoodUID.clear();
        BTarget.clear();
        Lname.clear();
        LImg.clear();
        Ldesc.clear();
        Lcategory.clear();
        LFoodUID.clear();
        LTarget.clear();
        Dname.clear();
        DImg.clear();
        Ddesc.clear();
        Dcategory.clear();
        DFoodUID.clear();
        DTarget.clear();
    }
}
