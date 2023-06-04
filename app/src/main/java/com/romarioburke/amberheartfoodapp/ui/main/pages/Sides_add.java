package com.romarioburke.amberheartfoodapp.ui.main.pages;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sides_add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sides_add extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imagesaved;
    Bitmap Saved;
    public Sides_add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sides_add.
     */
    // TODO: Rename and change types and number of parameters
    public static Sides_add newInstance(String param1, String param2) {
        Sides_add fragment = new Sides_add();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cooks_sides_add, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        String[] FoodCatergoryArray = {"Select a Food Type", "Breakfast", "Lunch", "Dinner"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, FoodCatergoryArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditText Foodname = getActivity().findViewById(R.id.Fname);
        EditText FoodDescription = getActivity().findViewById(R.id.Fdescription);
        Spinner FoodCategory = getActivity().findViewById(R.id.FCategory);
        Button Addside = getActivity().findViewById(R.id.addside);
        Button photoupload = getActivity().findViewById(R.id.photoupload);
        imagesaved = getActivity().findViewById(R.id.Img);
        FoodCategory.setAdapter(spinnerArrayAdapter);
        photoupload.setOnClickListener((view)->{
            Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(select, 1);
        });
        Addside.setOnClickListener((view) -> {
            if (Foodname.getText().toString().equals("")) {
                Foodname.setError("Please enter a side item name");
            } else if (FoodDescription.getText().toString().equals("Please enter a description")) {
                FoodDescription.setError("Please enter a short description");
            } else if (FoodCategory.getSelectedItem().toString().equals("Select a Food Type")) {
                Toast.makeText(getContext(), "Please ensure you have selected a food category", Toast.LENGTH_LONG).show();
            }else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://api.romarioburke.com/api/v1/Items/Additem";
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(response);
                                    String Message = obj.optString("message");
                                    Toast.makeText(getContext(), Message, Toast.LENGTH_LONG).show();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cooks_main, new TotalItems()).commit();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                Saved.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                byte[] bytes=byteArrayOutputStream.toByteArray();
                                String image = "data:image/jpeg;base64,";
                                image += Base64.encodeToString(bytes, Base64.DEFAULT);
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Item_id", UUID.randomUUID().toString());
                                params.put("Item_name", Foodname.getText().toString());
                                params.put("Item_image", image);
                                params.put("Item_description",FoodDescription.getText().toString());
                                params.put("Item_category","Sides");
                                params.put("Item_Target","Omnivore");
                                params.put("Side_Target",FoodCategory.getSelectedItem().toString());
                                return params;
                            }
                        };
                        queue.add(request);
                    }
                });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imagesaved.setImageBitmap(bitmap);
                    Saved = bitmap;
                }
                //capture
            }
            break;
            case 1: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), imageUri);
                        imagesaved.setImageBitmap(bitmap);
                        Saved = bitmap;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }
}