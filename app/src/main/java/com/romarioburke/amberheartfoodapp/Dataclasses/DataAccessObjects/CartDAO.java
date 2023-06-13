package com.romarioburke.amberheartfoodapp.Dataclasses.DataAccessObjects;

import androidx.room.*;

import com.romarioburke.amberheartfoodapp.Dataclasses.CartModel;

import java.util.List;

public interface CartDAO  {

    @Query("Select * from cart where CartID = :CartID and MenuID = :MenuID ")
    public CartModel getCart(String CartID, String MenuID);
    List<CartModel>getCart();

    @Insert
    void insert(CartModel cartModel);

    @Update
    void update(CartModel cartModel);

    @Delete
    void delete(CartModel cartModel);

}
