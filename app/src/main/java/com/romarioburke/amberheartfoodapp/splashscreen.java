package com.romarioburke.amberheartfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;

import com.romarioburke.amberheartfoodapp.Authenticator.Auth;

public class splashscreen extends AppCompatActivity {
    public enum WindowSizeClass { COMPACT, MEDIUM, EXPANDED }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
      /*  // ...

        // Replace with a known container that you can safely add a
        // view to where the view won't affect the layout and the view
        // won't be replaced.
        ViewGroup container = binding.container;

        // Add a utility view to the container to hook into
        // View.onConfigurationChanged. This is required for all
        // activities, even those that don't handle configuration
        // changes. You can't use Activity.onConfigurationChanged,
        // since there are situations where that won't be called when
        // the configuration changes. View.onConfigurationChanged is
        // called in those scenarios.
        container.addView(new View(this) {
            @Override
            protected void onConfigurationChanged(Configuration newConfig) {
                super.onConfigurationChanged(newConfig);
                computeWindowSizeClasses();
            }
        });
        computeWindowSizeClasses();
       */
        Thread splashscreenTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent loginscreen = new Intent(getApplicationContext(), Overview.class);
                    startActivity(loginscreen);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        splashscreenTimer.start();
    }
    private void computeWindowSizeClasses() {
     /*   WindowMetrics metrics = WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(this);

        float widthDp = metrics.getBounds().width() /
                getResources().getDisplayMetrics().density;
        WindowSizeClass widthWindowSizeClass;

        if (widthDp < 600f) {
            widthWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (widthDp < 840f) {
            widthWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            widthWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        float heightDp = metrics.getBounds().height() /
                getResources().getDisplayMetrics().density;
        WindowSizeClass heightWindowSizeClass;

        if (heightDp < 480f) {
            heightWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (heightDp < 900f) {
            heightWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            heightWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        // Use widthWindowSizeClass and heightWindowSizeClass.
    }

      */
    }
}