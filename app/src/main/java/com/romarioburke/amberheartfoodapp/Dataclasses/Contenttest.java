package com.romarioburke.amberheartfoodapp.Dataclasses;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.R;

public class Contenttest extends BroadcastReceiver {
    Activity CurrentActivity;
    public Contenttest(Activity activity){
        CurrentActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BottomNavigationView navbar = CurrentActivity.findViewById(R.id.Navbar);
        SharedPreferences logs = CurrentActivity.getSharedPreferences("Carttotal", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = logs.edit();
        SharedPreferences previous = CurrentActivity.getSharedPreferences("Carttotal", Context.MODE_PRIVATE);
        int Carttotal = previous.getInt("Carttotal", 0);
        Bundle bundler = intent.getExtras();
        String Action = bundler.getString("Action");
        BadgeDrawable badge = navbar.getOrCreateBadge(R.id.cart);

        switch(Action){
            case "Add":
                myEdit.putInt("Carttotal",Carttotal+1);
                badge.setVisible(true);
                badge.setNumber(Carttotal+1);
                myEdit.apply();
                break;
            case "Remove":
                myEdit.putInt("Carttotal",Carttotal-1);
                badge.setVisible(true);
                badge.setNumber(Carttotal-1);
                myEdit.apply();
                break;
            case "Clear":
                myEdit.putInt("Carttotal", 0);
                badge.setVisible(false);
                badge.setNumber(0);
                myEdit.apply();
                break;
        }
    }
}
