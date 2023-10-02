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

public class custom_view_prescription extends BaseAdapter {

    SharedPreferences sh;
    private Context context;
    String[] aid,dn,du,dos,rou,inst;

    public custom_view_prescription(Context applicationContext, String[] aid, String[] dn, String[] du, String[] dos, String[] rou, String[] inst) {
        this.context = applicationContext;
        this.aid = aid;
        this.dn = dn;
        this.du = du;
        this.dos = dos;
        this.rou = rou;
        this.inst = inst;


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
            gridView=inflator.inflate(R.layout.activity_custom_view_prescription,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView17);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView19);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView31);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView33);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView35);
//        btn.setTag(i);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos=(int)view.getTag();
//                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//                SharedPreferences.Editor ed = sh.edit();
//                ed.putString("aid", aid[pos]);
//                ed.commit();
//                Intent i = new Intent(context.getApplicationContext(), MainActivity2.class);
//                context.startActivity(i);
//
//
//
//            }
//        });
//        Button btn2 = (Button)gridView.findViewById(R.id.button3);




        tv1.setTextColor(Color.BLACK);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);




        tv1.setText(dn[i]);
        tv2.setText(du[i]);
        tv3.setText(dos[i]);
        tv4.setText(rou[i]);
        tv5.setText(inst[i]);
        return gridView;

    }

}