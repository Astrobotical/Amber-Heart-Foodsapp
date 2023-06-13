package com.romarioburke.amberheartfoodapp.Dataclasses.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class Repository {

    private static final Repository INSTANCE = new Repository();

    private final MediatorLiveData<Integer> mData = new MediatorLiveData<>();

    public Repository() {}

    public static Repository instance() {
        return INSTANCE;
    }

    public LiveData<Integer> getData() {
        return mData;
    }

    public void addDataSource(LiveData<Integer> data) {
        mData.addSource(data, mData::setValue);
    }

    public void removeDataSource(LiveData<Integer> data) {
        mData.removeSource(data);
    }
}
