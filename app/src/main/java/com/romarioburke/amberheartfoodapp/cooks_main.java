package com.romarioburke.amberheartfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.ui.main.CooksMainFragment;
import com.romarioburke.amberheartfoodapp.ui.main.pages.cooks_Home;
import com.romarioburke.amberheartfoodapp.ui.main.pages.cooks_Menu;

public class cooks_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooks_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, CooksMainFragment.newInstance()).commitNow();
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        BottomNavigationView navbar = findViewById(R.id.Navbar);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                switch (item.getItemId()) {
                    case R.id.cooks_home:
                        frag = new cooks_Home();
                        break;
                    case R.id.cooks_item_add:
                        frag = new cooks_Menu();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, frag).commit();
                return true;
            }
        });
    }
}