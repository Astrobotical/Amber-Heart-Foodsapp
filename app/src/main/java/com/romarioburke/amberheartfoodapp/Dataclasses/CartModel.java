package com.romarioburke.amberheartfoodapp.Dataclasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartModel {
    @PrimaryKey(autoGenerate = true) private int AutoID;
    @ColumnInfo(name = "FoodID") private String FoodID;
    @ColumnInfo(name = "FoodName") private String FoodName;

    @ColumnInfo(name = "FoodCategory") private String FoodCategory;
    @ColumnInfo(name = "Foodimg") private String Foodimg;
    @ColumnInfo(name = "CartID") private String CartID;
    @ColumnInfo(name = "MenuID") private String MenuID;
    @ColumnInfo(name = "SideID") private String SideID;
    @ColumnInfo(name = "SideName") private String SideName;
    @ColumnInfo(name = "Sideimg") private String Sideimg;
    @ColumnInfo(name = "SideCategory") private String SideCategory;

    public CartModel(int autoID, String foodID, String foodName, String foodCategory, String foodimg, String cartID, String menuID, String sideID, String sideName, String sideimg, String sideCategory) {
        AutoID = autoID;
        FoodID = foodID;
        FoodName = foodName;
        FoodCategory = foodCategory;
        Foodimg = foodimg;
        CartID = cartID;
        MenuID = menuID;
        SideID = sideID;
        SideName = sideName;
        Sideimg = sideimg;
        SideCategory = sideCategory;
    }


    public int getAutoID() {
        return AutoID;
    }

    public void setAutoID(int autoID) {
        AutoID = autoID;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodCategory() {
        return FoodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        FoodCategory = foodCategory;
    }

    public String getFoodimg() {
        return Foodimg;
    }

    public void setFoodimg(String foodimg) {
        Foodimg = foodimg;
    }

    public String getCartID() {
        return CartID;
    }

    public void setCartID(String cartID) {
        CartID = cartID;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getSideID() {
        return SideID;
    }

    public void setSideID(String sideID) {
        SideID = sideID;
    }

    public String getSideName() {
        return SideName;
    }

    public void setSideName(String sideName) {
        SideName = sideName;
    }

    public String getSideimg() {
        return Sideimg;
    }

    public void setSideimg(String sideimg) {
        Sideimg = sideimg;
    }

    public String getSideCategory() {
        return SideCategory;
    }

    public void setSideCategory(String sideCategory) {
        SideCategory = sideCategory;
    }
}
