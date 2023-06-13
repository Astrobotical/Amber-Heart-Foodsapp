package com.romarioburke.amberheartfoodapp.Dataclasses;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.R;

public class Contenttest extends BroadcastReceiver {
    Activity CurrentActivity;
    MutableLiveData<Integer> cartcount = new MutableLiveData<>();
    public LiveData<Integer>cartsize(){
        return cartcount;
    }
    public Contenttest(Activity activity){
        CurrentActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BottomNavigationView navbar = CurrentActivity.findViewById(R.id.Navbar);
        BadgeDrawable badge = navbar.getOrCreateBadge(R.id.cart);
        badge.setVisible(true);
        badge.setNumber(cartcount.getValue());
    }
}
