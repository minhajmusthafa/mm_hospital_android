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

public class change_password extends AppCompatActivity {
    EditText curp,newp,rep;
    SharedPreferences sh;
    Button btn1;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        curp=findViewById(R.id.editTextTextPassword2);
        newp=findViewById(R.id.editTextTextPassword3);
        rep=findViewById(R.id.editTextTextPassword4);
        btn1 = findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String cp = curp.getText().toString();
            String np = newp.getText().toString();
            String rp = rep.getText().toString();
            String regex = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        + "(?=\\S+$).{8,20}$";
            if(!np.matches(regex)){
//                np.equalsIgnoreCase("invalid password");
                newp.setError("Invalid pattern");

            }
            else{
                sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sh.getString("ip","");
                url=sh.getString("url","")+"changepassand";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(change_password.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                        Intent i =new Intent(getApplicationContext(),MainActivity2.class);
                                        startActivity(i);
                                    }
                                    else if (jsonObj.getString("status").equalsIgnoreCase("em")) {
                                        Toast.makeText(change_password.this, " Password empty", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(getApplicationContext(), change_password.class);
//                                        startActivity(i);
                                    }

                                    else if (jsonObj.getString("status").equalsIgnoreCase("inc")) {
                                        Toast.makeText(change_password.this, " Password Not Matched", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(getApplicationContext(), change_password.class);
//                                        startActivity(i);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Incorrect Current Password", Toast.LENGTH_LONG).show();
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

                        params.put("cur", cp);                //passing to python
                        params.put("new", np);
                        params.put("rp", rp);

                        params.put("id", sh.getString("lid",""));//passing to python
//                        params.put("did", sh.getString("did",""));//passing to python


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