package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ip_page extends AppCompatActivity {

    EditText ip;
    Button btn;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_page);
        btn = findViewById(R.id.button11);
        ip=findViewById(R.id.editTextText2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip.setText(sh.getString("ip",""));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipaddress=ip.getText().toString();
//        Toast.makeText(this, ipaddress+",", Toast.LENGTH_SHORT).show();
                String url1 = "http://" + ipaddress + ":8000/";
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("ip",ipaddress);
                ed.putString("url",url1);
                ed.commit();
                Intent i=new Intent(getApplicationContext(),login.class);
                startActivity(i);

            }
        });
    }
}