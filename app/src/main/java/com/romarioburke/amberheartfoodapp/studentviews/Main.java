package com.romarioburke.amberheartfoodapp.studentviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.romarioburke.amberheartfoodapp.R;
import com.romarioburke.amberheartfoodapp.ui.main.pages.TotalItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ReviewManager reviewManager;
    ReviewInfo reviewInfo =null;

    public Main() {
        // Required empty public constructor
    }
    public static Main newInstance(String param1, String param2) {
        Main fragment = new Main();
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
        getReviewInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        reviewManager = ReviewManagerFactory.create(getContext());
        Bundle bundler = getActivity().getIntent().getExtras();
        SharedPreferences preferences = getActivity().getSharedPreferences("Featured", Context.MODE_PRIVATE);
        TextView Username = getActivity().findViewById(R.id.Username);
        Username.setText("Hi "+preferences.getString("Username","")+"!");
        EditText Feedback = getActivity().findViewById(R.id.Feedbackinfo);
        Button FeedbackSubmit = getActivity().findViewById(R.id.Submitfeedback);
        Button Review = getActivity().findViewById(R.id.Send_review);
        ImageView Featuredimg = getActivity().findViewById(R.id.Featuredimg);
        TextView Featuredname = getActivity().findViewById(R.id.Featuredtext);
        RatingBar Featuredrating = getActivity().findViewById(R.id.Featuredrating);
        String Imagealtered = "https://api.romarioburke.com/"+preferences.getString("Item_img","");
        Glide.with(getContext()).load(Imagealtered).placeholder(R.drawable.loadingplaceholder).into(Featuredimg);
        Featuredname.setText(preferences.getString("Item_name",""));
        Featuredrating.setRating(preferences.getFloat("Item_rating",0));
        Review.setOnClickListener((v)->{
            startReviewFlow();
        });
        Feedback.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        FeedbackSubmit.setOnClickListener((v)->{
            if(Feedback.getText().toString().equals("")){
                Feedback.setError("Please enter feedback");
            }else{
                String url = "https://api.romarioburke.com/api/v1/feeds/reportfeedback";
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            String Message = obj.optString("message");
                            Toast.makeText(getContext(), Message, Toast.LENGTH_LONG).show();
                            Feedback.setText("");

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
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("StudentID", bundler.getString("StudentID"));
                        params.put("Feedback", Feedback.getText().toString());
                        params.put("Type", "Complaints");
                        return params;
                    }
                };
                queue.add(request);
            }
        });
    }
    private void getReviewInfo() {
        reviewManager = ReviewManagerFactory.create(getContext());
        Task<ReviewInfo> manager = reviewManager.requestReviewFlow();
        manager.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            } else {
                Toast.makeText(getContext(), "In App ReviewFlow failed to start", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = reviewManager.launchReviewFlow(getActivity(), reviewInfo);
            flow.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    Toast.makeText(getContext(), "In App Rating has already been completed", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(getContext(), "In App Rating failed", Toast.LENGTH_LONG).show();
        }
    }
}