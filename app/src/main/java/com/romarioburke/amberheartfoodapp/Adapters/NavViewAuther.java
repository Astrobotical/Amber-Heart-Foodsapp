package com.romarioburke.amberheartfoodapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.romarioburke.amberheartfoodapp.Authenticator.auth_login;
import com.romarioburke.amberheartfoodapp.Authenticator.auth_register;

public class NavViewAuther extends FragmentStateAdapter {
    public NavViewAuther(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1)
        {
            return new auth_register();
        }
        return new auth_login();
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
