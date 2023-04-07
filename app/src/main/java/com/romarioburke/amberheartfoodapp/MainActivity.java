package com.romarioburke.amberheartfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navbar = findViewById(R.id.Navbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.body,new Main()).commit();
        navbar.setSelectedItemId(R.id.nav_home);
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        frag = new Main();
                        break;
                    case R.id.currentmenu:
                        frag = new Products();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body, frag).commit();
                return true;
            }
        });
    }
}