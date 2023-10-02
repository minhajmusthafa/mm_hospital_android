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
import android.widget.TextView;

public class custom_view_booking extends BaseAdapter {
    String[] aid,tn,dt,dn,sc,did;
    SharedPreferences sh;
    private Context context;

    public custom_view_booking(Context applicationContext, String[] aid, String[] tn, String[] dn, String[] dt, String[] sc,String[] did) {
        this.context = applicationContext;
        this.aid = aid;
        this.tn = tn;
        this.dt = dt;
        this.dn = dn;
        this.sc = sc;
        this.did = did;

    }

    @Override
    public int getCount() {
        return dn.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_booking,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView12);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView13);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView14);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView15);
        Button btn = (Button)gridView.findViewById(R.id.button19);
        btn.setTag(i);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("aid", aid[pos]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(), view_prescription.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);



            }
        });
        Button btn2 = (Button)gridView.findViewById(R.id.button20);

        btn2.setTag(i);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("did", did[pos]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(), rating.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });




        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        btn.setTextColor(Color.BLACK);
        btn2.setTextColor(Color.BLACK);



        tv1.setText(tn[i]);
        tv2.setText(dt[i]);
        tv3.setText(dn[i]);
        tv4.setText(sc[i]);
        return gridView;

    }

}