package com.example.smartmeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    static LayoutInflater inflater = null;
    ArrayList<String> header;

    public CustomAdapter(Context context, ArrayList<String> header){

        this.context = context;
        this.header = header;

    }


    @Override
    public int getCount() {
        return header.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if(row == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.listview_contact_template , null);
        }

        TextView head = (TextView) row.findViewById(R.id.item_name);

        head.setText(header.get(position));

        return row;
    }
}
