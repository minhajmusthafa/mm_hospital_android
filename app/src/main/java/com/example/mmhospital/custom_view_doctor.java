package com.example.mmhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class custom_view_doctor extends BaseAdapter {
    String[] ra,dn,ca,did;

    ListView li;
    SharedPreferences sh;
    String ip, url, lid;
    private Context context;


    public custom_view_doctor(Context applicationContext, String[] did, String[] dn, String[] ra, String[] ca) {
        this.context = applicationContext;
        this.did = did;
        this.ra = ra;
        this.dn = dn;
        this.ca = ca;

    }
    public int getCount() {
    return did.length;
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
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_doctor,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView39);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView40);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView41);


        Button btn = (Button)gridView.findViewById(R.id.button2);
        btn.setTag(i);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("did", did[pos]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(), view_doctor_profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

        Button btn1 = (Button)gridView.findViewById(R.id.button7);
        btn1.setTag(i);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("did", did[pos]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(), view_chat.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

        tv1.setTextColor(Color.BLACK);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);



        tv1.setText(dn[i]);
        tv2.setText(ra[i]);
        tv3.setText(ca[i]);


        return gridView;

    }

}