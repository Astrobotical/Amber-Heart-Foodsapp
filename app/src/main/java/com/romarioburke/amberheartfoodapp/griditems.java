package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class griditems extends BaseAdapter {
    Context context;
    int counter;
    //RequestQueue Queue = Volley.newRequestQueue(context.getApplicationContext());
    String RequestURL = "localhost:8000/api/v1/catalogs";
    public griditems(Context context, ArrayList<String> name, ArrayList<String> img, ArrayList<String> Desc, ArrayList<String> Cat) {

        this.context = context;
        this.name = name;
        this.desc = Desc;
        this.Img = img;
        this.Category = Cat;
        //
    }
    ArrayList<String> Img = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    ArrayList<String> Category = new ArrayList<>();

    @Override
    public int getCount() {
        return name.size();
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
        ImageView img = views.findViewById(R.id.imageView);
        TextView itemname = views.findViewById(R.id.Itemname);
        TextView item_description =  views.findViewById(R.id.Item_Description);
        CardView cards = views.findViewById(R.id.card);
        TextView CategoryElement = views.findViewById(R.id.category);


        Glide.with(views.getContext()).load(Img.get(i)).centerCrop().placeholder(R.drawable.loadingplaceholder).into(img);
        cards.setCardBackgroundColor(45322);
        itemname.setText(name.get(i));
        item_description.setText(desc.get(i));
        CategoryElement.setText(Category.get(i));
        //img.setImageBitmap(getBitmapFromURL(Img.get(i)));
        counter++;
        return views;
    }

}