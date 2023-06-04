package com.romarioburke.amberheartfoodapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ProductsModel extends ViewModel {
    MutableLiveData<HashMap<String, HashMap>>ProductData = new MutableLiveData<>();
    MutableLiveData<JSONArray>Products = new MutableLiveData<>();
    public void setProducts(JSONArray data){
        Products.setValue(data);
    }
    public LiveData<JSONArray> getProducts(){
        return Products;
    }
    void setProductData(String Categoryname,HashMap data){
        HashMap<String, HashMap> Data = new HashMap<>();
        switch(Categoryname){
            case"Breakfast":
                if(ProductData.getValue().containsKey("Breakfast")){
               if(ProductData.getValue().get("Breakfast").size() != data.size()){
                     ProductData.getValue().get("Breakfast").putAll(data);
                }}
            else{
                Data.put("Breakfast", data);
                ProductData.setValue(Data);
            }
                 break;
            case"Lunch":
                if(ProductData.getValue().containsKey("Lunch")) {
                    if (ProductData.getValue().get("Lunch").size() != data.size()) {
                        ProductData.getValue().get("Lunch").putAll(data);
                    }
                }
                else {
                    Data.put("Lunch", data);
                    ProductData.setValue(Data);
                }
                 break;
            case"Dinner":
                if(ProductData.getValue().containsKey("Dinner")) {
                    if (ProductData.getValue().get("Dinner").size() != data.size()) {
                        ProductData.getValue().get("Dinner").putAll(data);
                    }
                }
                else {
                    Data.put("Dinner", data);
                    ProductData.setValue(Data);
                    break;
                }
        }
    }
    public LiveData<HashMap<String, HashMap>> getProductData(){
        return ProductData;
    }

}
