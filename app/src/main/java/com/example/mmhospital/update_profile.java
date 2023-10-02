package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class update_profile extends AppCompatActivity {
    EditText name,age,dob,phone,place,post,pin,email;

    Button btn;
    RadioGroup rg;
    RadioButton male,female;
    ImageView imgp;
    SharedPreferences sh;
    String ip, url, lid;
    String gender="male";
    Bitmap bitmap = null;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        name=findViewById(R.id.editTextText8);
        age=findViewById(R.id.editTextNumber2);
        dob=findViewById(R.id.editTextDate2);
        phone=findViewById(R.id.editTextPhone2);
        place=findViewById(R.id.editTextText10);
        post=findViewById(R.id.editTextText11);
        pin=findViewById(R.id.editTextNumber3);
        email=findViewById(R.id.editTextTextEmailAddress2);
        rg=findViewById(R.id.radioGroup2);
        male=findViewById(R.id.radioButton7);
        female=findViewById(R.id.radioButton6);
        imgp=findViewById(R.id.imageView3);
        btn = findViewById(R.id.button9);
        if (female.isChecked()) {
            gender="female";
        }
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("ip", "");
        url = "http://" + ip + ":8000/viewprof";
        lid = sh.getString("lid", "");
//
//        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        sh.getString("ip","");
//        url=sh.getString("url","")+"/updprof";
//
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                JSONObject jj = jsonObj.getJSONObject("data");

                                name.setText(jj.getString("p_name"));
                                age.setText(jj.getString("age"));
                                dob.setText(jj.getString("dob"));
//                                Toast.makeText(update_profile.this, jj.getString("gender"), Toast.LENGTH_SHORT).show();
                                if(jj.getString("gender").equalsIgnoreCase("female")){
                                    female.setChecked(true);
                                }
                                phone.setText(jj.getString("phone"));
                                place.setText(jj.getString("place"));
                                post.setText(jj.getString("post"));
                                pin.setText(jj.getString("pin"));

                                email.setText(jj.getString("email"));




                                String image = jj.getString("photo");
//                                Toast.makeText(update_profile.this, ""+image, Toast.LENGTH_SHORT).show();
                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip = sh.getString("ip", "");
                                String url = "http://" + ip + ":8000" + image;
                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(imgp);//circle

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

                params.put("login", lid);//passing to python

                return params;
            }
        };
        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("ip", "");
        url = "http://" + ip + ":8000/updprof";
        lid = sh.getString("lid", "");
//


//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + getPackageName()));
//            finish();
//            startActivity(intent);
//            return;
//        }
        imgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam=name.getText().toString();
                String emai=email.getText().toString();
                String phon=phone.getText().toString();
                String plac=place.getText().toString();
                String pos=post.getText().toString();
                String pi=pin.getText().toString();

                String ag=age.getText().toString();
                String  dd=dob.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[6-9][0-9]{9}";
                String PinPattern = "[6-9][0-9]{5}";

                if (female.isChecked()) {
                    gender="female";
                }
                if (male.isChecked()) {
                    gender="male";
                }


                if(nam.length()==0){
                    name.setError("Name is Missing");
                }
                else if (ag.length()==0) {
                    age.setError("Missing");

                }
                else if (dd.length()==0) {
                    dob.setError("Missing");

                }
                else if (!phon.matches(MobilePattern)) {
                    phone.setError("Invalid Phone Number");

                }
                else if (plac.length()==0) {
                    place.setError("Missing");

                }
                else if (pos.length()==0)
                {
                    post.setError("missing");
                } else if (!pi.matches(PinPattern)) {
                    pin.setError("Invalid Pin");

                }

                else if (!emai.matches(emailPattern)) {
                    Toast.makeText(update_profile.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }

                else {

                   if(bitmap == null){
                       Toast.makeText(update_profile.this, "Hlo", Toast.LENGTH_SHORT).show();
                       uploadBitmap2(nam, emai, phon, plac, pos, pi, ag, dd);

                   }
                   else {
                       Toast.makeText(update_profile.this, "2", Toast.LENGTH_SHORT).show();
                       uploadBitmap(nam, emai, phon, plac, pos, pi, ag, dd);
                   }
                }
            }
        });
    }

    private void uploadBitmap2(String nam, String emai, String phon, String plac, String pos, String pi, String ag, String dd) {
        pd = new ProgressDialog(update_profile.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                                startActivity(i);
                            } else if (obj.getString("status").equals("done")) {
                                Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                                startActivity(i);
                            } else{
//                                Toast.makeText(getApplicationContext(),"update failed" ,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("na", nam);//passing to python
                params.put("em", emai);//passing to python
                params.put("phon", phon);
                params.put("pla", plac);
                params.put("pos", pos);
                params.put("pin", pi);
                params.put("a", ag);
                params.put("do",dd);
                params.put("gender", gender);
                params.put("login", sh.getString("lid",""));//passing to python
                return params;
            }



        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);


}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                imgp.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final String nam, final String emai, final String phon, final String plac, final String pos, final String pi,final String ag,final String dd) {


        pd = new ProgressDialog(update_profile.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                                startActivity(i);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("na", nam);//passing to python
                params.put("em", emai);//passing to python
                params.put("phon", phon);
                params.put("pla", plac);
                params.put("pos", pos);
                params.put("pin", pi);

                params.put("a", ag);
                params.put("do",dd);
                params.put("gender", gender);
                params.put("login", sh.getString("lid",""));//passing to python
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}


