package com.romarioburke.amberheartfoodapp.ui.main.pages;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
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
    private static final int PICK_IMAGE = 100;
    private static final int Imager= 1;
    private String Sentimage="";
    Uri imageUri;
    protected String Category;
    ActivityResultLauncher<Intent> launcher;
    protected String ChoosenTargetedStudentGroup,ChoosenFoodCategory,ChoosenCategory = "";
    public food_add() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},101);
        }
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getResultCode() == 1 ) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap photo = (Bitmap) bundle.get("data");
                    image.setImageBitmap(photo);
                }
                else{
                    if (result.getResultCode() == RESULT_OK && result.getResultCode() == PICK_IMAGE) {
                        imageUri = result.getData().getData();
                        image.setImageURI(imageUri);
                        String[] filePath = { MediaStore.Images.Media.DATA };
                        Cursor c = getActivity().getContentResolver().query(imageUri,filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                        thumbnail=getResizedBitmap(thumbnail, 400);
                        BitMapToString(thumbnail);
                    }
                }
            }
        });

     */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_add, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

       // String spinnerArray[] = {"Select a Category","0 - Normal Lunches only","1 - Normal lunches and Large Lunches"};
        String TargetStudentArray[]={"Select a Student Type","Omnivore","Herbivore","Pescatarian"};
        String FoodCatergoryArray[]={"Select a Food Type","Breakfast","Lunch","Dinner"};

        Spinner CategoryList = getActivity().findViewById(R.id.Category_list);
        Spinner TargetedStudent = getActivity().findViewById(R.id.Options_StudentTarget);
        Button uploadphoto = getActivity().findViewById(R.id.uploadphoto);
        Button takephoto = getActivity().findViewById(R.id.takephoto);
        Button additem = getActivity().findViewById(R.id.addbtn);
        EditText Foodname = getActivity().findViewById(R.id.food_name);
        EditText Food_description = getActivity().findViewById(R.id.food_description);
        image = getActivity().findViewById(R.id.food_img);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, FoodCatergoryArray); //selected item will look like a spinner set from XML
        ArrayAdapter<String> TargetStudentAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,TargetStudentArray);
        TargetStudentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TargetedStudent.setAdapter(TargetStudentAdapter);
        CategoryList.setAdapter(spinnerArrayAdapter);

        uploadphoto.setOnClickListener((view) -> {imageChooser();});
        takephoto.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View v) {/*
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager())!= null)
                {
                    launcher.launch(intent);
                }else{
                    Toast.makeText(getContext(), "No photo taking app present",Toast.LENGTH_SHORT).show();
                }
            }
            */
            }
        });
        additem.setOnClickListener((view)->{
            if(Foodname.getText().equals(" ")|| Food_description.getText().equals(" "))
            {
                Toast.makeText(getActivity().getApplicationContext(), "Please ensure the foodname and food description were entered.",Toast.LENGTH_SHORT).show();
            }
            CategoryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(FoodCatergoryArray[i].equals("Select a food type"))
                    {
                        Toast.makeText(getContext(),"Please select the type of food item", Toast.LENGTH_LONG).show();
                    }else{
                        ChoosenFoodCategory = FoodCatergoryArray[i];
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });
            TargetedStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(TargetStudentArray[position].equals("Select a student type"))
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Please select a student group to target for this item", Toast.LENGTH_LONG).show();
                    } else{
                        ChoosenTargetedStudentGroup = TargetStudentArray[position];
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            postData(Foodname.getText().toString(),Sentimage,ChoosenTargetedStudentGroup,ChoosenFoodCategory,Food_description.getText().toString());
        });
    }
    void imageChooser() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(
                Intent.createChooser(i, "Select an image"), PICK_IMAGE
        );
    }

    String currentPhotoPath;
   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                image.setImageURI(selectedImageUri);
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImageUri,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                BitMapToString(thumbnail);
            }
        }
    }
    private void postData(String Foodname,String Image,String Target,String FoodCategpry,String Food_Description) {
        String url = "https://api.romarioburke.com/api/v1/Items/Additem";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Item_id", UUID.randomUUID().toString());
                params.put("Item_name", Foodname);
                params.put("Item_image", Image);
                params.put("Item_description",Food_Description);
                params.put("Item_category",FoodCategpry);
                params.put("Item_Target",Target);
                return params;
            }
        };
        queue.add(request);
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

