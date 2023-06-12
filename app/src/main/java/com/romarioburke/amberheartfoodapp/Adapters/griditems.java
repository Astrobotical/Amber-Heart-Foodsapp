package com.romarioburke.amberheartfoodapp.Adapters;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
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


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.romarioburke.amberheartfoodapp.studentviews.Products;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.SavedData;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class griditems extends BaseAdapter {
    Context context;
    private Products product;
    int counter;
    public griditems(Context context, ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category,ArrayList<String>FoodUID, Bundle bundler,   HashMap<String, String> Selecteditem,ArrayList<String>Target,Fragment trying,ArrayList<String>Sname,ArrayList<String>Simage,ArrayList<String>SUID,ArrayList<String> SideCat) {
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
    }
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
    SavedData Actioncallor;

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
            Container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder prompt = new AlertDialog.Builder(view.getContext());
                            prompt.setView(R.layout.item_selector_order);
                            AlertDialog alertDialog = prompt.create();
                            alertDialog.show();
                            //Button Imagechanger = alertDialog.findViewById(R.id.changeimg);
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
                                }else{

                                }
                            }
                          /*  if( isPresent(SidesCategory,FoodCategory.get(i))){
                                adapter = new RecyclerAdapter(alertDialog.getContext(), Sname, SImg, SFoodUID, main, Sname, SImg, SFoodUID);
                                miniview.setAdapter(adapter);
                            } else {
                                miniview.setAdapter(emptyadapt);
                            }

                           */
                            ModalBtnAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JSONArray arraybuilder = new JSONArray();
                                    arraybuilder.put("Name");
                                    Actioncallor = new ViewModelProvider(main).get(SavedData.class);
                                    Actioncallor.addItem(FoodName.get(i), arraybuilder);
                                    alertDialog.onBackPressed();
                                }
                            });
                            ModalBtnAdd.setText("Choose " + FoodCategory.get(i) + " item");
                            Modalproductname.setText(FoodName.get(i));
                            ModalDiscription.setText(FoodDescription.get(i)+"\n"+"Food Base Type -"+FoodTarget.get(i));
                            String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(i);

                            Glide.with(views.getContext()).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(Modalproductimage);
                            ratingBar.setRating(1.5F);
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

}