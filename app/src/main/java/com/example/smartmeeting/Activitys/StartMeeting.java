package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartmeeting.R;

import java.util.ArrayList;

public class StartMeeting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meeting);


        ListView topicList = findViewById(R.id.listview_topics);

        ArrayList<String> listItems=new ArrayList<String>();
        listItems.add("Topic 1");
        listItems.add("Topic 2");
        listItems.add("Topic 3");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listItems);

        topicList.setAdapter(arrayAdapter);

        arrayAdapter.notifyDataSetChanged();




        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                startActivity(intent);
            }
        });


    }


}
