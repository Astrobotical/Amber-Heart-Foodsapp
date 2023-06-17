package com.romarioburke.amberheartfoodapp.Adapters;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;
import com.romarioburke.amberheartfoodapp.Dataclasses.Helpers.DatabaseHelper;
import com.romarioburke.amberheartfoodapp.Dataclasses.Repositories.Repository;
import com.romarioburke.amberheartfoodapp.MainActivity;
import com.romarioburke.amberheartfoodapp.studentviews.Products;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.SavedData;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Locale;
import java.util.Random;
import java.util.TooManyListenersException;

public class griditems extends BaseAdapter {
    Context context;
    private Products product;
    int counter;
    public griditems(Context context, ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category,ArrayList<String>FoodUID, Bundle bundler,   HashMap<String, String> Selecteditem,ArrayList<String>Target,ArrayList<Float> rating,Fragment trying,ArrayList<String>Sname,ArrayList<String>Simage,ArrayList<String>SUID,ArrayList<String> SideCat) {
        this.context = context;
        this.FoodName = FoodName;
        this.FoodDescription = Description;
        this.FoodImage = FoodImage;
        this.FoodCategory = Category;
        this.FoodUID = FoodUID;
        this.bundle = bundler;
        this.Selecteditems = Selecteditem;
        this.FoodTarget = Target;
        this.main = trying;
        this.Sname = Sname;
        this.SImg = Simage;
        this.SFoodUID = SUID;
        this.SidesCategory = SideCat;
        this.FoodRating = rating;
        this.CurrentMenuID = getCart();
    }
    ArrayList<Float>FoodRating = new ArrayList<>();
    ArrayList<String> SidesCategory = new ArrayList<>();
    ArrayList<String>FoodTarget = new ArrayList<>();
    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
    ArrayList<String> SImg = new ArrayList<>();
    ArrayList<String> Sname = new ArrayList<>();
    ArrayList<String> SFoodUID = new ArrayList<>();
    HashMap<String, String> Selecteditems = new HashMap<String,String>();
    Bundle bundle;
    Fragment main;
    ProductsModel Modelviewer;
    DatabaseHelper DBhelper;
    String CurrentMenuID= "";

    TextToSpeech tts;

