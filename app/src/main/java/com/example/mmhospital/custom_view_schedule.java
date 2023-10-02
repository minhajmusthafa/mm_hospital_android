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

public class custom_view_schedule extends BaseAdapter {
    String[] sid,da,dn,ca,st,et,tt;
    private Context context;
    SharedPreferences sh;
    String url;
    public custom_view_schedule(Context applicationContext, String[] sid, String[] da, String[] dn, String[] ca, String[] st, String[] et, String[] tt) {
        this.context = applicationContext;
        this.sid = sid;
        this.da = da;
        this.dn = dn;
        this.ca = ca;
        this.st = st;
        this.et = et;
        this.tt = tt;

    }

    public int getCount() {
        return sid.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_schedule,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView46);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView47);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView48);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView49);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView50);
        TextView tv6=(TextView)gridView.findViewById(R.id.textView51);

        Button btn = (Button)gridView.findViewById(R.id.button8);
        btn.setTag(i);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                sh.getString("ip","");
                sh.getString("url","");
                url=sh.getString("url","")+"bookappointmentand";
                int pos=(int) view.getTag();

                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context.getApplicationContext(), "booked", Toast.LENGTH_LONG).show();

//                                        Toast.makeText(view_schedule.this, "complaint send", Toast.LENGTH_SHORT).show();
                                        Intent i =new Intent(context.getApplicationContext(), view_schedule.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);

                                    }
                                    else if (jsonObj.getString("status").equalsIgnoreCase("no")) {
                                        Toast.makeText(context.getApplicationContext(), "Already exist", Toast.LENGTH_LONG).show();

////                                        Toast.makeText(view_schedule.this, "complaint send", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(context.getApplicationContext(), view_schedule.class);
//                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        context.startActivity(i);
                                    }
                                    else {
                                        Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("sid", sid[pos]);//passing to python
                        params.put("id", sh.getString("lid",""));//passing to python



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
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos=(int) view.getTag();
//                sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                sh.getString("ip","");
//                sh.getString("url","");
//                url=sh.getString("url","")+"/addcomplaint";
//
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                try {
//                                    JSONObject jsonObj = new JSONObject(response);
//                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                        Toast.makeText(Addcomplaint.this, "complaint send", Toast.LENGTH_SHORT).show();
//                                        Intent i =new Intent(getApplicationContext(),Addcomplaint.class);
//                                        startActivity(i);
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
//                                    }
//
//                                } catch (Exception e) {
//                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ) {
//
//                    //                value Passing android to python
//                    @Override
//                    protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        Map<String, String> params = new HashMap<String, String>();
//
//                        params.put("comp", complaint);//passing to python
//                        params.put("id", sh.getString("lid",""));//passing to python
//
//
//
//                        return params;
//                    }
//                };
//
//
//                int MY_SOCKET_TIMEOUT_MS = 100000;
//
//                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MY_SOCKET_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                requestQueue.add(postRequest);


                tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);






        tv1.setText(da[i]);
        tv2.setText(dn[i]);
        tv3.setText(ca[i]);
        tv4.setText(st[i]);
        tv5.setText(et[i]);
        tv6.setText(tt[i]);

        return gridView;

    }

}