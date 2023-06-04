package com.romarioburke.amberheartfoodapp.AsyncTasks;

import android.app.IntentService;
import android.app.backup.BackupDataInputStream;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.SavedData;
import com.romarioburke.amberheartfoodapp.studentviews.Products;
import com.romarioburke.amberheartfoodapp.viewmodels.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetProducts implements  Runnable{
    private Context context;
    Fragment main;
    private JSONArray Product;
    public GetProducts(Context context, Fragment Frag){
        this.context = context;
        this.main = Frag;
    }
    @Override
    public void run() {
        String RequestURL = "https://api.romarioburke.com/api/v1/catalogs";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RequestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject result = new JSONObject(response.toString());
                   Product = result.getJSONArray("data");
                    Log.i("RESULTUID", Product.toString());
                    ProductsModel Viewmodel = new ViewModelProvider(main).get(ProductsModel.class);
                    Viewmodel.setProducts(Product);
                } catch (JSONException EX) {
                    //Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                    Log.i("CustomError", EX.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("CustomError", error.toString());
            }
        });
        queue.add(request);

    }
}