    @Override
    public int getCount() {
        return FoodName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View views;
        tts = new TextToSpeech(main.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
        Modelviewer = new ViewModelProvider(main).get(ProductsModel.class);
        if (FoodName.size() == 0) {
            views = LayoutInflater.from(view.getContext()).inflate(R.layout.emptyproductslayout, viewGroup, false);
            return views;
        } else {
            views = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_griditems, viewGroup, false);
            ImageView img = views.findViewById(R.id.Itemimage);
            TextView itemname = views.findViewById(R.id.modalname);
            CardView cards = views.findViewById(R.id.card);
            TextView CategoryElement = views.findViewById(R.id.category);
            RelativeLayout Container = views.findViewById(R.id.itemscontainer);
            RatingBar ratingBar = views.findViewById(R.id.Bars);
            Container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Modelviewer.getTOSTOGGLE().observe(main, item ->{
                                if(item == true)
                                {
                                    tts.speak(FoodName.get(i),TextToSpeech.SUCCESS,null);
                                }
                                else
                                {
                                    tts.stop();
                                }
                            });
                            AlertDialog.Builder prompt = new AlertDialog.Builder(view.getContext());
                            prompt.setView(R.layout.item_selector_order);
                            AlertDialog alertDialog = prompt.create();
                            alertDialog.show();
                            RecyclerView miniview = alertDialog.findViewById(R.id.miniview);
                            ImageButton Exitbutton = alertDialog.findViewById(R.id.Exitbutton);
                            TextView Modalproductname = alertDialog.findViewById(R.id.modalname);
                            TextView ModalDiscription = alertDialog.findViewById(R.id.modaldescription);
                            RatingBar ratingBar = alertDialog.findViewById(R.id.modalrating);
                            ImageView Modalproductimage = alertDialog.findViewById(R.id.Itemimage);
                            Button ModalBtnAdd = alertDialog.findViewById(R.id.additembtn);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(alertDialog.getContext(), LinearLayoutManager.HORIZONTAL, false);
                            emptysidesAdapter emptyadapt =  new emptysidesAdapter();
                            RecyclerAdapter adapter;
                            miniview.setLayoutManager(layoutManager);
                            for(int counter=0; counter < SidesCategory.size();counter++)
                            {
                                if(SidesCategory.get(counter).equals(FoodCategory.get(i)))
                                {
                                    adapter = new RecyclerAdapter(alertDialog.getContext(), Sname, SImg, SFoodUID, main, Sname, SImg, SFoodUID);
                                    miniview.setAdapter(adapter);
                                }else{}
                            }
                            ModalBtnAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JSONArray arraybuilder = new JSONArray();
                                    arraybuilder.put("Name");
                                    Modelviewer.additem();
                                    Repository rep = new Repository();
                                    rep.addDataSource(Modelviewer.getData());
                                    Intent intent = new Intent("CartUpdate");
                                    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                                    intent.putExtra("Action", "Add");
                                    main.getActivity().sendBroadcast(intent);
                                    AddtoCart(FoodUID.get(i), FoodName.get(i),FoodCategory.get(i),"https://api.romarioburke.com/"+FoodImage.get(i),"123",SFoodUID.get(i),Sname.get(i),"https://api.romarioburke.com/"+SImg.get(i),SidesCategory.get(i));
                                    alertDialog.onBackPressed();
                                }
                            });
                            ModalBtnAdd.setText("Choose " + FoodCategory.get(i) + " item");
                            Modalproductname.setText(FoodName.get(i));
                            ModalDiscription.setText(FoodDescription.get(i)+"\n"+"Food Base Type -"+FoodTarget.get(i));
                            String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(i);
                            Glide.with(views.getContext()).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(Modalproductimage);
                            ratingBar.setRating(FoodRating.get(i));
                            Exitbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.onBackPressed();
                                }
                            });
                            alertDialog.getWindow().setBackgroundDrawable(getDrawableWithRadius());
                           // alertDialog.getWindow().setLayout(1000, 1900);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            }
                            //   Toast.makeText(context.getApplicationContext(), FoodName.get(i) + "- was clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(i);
            Glide.with(views.getContext()).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(img);
            cards.setCardBackgroundColor(45322);
            itemname.setText(FoodName.get(i));
            ratingBar.setRating(FoodRating.get(i));
            Toast.makeText(context.getApplicationContext(),Imagealtered,Toast.LENGTH_SHORT);
            CategoryElement.setText(FoodCategory.get(i));
            counter++;

            return views;
        }

    }
    private boolean isPresent(ArrayList<String> array, String targetValue) {
        return Arrays.asList(array).contains(targetValue);
    }
    private Drawable getDrawableWithRadius() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        return gradientDrawable;
    }
    void AddtoCart(String FID, String Fname, String Fcategory, String Fimage,String MenuID,String SideID, String  SideName,String Sideimg,String SideCategory)
    {
        getCart();
        SharedPreferences precheck = main.getActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
        Integer CartID = precheck.getInt("CartID", 0);
        if(CartID == 0) {
            CreateCart();
        }else{
            getCart();
            DBhelper = DatabaseHelper.getInstance(main.getContext());
            DBhelper.cartDAO().insert(
                    new CartModel(
                            FID, Fname,
                            Fcategory, Fimage,
                           CartID.toString(), CurrentMenuID,
                            SideID, SideName,
                            Sideimg, SideCategory
                    )
            );
            Toast.makeText(main.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
        }

    }
    void CreateCart()
    {
        SharedPreferences logs = main.getActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = logs.edit();
        Random randomid = new Random();
        myEdit.putInt("CartID", randomid.hashCode());
        myEdit.apply();
    }
    String getCart()
    {
        String Menu;
        main.getActivity().runOnUiThread(new Runnable() {
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
                        SharedPreferences precheck = main.getActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
                        //ProductsModel Viewmodel = new ViewModelProvider(main).get(ProductsModel.class);
                        //Viewmodel.setMenuID(MenuID);
                       CurrentMenuID = MenuID;
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
    });
        return CurrentMenuID;
    }
}