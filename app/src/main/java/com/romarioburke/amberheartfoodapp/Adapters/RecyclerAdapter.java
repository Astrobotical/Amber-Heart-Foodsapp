package com.romarioburke.amberheartfoodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.romarioburke.amberheartfoodapp.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CountryViewHolder>{

    ArrayList<String> FoodTarget = new ArrayList<>();
    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String> SUID = new ArrayList<>();
    ArrayList<String> Sname = new ArrayList<>();
    ArrayList<String> SImg = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
    CardView CurrentCard;
    CheckBox CurrentCheckbox;
    CardView PreviousCard;
    CheckBox PreviousCheckbox;
    Fragment main;
    private Context context;

    public RecyclerAdapter(Context context,ArrayList<String> FoodName, ArrayList<String> FoodImage,ArrayList<String>UID, Fragment trying,ArrayList<String>Sname,ArrayList<String>Simage,ArrayList<String>SUID) {
        this.context = context;
        this.FoodName = FoodName;
        this.FoodImage = FoodImage;
        this.FoodUID = UID;
        this.main = trying;
        this.Sname = Sname;
        this.SImg = Simage;
        this.SUID = SUID;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sidesview,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        String Imagealtered = "https://api.romarioburke.com/"+SImg.get(position);
        Glide.with(context).load(Imagealtered).placeholder(R.drawable.loadingplaceholder).into(holder.sideimage);
        holder.sidename.setText(Sname.get(position));
        holder.Container.setOnClickListener((view)->{
            if(PreviousCard != null &&  PreviousCheckbox != null)
            {
                PreviousCard.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                PreviousCheckbox.setVisibility(View.INVISIBLE);
                PreviousCheckbox.setChecked(false);
                PreviousCheckbox = null;
                PreviousCard = null;
            }
                CurrentCard = holder.Container;
                CurrentCheckbox = holder.sidecheckbox;
                holder.Container.setCardBackgroundColor(context.getResources().getColor(R.color.teal_700));
                holder.sidecheckbox.setVisibility(View.VISIBLE);
                holder.sidecheckbox.setChecked(true);
                Toast.makeText(context, Sname.get(position)+"was clicked", Toast.LENGTH_SHORT).show();
                PreviousCard = CurrentCard;
                PreviousCheckbox = CurrentCheckbox;

        });


    }

    @Override
    public int getItemCount() {
        return SUID.size();
    }


    public class CountryViewHolder extends RecyclerView.ViewHolder{
    private TextView sidename;
    private ImageView sideimage;
    private CheckBox sidecheckbox;
    private CardView Container;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);
        sideimage = itemView.findViewById(R.id.sideimage);
        sidename = itemView.findViewById(R.id.sidename);
        sidecheckbox = itemView.findViewById(R.id.sideisselected);
        Container = itemView.findViewById(R.id.cardcontainer);
    }
}

}
