package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_chat extends AppCompatActivity {
    ListView li;
    String []  sendmsg,resmsg,cid,type,chat;
    SharedPreferences sh;
    String ip, url, lid;
    EditText msg;
    ImageView btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chat);

        li = (ListView) findViewById(R.id.li);
        msg = findViewById(R.id.editTextText7);
        btn = findViewById(R.id.button3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip", "");
        url = sh.getString("url", "") + "chatandget";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python

                                cid = new String[js.length()];
                                sendmsg = new String[js.length()];
                                resmsg = new String[js.length()];
                                type = new String[js.length()];
                                chat = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);

                                    cid[i] = u.getString("id");//dbcolumn name in double quotes
                                    sendmsg[i] = u.getString("sender_id");
                                    resmsg[i] = u.getString("receiver_id");
                                    chat[i] = u.getString("chat");
                                    type[i] = u.getString("type");


                                }
                                li.setAdapter(new custom_view_chat(getApplicationContext(), cid, sendmsg, resmsg, type, chat));//custom_view_service.xml and li is the listview object


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
                params.put("id", sh.getString("lid", ""));//passing to python
                params.put("did", sh.getString("did",""));
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final float message= msg.getText().length();
                final String message = msg.getText().toString();
                if(message.length()==0){
                    Toast.makeText(view_chat.this,"Empty",Toast.LENGTH_SHORT).show();
                }
                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sh.getString("ip", "");
                sh.getString("url", "");
                url = sh.getString("url", "") + "chatandpost";


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                        Toast.makeText(view_chat.this, " send", Toast.LENGTH_SHORT).show();
                                        Intent i =new Intent(getApplicationContext(),view_chat.class);
                                        startActivity(i);
                                    } else {
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

                        params.put("msg", message);//passing to python
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
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i =new Intent(getApplicationContext(),view_doctor.class);
        startActivity(i);
    }
}