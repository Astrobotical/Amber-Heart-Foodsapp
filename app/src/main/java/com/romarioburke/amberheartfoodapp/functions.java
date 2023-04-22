package com.romarioburke.amberheartfoodapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class functions implements ViewModelStoreOwner {
    SavedData Items;
    public void SetProduct(String A){
        Items = new ViewModelProvider(this).get(SavedData.class);
        Items.setData(A);
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return null;
    }
}
