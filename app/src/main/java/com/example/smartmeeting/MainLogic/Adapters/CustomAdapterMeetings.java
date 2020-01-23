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

public class CustomAdapterMeetings extends BaseAdapter {

    Context context;
    static LayoutInflater inflater = null;
    ArrayList<String> titel;
    ArrayList<String> time;
    ArrayList<String> date;
    ArrayList<String> startTime;
    ArrayList<String> id;
    ArrayList<String> lokation;

    public CustomAdapterMeetings(Context context, ArrayList<String> titel, ArrayList<String> time, ArrayList<String> date, ArrayList<String> startTime, ArrayList<String> lokation, ArrayList<String> ids){

        this.context = context;
        this.titel = titel;
        this.time = time;
        this.date = date;
        this.startTime = startTime;
        this.lokation = lokation;
        this.id = ids;

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

            row = inflater.inflate(R.layout.listview_meeting_template, null);
        }

        TextView titlen = (TextView) row.findViewById(R.id.item_meeting_name);
        TextView duration = (TextView) row.findViewById(R.id.item_meeting_durationi);
        TextView beskrivelse = (TextView) row.findViewById(R.id.item_meeting_date);
        TextView startTimeTV = (TextView) row.findViewById(R.id.item_meeting_start_time);
        TextView lokationTV = (TextView) row.findViewById(R.id.item_location);

        titlen.setText(titel.get(position));
        duration.setText(time.get(position));
        beskrivelse.setText(date.get(position));
        startTimeTV.setText(startTime.get(position));
        lokationTV.setText(lokation.get(position));

        return row;
    }
}
