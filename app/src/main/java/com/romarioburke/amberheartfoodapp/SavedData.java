package com.romarioburke.amberheartfoodapp;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

public class SavedData extends ViewModel {
    private HashMap<String,String> item;
    private final MediatorLiveData<Boolean> Errors = new MediatorLiveData<>();
    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();

    HashMap<String, JSONArray> Data =  new HashMap<>();
    //private final MutableLiveData<HashMap<String,String>> StoredItemObject = new MutableLiveData<>();
    private final MutableLiveData<HashMap<String, JSONArray>> Storeditems = new MutableLiveData<>();
    private final MediatorLiveData<Integer> counter = new MediatorLiveData<Integer>();
    private final MediatorLiveData<Bitmap> choosenimg = new MediatorLiveData<>();
    private int count = 0;
    public void Saveimage(Bitmap image){
        choosenimg.setValue(image);
    }
    public LiveData<Bitmap>getImage()
    {
        return choosenimg;
    }
    public MediatorLiveData<Bitmap> Imageet()
    {
        return choosenimg;
    }
    public void  setError()
    {
        Errors.setValue(false);
    }
    public void setErrorsuccess()
    {
        Errors.setValue(true);
    }
    public LiveData<Boolean> AreErrors(){
        return Errors;
    }
    public void increment(){
       count +=1;
        counter.setValue(count);
    }
    private  HashMap<String, String> StoredItems;
    private List<String> Cart;
   // private Mutable<>
    public void addItem(String ItemUID, JSONArray Array){
        Data.put(ItemUID,Array);
        Storeditems.setValue(Data);
        increment();
    }
    public LiveData<Integer> TotalItems() {
        return counter;
    }
    public void RemoveItem(){}
    public void RemoveItem(String ItemID) {
     StoredItems.remove(ItemID);
     //StoredItemObject.setValue(StoredItems);
        }
 public void additem2(String ItemID,JSONArray Array)
 {
     Data.put(ItemID,Array);
 }

 public LiveData<HashMap<String, JSONArray>> getArray(){
        return Storeditems;
 }
 public void setData(String item){
        selectedItem.setValue(item);
 }
 public LiveData<String> getSelectedItem(){
        return selectedItem;
 }
}

