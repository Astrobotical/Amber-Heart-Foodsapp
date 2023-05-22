package com.romarioburke.amberheartfoodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.romarioburke.amberheartfoodapp.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CountryViewHolder>{

    ArrayList<String> FoodTarget = new ArrayList<>();
    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
    Fragment main;
    private Context context;

    public RecyclerAdapter(Context context,ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category, ArrayList<String>FoodUID,ArrayList<String>Target, Fragment trying) {
        this.context = context;
        this.FoodName = FoodName;
        this.FoodDescription = Description;
        this.FoodImage = FoodImage;
        this.FoodCategory = Category;
        this.FoodUID = FoodUID;
        this.FoodTarget = Target;
        this.main = trying;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        //holder.textViewName.setText(countryName.get(position));
       // holder.textViewDetails.setText(countryDetails.get(position));
       // holder.imageViews.setImageResource(imageList.get(position));

    }

    @Override
    public int getItemCount() {
        return FoodName.size();
    }


    public class CountryViewHolder extends RecyclerView.ViewHolder{
    private TextView textViewName,textViewDetails;
    private ImageView image;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);

        //textViewName=itemView.findViewById(R.id.textView);
        textViewDetails=itemView.findViewById(R.id.textView2);
        image= itemView.findViewById((R.id.imageView));
    }
}

}
