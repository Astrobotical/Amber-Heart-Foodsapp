package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class griditems extends BaseAdapter {
    Context context;

    public griditems(Context context, ArrayList<String> name, ArrayList<String> img, ArrayList<String> Desc) {
        this.context = context;
        this.name = name;
        this.desc = Desc;
        this.Img = img;
    }
    ArrayList<String> Img = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();

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
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View views;
        views = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_griditems,viewGroup,false);
        ImageView img = views.findViewById(R.id.imageView);
        TextView itemname = views.findViewById(R.id.Itemname);
        TextView item_description =  views.findViewById(R.id.Item_Description);
        CardView cards = views.findViewById(R.id.card);
       // cards.setCardElevation(5);
        cards.setCardBackgroundColor(45322);
        itemname.setText(name.get(i));
        item_description.setText(desc.get(i));
        img.setImageBitmap(getBitmapFromURL(Img.get(i)));

        return views;
    }
}