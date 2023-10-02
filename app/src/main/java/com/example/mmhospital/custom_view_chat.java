package com.example.mmhospital;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class custom_view_chat extends BaseAdapter {
    String []  sendmsg,resmsg,cid,type,chat;
    SharedPreferences sh;
    String ip, url, lid;
    private Context context;

    public custom_view_chat(Context applicationContext, String[] cid, String[] sendmsg, String[] resmsg, String[] type, String[] chat) {
        this.context = applicationContext;
        this.cid = cid;
        this.sendmsg = sendmsg;
        this.resmsg = resmsg;
        this.type = type;
        this.chat = chat;

    }
    @Override
    public int getCount() {
        return sendmsg.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_chat,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
//        Toast.makeText(context, ""+type, Toast.LENGTH_SHORT).show();
        if(type[i].equalsIgnoreCase("patient") ){
            TextView tv1 = (TextView) gridView.findViewById(R.id.textView44);
            TextView tv2 = (TextView) gridView.findViewById(R.id.textView59);
            tv2.setTextColor(Color.WHITE);
            tv1.setVisibility(View.INVISIBLE);
            tv2.setText(chat[i]);

        }
        else if (type[i].equalsIgnoreCase("doctor")){
//            Toast.makeText(context, ""+chat, Toast.LENGTH_SHORT).show();
            TextView tv2 = (TextView) gridView.findViewById(R.id.textView59);
            TextView tv1 = (TextView) gridView.findViewById(R.id.textView44);
            tv1.setTextColor(Color.WHITE);
            tv2.setVisibility(View.INVISIBLE);
            tv1.setText(chat[i]);


        }


        return gridView;

    }
    private String splitMessage(String message) {
        int maxLength = 10; // Set the maximum length for each line
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i += maxLength) {
            int endIndex = Math.min(i + maxLength, message.length());
            sb.append(message.substring(i, endIndex));
            if (endIndex < message.length()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}