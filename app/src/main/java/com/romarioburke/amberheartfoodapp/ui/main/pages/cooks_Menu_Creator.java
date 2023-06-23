package com.romarioburke.amberheartfoodapp.ui.main.pages;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.romarioburke.amberheartfoodapp.Adapters.ListedItemsAdapter;
import com.romarioburke.amberheartfoodapp.AsyncTasks.GetProducts;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cooks_Menu_Creator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cooks_Menu_Creator extends Fragment {

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
    public cooks_Menu_Creator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cooks_Menu_Creator.
     */
    // TODO: Rename and change types and number of parameters
    public static cooks_Menu_Creator newInstance(String param1, String param2) {
        cooks_Menu_Creator fragment = new cooks_Menu_Creator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
        return inflater.inflate(R.layout.cooks__menu__creator, container, false);
    }
    public void onStart()
    {
        super.onStart();
        Button Back = getActivity().findViewById(R.id.Cancel_request);
        ListView Breakfast = getActivity().findViewById(R.id.BreakfastList);
        ListView Lunch = getActivity().findViewById(R.id.LunchList);
        ListView Dinner = getActivity().findViewById(R.id.DinnerList);
        // on below line we are getting
        // the instance of our calendar.
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView Heading = getActivity().findViewById(R.id.mucHeading);
                        Heading.setText("Menu for " + dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                },
                year, month, day);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new cooks_Menu()).commit();
            }
        });
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
        //Log.i("PulledItem", Productdata.toString());
        ListedItemsAdapter Breakfastview = new ListedItemsAdapter(getActivity(), BImg,Bname, Bcategory, BFoodUID,Sname,SImg,SFoodUID,Scategory);
        ListedItemsAdapter Dinnerview = new ListedItemsAdapter(getActivity(), DImg, Dname, Dcategory, DFoodUID, Sname,SImg,SFoodUID,Scategory);
        ListedItemsAdapter Lunchview = new ListedItemsAdapter(getActivity(), LImg, Lname, Lcategory, LFoodUID, Sname,SImg,SFoodUID,Scategory);
                Lunch.setAdapter(Lunchview);
                Breakfast.setAdapter(Breakfastview);
                Dinner.setAdapter(Dinnerview);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new cooks_Menu()).commit();
            }
        });

    }
}