package com.romarioburke.amberheartfoodapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.romarioburke.amberheartfoodapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> FoodTarget = new ArrayList<>();
    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
    Fragment main;
    public DinnerAdapter(Context context, ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category, ArrayList<String>FoodUID,ArrayList<String>Target, Fragment trying) {
        this.context = context;
        this.FoodName = FoodName;
        this.FoodDescription = Description;
        this.FoodImage = FoodImage;
        this.FoodCategory = Category;
        this.FoodUID = FoodUID;
        this.FoodTarget = Target;
        this.main = trying;
    }

    @Override
    public int getCount() {
        return FoodName.size();
    }

    @Override
    public Object getItem(int i) {
        return FoodImage.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.listitems, parent, false);
        //ViewHolder holder;
     /*   if(convertView == null)
        {
            holder = new ViewHolder(convertView);
            String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(position);
            Glide.with(context).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(holder.Itemimage);
        }*/
       Log.i("Listitems",FoodCategory.toString());
        ImageView Item_image = convertView.findViewById(R.id.Itemimage);
        TextView Item_name = convertView.findViewById(R.id.Itemname);
        Button Edit_btn = convertView.findViewById(R.id.Edit);
        Button Delete_btn = convertView.findViewById(R.id.Delete);
        String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(position);
        //  Glide.with(view.getContext()).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(Item_image);
        Item_name.setText(FoodName.get(position));
        Edit_btn.setOnClickListener((v)->{
            Toast.makeText(context,FoodName.get(position)+" Item was clicked to be edited",Toast.LENGTH_LONG).show();
        });
        Delete_btn.setOnClickListener((v)->{
            Toast.makeText(context,FoodName.get(position)+" Item was clicked to be deleted",Toast.LENGTH_LONG).show();
        });
        return convertView;
    }
    /*
    private class ViewHolder {
        TextView ItemName;
        ImageView Itemimage;
        Button Edit_btn;
        Button Delete_btn;
        public ViewHolder(View view) {
            ItemName = (TextView) view.findViewById(R.id.Itemname);
            Itemimage = (ImageView) view.findViewById(R.id.Itemimage);
            Edit_btn = (Button) view.findViewById(R.id.Edit_btn);
            Delete_btn= (Button) view.findViewById(R.id.Delete_btn);

        }
    }
     */
}
