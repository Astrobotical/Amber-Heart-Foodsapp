package com.romarioburke.amberheartfoodapp.ui.main.pages;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.romarioburke.amberheartfoodapp.Dataclasses.AddingFood;
import com.romarioburke.amberheartfoodapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class food_add extends Fragment {
    public ImageView image;
    Bitmap Saved;
    private String Sentimage="";
    private JSONObject jsonObject;
    private JsonObjectRequest jsonObjectRequest;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ProgressDialog progressDialog;
    ActivityResultLauncher<PickVisualMediaRequest> launcher;
    AddingFood classobj = new AddingFood();

    public food_add() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 101);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_add, container, false);
    }
    private final boolean Choice = true;
    @Override
    public void onStart() {
        super.onStart();
        String[] TargetStudentArray = {"Select a Student Type", "Omnivore", "Herbivore", "Pescatarian"};
        String[] FoodCatergoryArray = {"Select a Food Type", "Breakfast", "Lunch", "Dinner"};
        Spinner CategoryList = getActivity().findViewById(R.id.Category_list);
        Spinner TargetedStudent = getActivity().findViewById(R.id.Options_StudentTarget);
        Button uploadphoto = getActivity().findViewById(R.id.uploadphoto);
        Button additem = getActivity().findViewById(R.id.addbtn);

        image = getActivity().findViewById(R.id.food_img);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Image Uploading...");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, FoodCatergoryArray); //selected item will look like a spinner set from XML
        ArrayAdapter<String> TargetStudentAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, TargetStudentArray);
        TargetStudentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TargetedStudent.setAdapter(TargetStudentAdapter);
        CategoryList.setAdapter(spinnerArrayAdapter);
        image = getActivity().findViewById(R.id.food_img);
        uploadphoto.setOnClickListener((view) -> {
            CheckPermission();
            Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(select, 1);
        });
        CategoryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (FoodCatergoryArray[i].equals("Select a food type")) {
                    Toast.makeText(getContext(), "Please select the type of food item", Toast.LENGTH_LONG).show();
                    // Toast.makeText(getContext(), "Please select the type of food item", Toast.LENGTH_LONG).show();
                    classobj.setChoice(false);
                } else {
                    classobj.setFoodCategory(FoodCatergoryArray[i]);
                    //ChoosenFoodCategory = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        TargetedStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (TargetStudentArray[position].equals("Select a student type")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please select a student group to target for this item", Toast.LENGTH_LONG).show();
                    classobj.setChoice(false);
                } else {

                    //ChoosenTargetedStudentGroup = parent.getItemAtPosition(position).toString();
                    classobj.setTargetedStudent(TargetStudentArray[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        EditText Foodname = getActivity().findViewById(R.id.food_name);
                        EditText Food_description = getActivity().findViewById(R.id.food_description);

                        if (Foodname.getText().toString().equals(" ")) {
                            Foodname.setError("Please ensure the Food name was entered.");
                            classobj.setChoice(false);
                        } else if (Food_description.getText().toString().equals(" ")) {
                            classobj.setChoice(false);
                            Food_description.setError("Please ensure the Food description was entered.");
                        }
                        classobj.setFoodName(Foodname.getText().toString());
                        classobj.setDescription(Food_description.getText().toString());
                        if (classobj.isChoice()) {
                            //progressDialog.show();
                            //UploadImage(Foodname.getText().toString(),"Ominvore",ChoosenFoodCategory,Food_description.getText().toString());
                            postData(classobj.getFoodName(), classobj.getTargetedStudent(), classobj.getFoodCategory(), classobj.getDescription());
                        }

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
                   image.setImageBitmap(bitmap);
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
                       image.setImageBitmap(bitmap);
                       Saved = bitmap;
                       //UploadImage(Foodname,bitmap,"Ominvore",ChoosenFoodCategory,Food_description);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
           break;
       }
   }
   //Requesting permission
   public boolean CheckPermission() {
       if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
           if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {new AlertDialog.Builder(getContext())
                       .setTitle("Permission")
                       .setMessage("Accept the permissions")
                       .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               //Prompt the user once explanation has been shown
                               ActivityCompat.requestPermissions(getActivity(),
                                       new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                       MY_PERMISSIONS_REQUEST_LOCATION);
                               startActivity(new Intent(getContext(), food_add.class));
                              getActivity().overridePendingTransition(0, 0);
                           }
                       })
                       .create()
                       .show();


           } else {
               ActivityCompat.requestPermissions(getActivity(),
                       new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                       MY_PERMISSIONS_REQUEST_LOCATION);
           }

           return false;
       } else {

           return true;
       }
   }
    private void postData(String Foodname,String Target,String FoodCategpry,String Food_Description) {
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
                       params.put("Item_name", Foodname);
                       params.put("Item_image", image);
                       params.put("Item_description",Food_Description);
                       params.put("Item_category",FoodCategpry);
                       params.put("Item_Target",Target);
                       return params;
                   }
               };
               queue.add(request);
        }
        });
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Sentimage = Base64.encodeToString(b, Base64.DEFAULT);
        return Sentimage;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}

