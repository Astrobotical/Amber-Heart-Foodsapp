package com.romarioburke.amberheartfoodapp.Dataclasses.DataAccessObjects;

import android.util.Log;

import androidx.room.*;

import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface CartDAO  {

    @Query("Select * from Cart WHERE MenuID = :MenuID AND CartID = :CartID")
    List<CartModel> getCartItems(String MenuID, String CartID);

    @Query("Delete from Cart WHERE FoodID = :FoodID AND CartID = :CartID")
    Integer deleteCartItem(String FoodID, String CartID);

    @Query("Select exists(Select * from Cart WHERE FoodCategory =:FoodCategory AND CartID = :CartID)")
    boolean checkCartItem(String FoodCategory, String CartID);

    @Insert
    void insert(CartModel cartModel);

    @Update
    void update(CartModel cartModel);

    @Delete
    void delete(CartModel cartModel);

}
