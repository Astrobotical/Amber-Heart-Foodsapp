package com.romarioburke.amberheartfoodapp;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringBufferInputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Products#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Products extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String []Itemnames = new String[3];
    String []Item_images =  new String[3];
    String []item_description = new String[3];
    public Products() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Products.
     */
    // TODO: Rename and change types and number of parameters
    public static Products newInstance(String param1, String param2) {
        Products fragment = new Products();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        ImageButton Breakfast = this.getActivity().findViewById(R.id.Breakfast);
        ImageButton Lunch = this.getActivity().findViewById(R.id.lunch);
        ImageButton Dinner = this.getActivity().findViewById(R.id.dinner);
        TextView Header = this.getActivity().findViewById(R.id.heading);
        GridView gridView = this.getActivity().findViewById(R.id.grids);
        ArrayList<String> Img = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> desc = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        String RequestURL = "https://api.romarioburke.com/api/v1/catalogs";
        /*
        Img.add("https://a.cdn-hotels.com/gdcs/production0/d1513/35c1c89e-408c-4449-9abe-f109068f40c0.jpg");
        name.add("Burger");
        desc.add("Burger King is an American-based multinational chain of hamburger fast food restaurants. Headquartered in Miami-Dade County, Florida, the company was founded in 1953 as Insta-Burger King ");
        Img.add("https://hips.hearstapps.com/hmg-prod/images/delish-230119-footballspinach-artichokedipbreadsticks-kj-9356-1674525329.jpg");
        name.add("Fish");
        desc.add("Fish Cake");
        Img.add("https://gardengrubblog.com/wp-content/uploads/2021/08/Untitled-design-2021-08-08T212255.120.jpg");
        name.add("Patty");
        desc.add("Patty description");
         */
        Breakfast.setOnClickListener((view)->{
           // Toast.makeText(getActivity(),"This many 123" ,Toast.LENGTH_SHORT).show();
            Header.setText("Breakfast");
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, RequestURL,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        Toast.makeText(getActivity().getApplicationContext(), "Response :" +response.toString(), Toast.LENGTH_LONG).show();
                        //JSONArray Array = Productdata.getJSONArray("data");
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject Productdata = response.getJSONObject(i);
                         //   String names = Productdata.getString("Item_name");
                            //Toast.makeText(getActivity().getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                            // Log.i("Butler", Array.getString("name"));
                            Toast.makeText(getActivity().getApplicationContext(), Productdata.getString("Item_name"), Toast.LENGTH_SHORT).show();
                            Img.add(Productdata.getString("Item_image"));
                            name.add(Productdata.getString("Item_name"));
                            desc.add(Productdata.getString("Item_description"));
                            category.add(Productdata.getString("Item_category"));
                        }

                        // }
                    } catch (JSONException EX) {
                        Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                        Log.i("CustomError", EX.toString());
                    }
                }
            },

                    new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("CustomError", error.toString());
                }
            });
            queue.add(request);
            griditems Grid = new griditems(this.getActivity().getApplicationContext(),name,Img,desc,category);
            gridView.setAdapter(Grid);
        });
        Lunch.setOnClickListener((view)->{
            Toast.makeText(getActivity(),"This works, it is lunch",Toast.LENGTH_SHORT).show();
            Header.setText("Lunch");
        });
        Dinner.setOnClickListener((view)->{
            Toast.makeText(getActivity(),"This works, it is Dinner",Toast.LENGTH_SHORT).show();
            Header.setText("Dinner");
        });
    }

}