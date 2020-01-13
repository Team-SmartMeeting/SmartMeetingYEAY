package com.example.smartmeeting.MainLogic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartmeeting.R;

import java.util.ArrayList;

/**
 * @author Søren Aarup Poulsen
 */

public class CustomAdapterAgenda extends BaseAdapter {

    Context context;
    static LayoutInflater inflater = null;
    ArrayList<String> titel;
    ArrayList<String> time;
    ArrayList<String> description;

    public CustomAdapterAgenda(Context context, ArrayList<String> titel, ArrayList<String> time, ArrayList<String> description){

        this.context = context;
        this.titel = titel;
        this.time = time;
        this.description = description;

    }


    @Override
    public int getCount() {
        return titel.size();
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

            row = inflater.inflate(R.layout.listview_topic_template, null);
        }

        TextView titlen = (TextView) row.findViewById(R.id.pop_topic_titel);
        TextView tiden = (TextView) row.findViewById(R.id.pop_topic_time);
        TextView beskrivelse = (TextView) row.findViewById(R.id.pop_topic_description);

        titlen.setText(titel.get(position));
        tiden.setText(time.get(position));
        beskrivelse.setText(description.get(position));

        return row;
    }
}
