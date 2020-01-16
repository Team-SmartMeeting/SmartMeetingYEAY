package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartMeeting extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ArrayList<Topic> topicList;
    private TextView meetingTitle;
    private ListView topicListView;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> arrayAdapter;
    //private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        topicList = new ArrayList<>();
        listItems = new ArrayList<>();
        //listItems.add("Test");
        meetingTitle = findViewById(R.id.meetingTitle);
        //progressBar = findViewById(R.id.progressBarsm);


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Meetings").child(getMeeting());

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MeetingDTO post = dataSnapshot.getValue(MeetingDTO.class);


                if (post.getAgendalist() != null){
                    topicList = post.getAgendalist();
                    //meetingTitle.setText(post.getMeetingName());
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meeting);


        topicListView = findViewById(R.id.listview_topics);
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listItems);


        Thread w = new Thread(){
            public void run(){
                try {
                    Thread.sleep(2500);
                    //progressBar.setVisibility(View.VISIBLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0;i < topicList.size();i++){
                                listItems.add(getTopicTitle(i));
                            }

                            topicListView.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                            //listItems.add("t");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //progressBar.setVisibility(View.GONE);
            }
        };
        w.start();


        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                startActivity(intent);
            }
        });
    }


    public String getMeeting(){
        return "-LyhmpETDbxVJQ04N21w";
    }

    public Topic getTopic(int listNum){
        return topicList.get(listNum);
    }

    public String getTopicTitle(int listNum){
        return getTopic(listNum).getTopicName();
    }
}
