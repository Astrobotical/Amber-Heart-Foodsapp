package com.romarioburke.amberheartfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.Dataclasses.Contenttest;
import com.romarioburke.amberheartfoodapp.Dataclasses.Repositories.Repository;
import com.romarioburke.amberheartfoodapp.studentviews.Main;
import com.romarioburke.amberheartfoodapp.studentviews.Products;
import com.romarioburke.amberheartfoodapp.studentviews.logout;
import com.romarioburke.amberheartfoodapp.studentviews.studentcart;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

public class   MainActivity extends AppCompatActivity {
    ProductsModel Productsmodel;
    int cartitem = 0;
    Contenttest Carti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Location = "";
        setContentView(R.layout.activity_main);
        Carti  = new Contenttest(this);
        BottomNavigationView navbar = findViewById(R.id.Navbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.body, new Main()).commit();
        navbar.setSelectedItemId(R.id.nav_home);
        BadgeDrawable badge = navbar.getOrCreateBadge(R.id.cart);


        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES-> Day();

            case Configuration.UI_MODE_NIGHT_NO -> Night();

            case Configuration.UI_MODE_NIGHT_UNDEFINED-> Day();
        }
       // navbar.getOrCreateBadge(R.id.cart).setNumber(90);

        Productsmodel = new ViewModelProvider(this).get(ProductsModel.class);
        Productsmodel.getCartitems().observe(this, cartitems -> {
          //  getSupportFragmentManager().beginTransaction().replace(R.id.body, new studentcart()).commit();
                   badge.setVisible(true);
                    badge.setNumber(cartitems);
                    Log.i("Cart","onCreate: " + cartitem);
                    getSupportFragmentManager().notifyAll();
        }
        );
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        frag = new Main();
                        break;
                    case R.id.currentmenu:
                        navbar.getOrCreateBadge(R.id.cart).setNumber(1);
                        frag = new Products();
                        break;
                    case R.id.Logout:
                        navbar.getOrCreateBadge(R.id.cart).setNumber(2);
                        frag = new logout();
                        break;
                    case R.id.cart:
                        navbar.getOrCreateBadge(R.id.cart).setNumber(3);
                        frag = new studentcart();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body, frag).commit();
                return true;
            }
        });

    }
    private void Day(){

    }
    private void Night(){

    }
    @Override
    public void onStart(){
        super.onStart();
        BottomNavigationView navbar = findViewById(R.id.Navbar);
        Productsmodel = new ViewModelProvider(this).get(ProductsModel.class);
        Productsmodel.getCartitems().observe(this, cartitems -> {
            navbar.getOrCreateBadge(R.id.cart).setNumber(cartitems);
            getSupportFragmentManager().notifyAll();
            navbar.notify();
            navbar.refreshDrawableState();
        });
        Repository.instance().addDataSource(Carti.cartsize());
        registerReceiver(Carti, IntentFilter.create("CartUpdate", "text/plain"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        // don't forget to remove receiver data source
        Repository.instance().removeDataSource(Carti.cartsize());
        unregisterReceiver(Carti);
    }
}
