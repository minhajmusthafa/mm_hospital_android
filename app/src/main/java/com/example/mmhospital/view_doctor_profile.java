package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_doctor_profile extends AppCompatActivity {
    TextView co,dn,ca,did,sex,age;
    ImageView pho;
    SharedPreferences sh;
    String ip, url, lid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_profile);
        dn=findViewById(R.id.textView53);
        age=findViewById(R.id.textView54);
        sex=findViewById(R.id.textView55);
        ca=findViewById(R.id.textView56);
        co=findViewById(R.id.textView57);
        pho = findViewById(R.id.imageView4);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url=sh.getString("url","")+"viewdocprofand";
//        lid = sh.getString("lid", "");


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                JSONObject jj = jsonObj.getJSONObject("data");
//                                did.setText(jsonObj.getString("id"));
                                dn.setText(jsonObj.getString("d_name"));
                                age.setText(jsonObj.getString("age"));
                                sex.setText(jsonObj.getString("gender"));
                                ca.setText(jsonObj.getString("qualification"));
                                co.setText(jsonObj.getString("phone"));


                                String image = jsonObj.getString("photo");
                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip = sh.getString("ip", "");
                                String url = "http://" + ip + ":8000" + image;
                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(pho);//circle

                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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

                params.put("did", sh.getString("did",""));//passing to python


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