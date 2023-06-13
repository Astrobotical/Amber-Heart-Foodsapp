package com.romarioburke.amberheartfoodapp.Dataclasses.Helpers;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;
import com.romarioburke.amberheartfoodapp.Dataclasses.DataAccessObjects.CartDAO;

@Database(entities = CartModel.class, version = 1, exportSchema = false)
public abstract class  DatabaseHelper extends RoomDatabase {
    private static DatabaseHelper instance;
    private static final String DBNAME = "CartDB";

    public static synchronized  DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DBNAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CartDAO cartDAO();
}
