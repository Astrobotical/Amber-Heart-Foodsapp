package com.romarioburke.amberheartfoodapp.studentviews;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.OptIn;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.romarioburke.amberheartfoodapp.Adapters.Alreadyincart;
import com.romarioburke.amberheartfoodapp.Adapters.emptyadapter;
import com.romarioburke.amberheartfoodapp.Adapters.griditems;
import com.romarioburke.amberheartfoodapp.AsyncTasks.GetProducts;
import com.romarioburke.amberheartfoodapp.Dataclasses.Helpers.DatabaseHelper;
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
    ArrayList<Float> DefaultRating = new ArrayList<>();


    ArrayList<String> BImg = new ArrayList<>();
    ArrayList<String> Bname = new ArrayList<>();
    ArrayList<String> Bdesc = new ArrayList<>();
    ArrayList<String> Bcategory = new ArrayList<>();
    ArrayList<String> BFoodUID = new ArrayList<>();
    ArrayList<String> BTarget = new ArrayList<>();
    ArrayList<Float> BRating = new ArrayList<>();

    ArrayList<String> LImg = new ArrayList<>();
    ArrayList<String> Lname = new ArrayList<>();
    ArrayList<String> Ldesc = new ArrayList<>();
    ArrayList<String> Lcategory = new ArrayList<>();
    ArrayList<String> LFoodUID = new ArrayList<>();
    ArrayList<String> LTarget = new ArrayList<>();
    ArrayList<Float> LRating = new ArrayList<>();
    ArrayList<String> DImg = new ArrayList<>();
    ArrayList<String> Dname = new ArrayList<>();
    ArrayList<String> Ddesc = new ArrayList<>();
    ArrayList<String> Dcategory = new ArrayList<>();
    ArrayList<String> DFoodUID = new ArrayList<>();
    ArrayList<String> DTarget = new ArrayList<>();
    ArrayList<Float> DRating = new ArrayList<>();

    ArrayList<String> SImg = new ArrayList<>();
    ArrayList<String> Sname = new ArrayList<>();
    ArrayList<String> Sdesc = new ArrayList<>();
    ArrayList<String> Scategory = new ArrayList<>();
    ArrayList<String> SFoodUID = new ArrayList<>();
    ArrayList<String> STarget = new ArrayList<>();
    ArrayList<String> BSImg = new ArrayList<>();
    ArrayList<String> BSname = new ArrayList<>();
    ArrayList<String> BSFoodUID = new ArrayList<>();
    ArrayList<String> BSCategory = new ArrayList<>();

    ArrayList<String> LSImg = new ArrayList<>();
    ArrayList<String> LSname = new ArrayList<>();
    ArrayList<String> LSFoodUID = new ArrayList<>();
    ArrayList<String> LSCategory = new ArrayList<>();
    ArrayList<String> DSImg = new ArrayList<>();
    ArrayList<String> DSname = new ArrayList<>();
    ArrayList<String> DSFoodUID = new ArrayList<>();
    ArrayList<String> DSCategory = new ArrayList<>();
    Bundle bundler;
    HashMap<String, String> Selecteditems = new HashMap<String, String>();
    CardView OuterContainer,HeadingContainer;
    Button PreviouslyClicked,currentbtn;
    String CurrentClicked;
    ProductsModel Datathatwassaved;
    DatabaseHelper helper;

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
                        DefaultRating.add(Float.parseFloat(Productdata.getString("ItemRating")));
                    }
                    if (Productdata.getString("ItemCategory").equals("Breakfast")) {
                        BImg.add(Productdata.getString("ItemImage"));
                        Bname.add(Productdata.getString("ItemName"));
                        Bdesc.add(Productdata.getString("ItemDescription"));
                        Bcategory.add(Productdata.getString("ItemCategory"));
                        BFoodUID.add(Productdata.getString("ItemID"));
                        BTarget.add(Productdata.getString("ItemTarget"));
                        BRating.add(Float.parseFloat(Productdata.getString("ItemRating")));
                    } else if (Productdata.getString("ItemCategory").equals("Lunch")) {
                        LImg.add(Productdata.getString("ItemImage"));
                        Lname.add(Productdata.getString("ItemName"));
                        Ldesc.add(Productdata.getString("ItemDescription"));
                        Lcategory.add(Productdata.getString("ItemCategory"));
                        LFoodUID.add(Productdata.getString("ItemID"));
                        LTarget.add(Productdata.getString("ItemTarget"));
                        LRating.add(Float.parseFloat(Productdata.getString("ItemRating")));
                    } else if (Productdata.getString("ItemCategory").equals("Dinner")) {
                        DImg.add(Productdata.getString("ItemImage"));
                        Dname.add(Productdata.getString("ItemName"));
                        Ddesc.add(Productdata.getString("ItemDescription"));
                        Dcategory.add(Productdata.getString("ItemCategory"));
                        DFoodUID.add(Productdata.getString("ItemID"));
                        DTarget.add(Productdata.getString("ItemTarget"));
                        DRating.add(Float.parseFloat(Productdata.getString("ItemRating")));
                    }
                    else if(Productdata.getString("ItemCategory").equals("Sides")) {
                        if(Productdata.getString("SideTarget").equals("Breakfast")){
                            BSImg.add(Productdata.getString("ItemImage"));
                            BSname.add(Productdata.getString("ItemName"));
                            BSFoodUID.add(Productdata.getString("ItemID"));
                            BSCategory.add(Productdata.getString("SideTarget"));
                        }else if(Productdata.getString("SideTarget").equals("Lunch")){
                            LSImg.add(Productdata.getString("ItemImage"));
                            LSname.add(Productdata.getString("ItemName"));
                            LSFoodUID.add(Productdata.getString("ItemID"));
                            LSCategory.add(Productdata.getString("SideTarget"));
                        }else if(Productdata.getString("SideTarget").equals("Dinner")){
                            DSImg.add(Productdata.getString("ItemImage"));
                            DSname.add(Productdata.getString("ItemName"));
                            DSFoodUID.add(Productdata.getString("ItemID"));
                            DSCategory.add(Productdata.getString("SideTarget"));
                        }
                        SImg.add(Productdata.getString("ItemImage"));
                        Sname.add(Productdata.getString("ItemName"));
                        Sdesc.add(Productdata.getString("ItemDescription"));
                        Scategory.add(Productdata.getString("ItemCategory"));
                        SFoodUID.add(Productdata.getString("ItemID"));
                        STarget.add(Productdata.getString("ItemTarget"));
                    }
                    Log.i("PulledItem", Productdata.toString());
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
        Alreadyincart incart = new Alreadyincart(this,getContext());
        helper =  DatabaseHelper.getInstance(getContext());
        SharedPreferences precheck = getActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
        Integer CartID = precheck.getInt("CartID", 0);
        if (RequestType.equals("All")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Defaultname, DefaultImg, Defaultdesc, Defaultcategory, DefaultFoodUID, bundler, Selecteditems,DefaultTarget, DRating,this,Sname,SImg,SFoodUID,Scategory,"A");
            if(Defaultname.size()== 0)
            {
              gridView.setAdapter(emptygrid);
            }else {
                gridView.setAdapter(Grid);
            }
        } else if (RequestType.equals("Breakfast")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Bname, BImg, Bdesc, Bcategory, BFoodUID, bundler, Selecteditems,BTarget, BRating,this,BSname,BSImg,BSFoodUID,BSCategory,"B");
            if(Bname.size()== 0)
            {
                gridView.setAdapter(emptygrid);
            }else {
               if(helper.cartDAO().checkCartItem("Breakfast",CartID.toString()))
               {gridView.setAdapter(incart);
               }else{
                     gridView.setAdapter(Grid);
               }
            }
        } else if (RequestType.equals("Lunch")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Lname, LImg, Ldesc, Lcategory, LFoodUID, bundler, Selecteditems,LTarget,LRating, this,LSname,LSImg,LSFoodUID,LSCategory,"L");
            if(Lname.size()== 0)
            {
                gridView.setAdapter(emptygrid);
            }else {
                if(helper.cartDAO().checkCartItem("Lunch",CartID.toString()))
                {gridView.setAdapter(incart);
                }else{
                    gridView.setAdapter(Grid);
                }
            }
        } else if (RequestType.equals("Dinner")) {
            griditems Grid = new griditems(this.getActivity().getApplicationContext(), Dname, DImg, Ddesc, Dcategory, DFoodUID, bundler, Selecteditems,DTarget,DRating,this,DSname,DSImg,DSFoodUID,DSCategory,"D");
            if(Dname.size()== 0)
            {
                gridView.setAdapter(emptygrid);
            }else {
                if(helper.cartDAO().checkCartItem("Dinner",CartID.toString()))
                {gridView.setAdapter(incart);
                }else{
                    gridView.setAdapter(Grid);
                }
            }
        }
    }

    @SuppressLint({"ResourceAsColor", "UnsafeOptInUsageError"})
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
        ToggleButton TOS = this.getActivity().findViewById(R.id.TOSToggler);
        if(PreviouslyClicked == null) {
            All.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menuitem)));
            PreviouslyClicked = All;
        }
        else{
            All.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        }
        Datathatwassaved = new ViewModelProvider(this).get(ProductsModel.class);

        TOS.setOnClickListener((view) -> {
            if(TOS.isChecked())
            {
                Toast.makeText(getContext(),"TOS is checked",Toast.LENGTH_SHORT).show();
                Datathatwassaved.setTOSTOGGLE(TOS.isChecked());
            }
            else{
                Toast.makeText(getContext(),"TOS is unchecked",Toast.LENGTH_SHORT).show();
            }
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
            PreviouslyClicked.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        }
        switch (current.getText().toString())
        {
            case "All":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menuitem)));
                CurrentClicked = "All";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
            case "Breakfast":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menuitem)));
                CurrentClicked = "Breakfast";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
            case "Lunch":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menuitem)));
               CurrentClicked = "Lunch";
                currentbtn = current;
                PreviouslyClicked = current;
                break;
            case "Dinner":
                current.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menuitem)));
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
