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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText name,age,dob,phone,place,post,pin,email,pass;

    Button btn;
    RadioGroup rg;
    RadioButton male,female;
    ImageView imgp;
    Bitmap bitmap = null;
    ProgressDialog pd;
    String url = "";
    String gender="male";

    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url=sh.getString("url","")+"reggandroid";


        name=findViewById(R.id.editTextText3);
        age=findViewById(R.id.editTextNumber);
        dob=findViewById(R.id.editTextDate);
        phone=findViewById(R.id.editTextPhone);
        place=findViewById(R.id.editTextText4);
        post=findViewById(R.id.editTextText5);
        pin=findViewById(R.id.editTextText6);
        email=findViewById(R.id.editTextTextEmailAddress);
        pass=findViewById(R.id.editTextTextPassword5);
        rg=findViewById(R.id.radioGroup4);
        male=findViewById(R.id.radioButton);
        female=findViewById(R.id.radioButton2);
        imgp=findViewById(R.id.imageView2);
        btn = findViewById(R.id.button6);
        if (female.isChecked()) {
            gender="female";
        }



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
                String p=pass.getText().toString();
                String ag=age.getText().toString();
                String  d=dob.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[6-9][0-9]{9}";
                String PinPattern = "[6-9][0-9]{5}";
                if(nam.length()==0){
                    name.setError("Name is Missing");
                }
                else if (ag.length()==0) {
                    age.setError("Missing");

                }
                else if (d.length()==0) {
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
                    Toast.makeText(register.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                } else if (p.length()==0) {
                    pass.setError("Missing");

                }
                else {

                uploadBitmap(nam,emai,phon,plac,pos,pi,p,ag,d);
            }}
        });
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
    private void uploadBitmap(final String nam, final String emai, final String phon, final String plac, final String pos, final String pi, final String p, String ag, String d) {


        pd = new ProgressDialog(register.this);
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
                                Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                            }
                            if(obj.getString("status").equals("no")){
                                Toast.makeText(getApplicationContext(), "User Already exsist", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(getApplicationContext(), login.class);
//                                startActivity(i);
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
                params.put("p", p);
                params.put("a", ag);
                params.put("db", d);
                params.put("gender", gender);
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

