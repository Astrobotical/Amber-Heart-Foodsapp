package com.romarioburke.amberheartfoodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.romarioburke.amberheartfoodapp.R;

import java.util.ArrayList;

public class ListedItemsAdapter extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;

    public ListedItemsAdapter(Context applicationContext, ArrayList<String> img, ArrayList<String> name, ArrayList<String> Category, ArrayList<String> UID,ArrayList<String>SName,ArrayList<String>Simage,ArrayList<String>SUID,ArrayList<String> SideCat) {
        this.context = applicationContext;
        this.FoodImage = img;
        this.FoodName = name;
        this.FoodCategory = Category;
        this.FoodUID = UID;
        this.SImg = Simage;
        this.Sname = SName;
        this.SFoodUID = SUID;
        this.SidesCategory = SideCat;

        inflter = (LayoutInflater.from(applicationContext));
    }
    ArrayList<String> SImg = new ArrayList<>();
    ArrayList<String> Sname = new ArrayList<>();
    ArrayList<String> SFoodUID = new ArrayList<>();
    ArrayList<String> SidesCategory = new ArrayList<>();

    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();

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
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.items, null);
        TextView foodname = (TextView) view.findViewById(R.id.foodname);
        ImageView foodimg = (ImageView) view.findViewById(R.id.foodimg);
        Button menubtn = (Button) view.findViewById(R.id.selectme);
        foodname.setText(FoodName.get(position));
        String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(position);
        Glide.with(context).load(Imagealtered).placeholder(R.drawable.loadingplaceholder).into(foodimg);
        return view;
    }

}