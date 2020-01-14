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

public class CustomAdapterUserInvited extends BaseAdapter {

    Context context;
    static LayoutInflater inflater = null;
    ArrayList<String> emails;

    public CustomAdapterUserInvited(Context context, ArrayList<String> emails){

        this.context = context;
        this.emails = emails;

    }


    @Override
    public int getCount() {
        return emails.size();
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

            row = inflater.inflate(R.layout.listview_invited_template, null);
        }

        TextView titlen = (TextView) row.findViewById(R.id.listview_invited_users_element);

        titlen.setText(emails.get(position));

        return row;
    }
}
