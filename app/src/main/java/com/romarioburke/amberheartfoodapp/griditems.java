package com.romarioburke.amberheartfoodapp;

import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class griditems extends BaseAdapter {
    Context context;
    int counter;
    public griditems(Context context, ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category,ArrayList<String>FoodUID) {

        this.context = context;
        this.FoodName = FoodName;
        this.FoodDescription = Description;
        this.FoodImage = FoodImage;
        this.FoodCategory = Category;
        this.FoodUID = FoodUID;
    }
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View views;
        views = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_griditems,viewGroup,false);
        ImageView img = views.findViewById(R.id.imageView);
        TextView itemname = views.findViewById(R.id.Itemname);
      //  TextView item_description =  views.findViewById(R.id.Item_Description);
        CardView cards = views.findViewById(R.id.card);
        TextView CategoryElement = views.findViewById(R.id.category);
        Button btnclicked = views.findViewById(R.id.add);
        btnclicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder prompt = new AlertDialog.Builder(view.getContext());
                prompt.setTitle(FoodName.get(i));
                prompt.setView(R.layout.item_selector_order);

                AlertDialog  alertDialog = prompt.create();

                alertDialog.show();
                ImageButton Exitbutton = (ImageButton) alertDialog.findViewById(R.id.Exitbutton);
                Exitbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(views.getContext(), "I worked? 123", Toast.LENGTH_SHORT).show();
                        alertDialog.onBackPressed();
                    }
                });

                alertDialog.getWindow().setLayout(1300, 2300);

                //alertDialog.setContentView(R.layout.item_selector_order);


                Toast.makeText(context.getApplicationContext(), FoodName.get(i)+"- was clicked",Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(views.getContext()).load(FoodImage.get(i)).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(img);
        cards.setCardBackgroundColor(45322);
        itemname.setText(FoodName.get(i));
       // item_description.setText(FoodDescription.get(i));
        CategoryElement.setText(FoodCategory.get(i));
        counter++;
        return views;
    }
}