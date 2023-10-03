package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class rating extends AppCompatActivity {
    RatingBar rat;
    Button btn,btn3;
    SharedPreferences sh;
    String ip, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        rat = findViewById(R.id.ratingBar);
        btn = findViewById(R.id.button5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final float rating=rat.getRating();
                final String rat=String.valueOf(rating);
                if(rat.length()==0.0){
                    Toast.makeText(rating.this,"Missing",Toast.LENGTH_SHORT).show();
                }
                else {

//        Toast.makeText(this, complaint+",",Toast.LENGTH_SHORT).show();
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip", "");
                    sh.getString("url", "");
                    url = sh.getString("url", "") + "ratte";


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(rating.this, "rating send", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_booking.class);
                                            startActivity(i);
                                        } else if (jsonObj.getString("status").equalsIgnoreCase("update")) {
                                            Toast.makeText(rating.this, "rating updated", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_booking.class);
                                            startActivity(i);
                                        } else if (jsonObj.getString("status").equalsIgnoreCase("null")) {
                                            Toast.makeText(rating.this, " rating is empty", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), rating.class);
                                            startActivity(i);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {

                        //                value Passing android to python
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("rate", rat);//passing to python
                            params.put("id", sh.getString("lid", ""));//passing to python
                            params.put("did", sh.getString("did", ""));//passing to python


                            return params;
                        }
                    };


                    int MY_SOCKET_TIMEOUT_MS = 100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);
                }



            }
        });


    }

}