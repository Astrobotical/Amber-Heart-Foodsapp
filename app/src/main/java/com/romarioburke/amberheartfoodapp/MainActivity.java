package com.romarioburke.amberheartfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;
import com.romarioburke.amberheartfoodapp.Dataclasses.Contenttest;
import com.romarioburke.amberheartfoodapp.Dataclasses.Helpers.DatabaseHelper;
import com.romarioburke.amberheartfoodapp.Dataclasses.Repositories.Repository;
import com.romarioburke.amberheartfoodapp.studentviews.Category;
import com.romarioburke.amberheartfoodapp.studentviews.Main;
import com.romarioburke.amberheartfoodapp.studentviews.Products;
import com.romarioburke.amberheartfoodapp.studentviews.logout;
import com.romarioburke.amberheartfoodapp.studentviews.studentcart;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// TODO: 17/06/2023 Fix item counter for the navagation bar based on the items in storage vs the items recently clicked  
public class   MainActivity extends AppCompatActivity {
    ProductsModel Productsmodel;
    DatabaseHelper helper;
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
        IntentFilter filter = new IntentFilter("CartUpdate");
        registerReceiver(Carti,filter);
        SharedPreferences logs = getSharedPreferences("Carttotal", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = logs.edit();
        int Carttotal = logs.getInt("Carttotal", 0);
        if(Carttotal > 0){
            helper = DatabaseHelper.getInstance(this);
            SharedPreferences precheck = getSharedPreferences("Cart", Context.MODE_PRIVATE);
            Integer CartID = precheck.getInt("CartID", 0);
            String MenuID = precheck.getString("MenuID", "");
            ArrayList<CartModel> seting = (ArrayList<CartModel>)helper.cartDAO().getCartItems(MenuID, CartID.toString());
            Log.i("Cart", "onCreate: " + seting.size());
            int total = 0;
            for(int i = 0; i < seting.size(); i++){
                total ++;
            }
            if(total == 0){
                badge.setVisible(false);
            }
            else if(total > Carttotal){
                badge.setVisible(true);
                badge.setNumber(seting.size());
            }
            else if(total < Carttotal){
                myEdit.putInt("Carttotal", total);
                myEdit.apply();
                badge.setVisible(true);
                badge.setNumber(total);
            }
        }else{
            badge.setVisible(false);
        }
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES-> Day();

            case Configuration.UI_MODE_NIGHT_NO -> Night();

            case Configuration.UI_MODE_NIGHT_UNDEFINED-> Day();
        }
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        frag = new Main();
                        break;
                    case R.id.currentmenu:
                       // navbar.getOrCreateBadge(R.id.cart).setNumber(1);
                        frag = new Products();
                        break;
                    case R.id.Logout:
                        frag = new logout();
                        break;
                    case R.id.cart:
                        frag = new studentcart();
                        break;
                    case R.id.Catalog:
                        frag =  new Category();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        // don't forget to remove receiver data source
        unregisterReceiver(Carti);
    }
}
