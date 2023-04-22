package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class Auth extends AppCompatActivity {

    private TabLayout Layout;
    private ViewPager2 viewedpager;
    private NavViewAuther adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Layout = findViewById(R.id.tab_layout);
        viewedpager = findViewById(R.id.PagerContainer);
        Layout.addTab(Layout.newTab().setText("Login"));
        Layout.addTab(Layout.newTab().setText("Signup"));
        FragmentManager Fragement = getSupportFragmentManager();
        adapter = new NavViewAuther(Fragement,getLifecycle());
        viewedpager.setAdapter(adapter);
        Layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewedpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewedpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Layout.selectTab(Layout.getTabAt(position));
            }
        });
    }
}