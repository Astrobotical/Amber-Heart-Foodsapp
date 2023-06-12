package com.romarioburke.amberheartfoodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.romarioburke.amberheartfoodapp.R;
public class emptysidesAdapter  extends RecyclerView.Adapter<emptyholder> {
    @NonNull
    @Override
    public emptyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.emptyside,parent,false);
        return new emptyholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull emptyholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
class emptyholder extends RecyclerView.ViewHolder{

    public emptyholder(@NonNull android.view.View itemView) {
        super(itemView);
    }
}
