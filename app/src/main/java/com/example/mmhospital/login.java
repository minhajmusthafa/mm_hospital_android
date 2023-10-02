package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {
    EditText username,password;
    Button log,reg;
    SharedPreferences sh;
    String ip,url;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.editTextText);
        password=findViewById(R.id.password);
        log = findViewById(R.id.button);
        reg = findViewById(R.id.button10);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=username.getText().toString();
                String p = password.getText().toString();
                if(u.equalsIgnoreCase("")){
                    username.setError("required");
                    flag++;
                }
                if(flag==0) {


                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip", "");
                    url = sh.getString("url", "") + "loginnn";


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                Toast.makeText(Login.this, "welcome", Toast.LENGTH_SHORT).show();
                                            String typ = jsonObj.getString("type");
                                            String id = jsonObj.getString("lid");
                                            String username = jsonObj.getString("p_name");
                                            String email = jsonObj.getString("email");
                                            String image = jsonObj.getString("photo");
                                            SharedPreferences.Editor ed = sh.edit();
                                            ed.putString("lid", id);
                                            ed.putString("p_name",username);
                                            ed.putString("email",email);
                                            ed.putString("photo",image);
                                            ed.commit();
                                            if (typ.equalsIgnoreCase("Patient")) {
                                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {

                        //                value Passing android to python
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("u", u);//passing to python
                            params.put("p", p);


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

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),register.class);
                startActivity(i);

            }
        });
    }
}