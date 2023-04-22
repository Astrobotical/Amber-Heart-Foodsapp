package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class griditems extends BaseAdapter {
    Context context;
    private Products product;
    int counter;
    public griditems(Context context, ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category,ArrayList<String>FoodUID, Bundle bundler,   HashMap<String, String> Selecteditem,Fragment trying) {
        this.context = context;
        this.FoodName = FoodName;
        this.FoodDescription = Description;
        this.FoodImage = FoodImage;
        this.FoodCategory = Category;
        this.FoodUID = FoodUID;
        this.bundle = bundler;
        this.Selecteditems = Selecteditem;
        this.main = trying;
    }
    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
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
        views = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_griditems,viewGroup,false);
        ImageView img = views.findViewById(R.id.modalimage);
        TextView itemname = views.findViewById(R.id.modalname);
        CardView cards = views.findViewById(R.id.card);
        TextView CategoryElement = views.findViewById(R.id.category);
        Button btnclicked = views.findViewById(R.id.add);
        btnclicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder prompt = new AlertDialog.Builder(view.getContext());
                        prompt.setView(R.layout.item_selector_order);
                        AlertDialog  alertDialog = prompt.create();
                        alertDialog.show();
                        ImageButton Exitbutton =  alertDialog.findViewById(R.id.Exitbutton);
                        TextView Modalproductname= alertDialog.findViewById(R.id.modalname);
                        RatingBar ratingBar =  alertDialog.findViewById(R.id.modalrating);
                        ImageView Modalproductimage =  alertDialog.findViewById(R.id.modalimage);
                        Button NormalLunch = alertDialog.findViewById(R.id.modalnormal);
                        Button LargeOnlyLunch =alertDialog.findViewById(R.id.modalLarge);
                        Button LargeComboLunch = alertDialog.findViewById(R.id.modalcombo);
                        TextView Label2 = alertDialog.findViewById(R.id.ModalLabelOP1);
                        Spinner Dropdowndrinks =  alertDialog.findViewById(R.id.ModalOPDropdown);
                        Button ModalBtnAdd =  alertDialog.findViewById(R.id.additembtn);
                        ModalBtnAdd .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                JSONArray arraybuilder = new JSONArray();
                                arraybuilder.put("Name");
                                Actioncallor = new ViewModelProvider(main).get(SavedData.class);
                                Actioncallor.addItem(FoodName.get(i),arraybuilder);
                                alertDialog.onBackPressed();
                            }
                        });
                        NormalLunch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                        LargeOnlyLunch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Label2.setVisibility(View.GONE);
                                Dropdowndrinks.setVisibility(View.GONE);
                            }
                        });
                        LargeComboLunch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Label2.setVisibility(View.VISIBLE);
                                Dropdowndrinks.setVisibility(View.VISIBLE);
                            }
                        });
                        ModalBtnAdd.setText("Choose "+FoodCategory.get(i)+" item");
                        Modalproductname.setText(FoodName.get(i));
                        Glide.with(views.getContext()).load(FoodImage.get(i)).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(Modalproductimage);
                        ratingBar.setRating(1.5F);
                        Exitbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.onBackPressed();
                            }
                        });
                        alertDialog.getWindow().setBackgroundDrawable(getDrawableWithRadius());
                        alertDialog.getWindow().setLayout(1400, 2300);
                        Toast.makeText(context.getApplicationContext(), FoodName.get(i)+"- was clicked",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Glide.with(views.getContext()).load(FoodImage.get(i)).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(img);
        cards.setCardBackgroundColor(45322);
        itemname.setText(FoodName.get(i));
        CategoryElement.setText(FoodCategory.get(i));
        counter++;

        return views;
    }
    private Drawable getDrawableWithRadius() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        return gradientDrawable;
    }

}