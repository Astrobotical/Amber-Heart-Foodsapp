package com.romarioburke.amberheartfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romarioburke.amberheartfoodapp.studentviews.Main;
import com.romarioburke.amberheartfoodapp.studentviews.Products;
import com.romarioburke.amberheartfoodapp.studentviews.logout;

public class   MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Location = "";
        setContentView(R.layout.activity_main);
        BottomNavigationView navbar = findViewById(R.id.Navbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.body, new Main()).commit();
        navbar.setSelectedItemId(R.id.nav_home);
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
                        frag = new Products();
                        break;
                    case R.id.Logout:
                        frag = new logout();
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
}
