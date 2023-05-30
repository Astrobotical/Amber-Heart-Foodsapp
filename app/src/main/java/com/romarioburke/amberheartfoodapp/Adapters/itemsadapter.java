package com.romarioburke.amberheartfoodapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.SavedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class itemsadapter extends BaseAdapter {
    public itemsadapter(Context context, ArrayList<String> FoodName, ArrayList<String> FoodImage, ArrayList<String> Description, ArrayList<String> Category, ArrayList<String>FoodUID, Bundle bundler, HashMap<String, String> Selecteditem, ArrayList<String>Target, Fragment trying) {
       // this.context = context;
        this.FoodName = FoodName;
        this.FoodDescription = Description;
        this.FoodImage = FoodImage;
        this.FoodCategory = Category;
        this.FoodUID = FoodUID;
        this.bundle = bundler;
        this.Selecteditems = Selecteditem;
        this.FoodTarget = Target;
        this.main = trying;
    }
    ArrayList<String>FoodTarget = new ArrayList<>();
    ArrayList<String> FoodImage = new ArrayList<>();
    ArrayList<String> FoodName = new ArrayList<>();
    ArrayList<String> FoodDescription = new ArrayList<>();
    ArrayList<String> FoodCategory = new ArrayList<>();
    ArrayList<String>FoodUID = new ArrayList<>();
    HashMap<String, String> Selecteditems = new HashMap<String,String>();
    Bundle bundle;
    Fragment main;
    SavedData Actioncallor;

    @Override
    public int getCount() {
        return FoodName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View views;

            views = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitems, viewGroup, false);
            ImageView img = views.findViewById(R.id.Itemimage);
            TextView itemname = views.findViewById(R.id.Itemname);
            CardView cards = views.findViewById(R.id.card);
            Button Editbtn = views.findViewById(R.id.Edit);
            Button Deletebtn = views.findViewById(R.id.Delete);
            TextView CategoryElement = views.findViewById(R.id.category);
            RelativeLayout Container = views.findViewById(R.id.retainer);
            Deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    String Boldfoodname = FoodName.get(i);
                    SpannableString ss = new SpannableString(Boldfoodname);
                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                    ss.setSpan(boldSpan, 0, FoodName.get(i).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.setTitle("Delete  item");
                    builder.setMessage("Are you sure that you want to delete "+ss+" ?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            main.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String url = "https://api.romarioburke.com/api/v1/Items/Deleteitem";
                                    RequestQueue queue = Volley.newRequestQueue(main.getActivity());
                                    StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            JSONObject obj = null;
                                            try {
                                                obj = new JSONObject(response);
                                                String Message = obj.optString("message");
                                                Toast.makeText(main.getContext(), Message, Toast.LENGTH_LONG).show();
                                                main.getActivity().recreate();
                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }, new com.android.volley.Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(main.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                            byte[] bytes=byteArrayOutputStream.toByteArray();
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("Item_id", FoodUID.get(i));
                                            return params;
                                        }
                                    };
                                    queue.add(request);
                                }
                            });
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            Editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder prompt = new AlertDialog.Builder(view.getContext());
                            prompt.setView(R.layout.edititem);
                            AlertDialog alertDialog = prompt.create();
                            alertDialog.show();
                            ImageButton Exitbutton = alertDialog.findViewById(R.id.ExitEditbtn);
                            EditText Modalproductname = alertDialog.findViewById(R.id.editname);
                            EditText ModalDiscription = alertDialog.findViewById(R.id.editdescription);
                            ImageView Modalproductimage = alertDialog.findViewById(R.id.editimage);
                            Spinner ModalTarget = alertDialog.findViewById(R.id.edittarget);
                            Button edititembtn = alertDialog.findViewById(R.id.edititembtn);
                            edititembtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JSONArray arraybuilder = new JSONArray();
                                    arraybuilder.put("Name");
                                    Actioncallor = new ViewModelProvider(main).get(SavedData.class);
                                    Actioncallor.addItem(FoodName.get(i), arraybuilder);
                                    alertDialog.onBackPressed();
                                }
                            });
                            //ModalTarget.setText("Food Base Type - "+FoodTarget.get(i));
                            edititembtn.setText("Save the changes");
                            Modalproductname.setText(FoodName.get(i));
                            ModalDiscription.setText(FoodDescription.get(i));
                            String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(i);

                            Glide.with(views.getContext()).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(Modalproductimage);
                            Exitbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.onBackPressed();
                                }
                            });
                            alertDialog.getWindow().setBackgroundDrawable(getDrawableWithRadius());
                            // alertDialog.getWindow().setLayout(1000, 1900);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            }

                            //   Toast.makeText(context.getApplicationContext(), FoodName.get(i) + "- was clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            String Imagealtered = "https://api.romarioburke.com/"+FoodImage.get(i);
            Glide.with(views.getContext()).load(Imagealtered).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.loadingplaceholder).into(img);
         //   cards.setCardBackgroundColor(45322);
            itemname.setText(FoodName.get(i));
           // Toast.makeText(context.getApplicationContext(),Imagealtered,Toast.LENGTH_SHORT);
          //  CategoryElement.setText(FoodCategory.get(i));
            //counter++;

        return views;
    }
    private Drawable getDrawableWithRadius() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        return gradientDrawable;
    }

}
