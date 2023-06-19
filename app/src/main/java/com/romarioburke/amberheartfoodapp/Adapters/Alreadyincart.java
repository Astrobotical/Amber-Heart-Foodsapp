package com.romarioburke.amberheartfoodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.romarioburke.amberheartfoodapp.R;

public class Alreadyincart extends BaseAdapter {
    Fragment main;
    Context context;
    public Alreadyincart(Fragment frag,Context con) {
        this.main = frag;
        this.context = context;
    }


    @Override
    public int getCount() {
        return 1;
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
        views = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alreadyincart, viewGroup, false);
        // Toast.makeText(main.getContext(),"Nothing in this category at the moment",Toast.LENGTH_LONG).show();
        return views;
    }


}
