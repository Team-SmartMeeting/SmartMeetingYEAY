package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.MainLogic.DTO.user.UserDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DuringMeeting extends AppCompatActivity{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private String meetingID = getMeeting();
    private ArrayList<Topic> topicList;
    private int topicListNum;
    private int topicListCurNum = 0;
    private TextView topicDescription;
    private TextView topicTitle;
    private TextView topicTimer;
    private TextView topicTotalTimer;
    private TextView nexttopic;
    private LinearLayout llclock;
    //private String email;

    private int timerTRY;
    private int timerTRYTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_meeting);

        topicDescription = findViewById(R.id.topiccontent);
        topicTitle = findViewById(R.id.topictitle2);
        topicTimer = findViewById(R.id.clock);
        topicTotalTimer = findViewById(R.id.totaltimer);
        nexttopic = findViewById(R.id.nexttopic);
        llclock = findViewById(R.id.llclock);


        topicList = new ArrayList<>();
        Topic test = new Topic("Title Test","Test", 61);
        Topic test2 = new Topic("Title Test2","Test2", 62);
        topicList.add(test);
        topicList.add(test2);



        //Checker om der er en user logget på
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //if (user != null) {
        //    email = user.getEmail();
        //} else {finish();}

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Meetings").child(getMeeting());

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (int i = 0;i < 10;i++){
                    System.out.println("TEST" + i);

                }
                MeetingDTO post = dataSnapshot.getValue(MeetingDTO.class);

                //textName.setText(post.getName());

                if (post.getAgendalist() != null){
                    topicList = post.getAgendalist();
                }
                else {

                }
                topicListNum = topicList.size();
                System.out.println(topicListNum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        int totalTime = 0;

        for (int i = 0;i < topicListNum;i++){
            totalTime += getTopicTime(i);
        }

        timerTRY = getTopicTime(topicListCurNum);
        timerTRYTotal = totalTime;


        Thread t = new Thread(){
            @Override
            public void run(){
                while (!isInterrupted()){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timerTRY--;
                                timerTRYTotal--;
                                topicTimer.setText(toClock(timerTRY));
                                topicTotalTimer.setText(toClock(timerTRYTotal));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();



        topicTitle.setText(getTopicTitle(topicListCurNum));
        topicDescription.setText(getTopicDesciption(topicListCurNum));
        topicTimer.setText(toClock(getTopicTime(topicListCurNum)));
        nexttopic.setText(getTopicTitle(topicListCurNum + 1));
        llclock.setBackgroundColor(Color.GREEN);



//        topicTotalTimer.setText(toClock(totalTime));

        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicListCurNum++;

                if (topicListCurNum == topicListNum){
                    Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                    startActivity(intent);
                }
                else {
                    timerTRY = getTopicTime(topicListCurNum);
                    topicTitle.setText(getTopicTitle(topicListCurNum));
                    topicDescription.setText(getTopicDesciption(topicListCurNum));
                    topicTimer.setText(toClock(getTopicTime(topicListCurNum)));

                    if (topicListCurNum ==  topicListNum - 1){
                        nexttopic.setText("End of meeting");
                    }
                    else {
                        nexttopic.setText(getTopicTitle(topicListCurNum + 1));
                    }
                }
            }
        });
    }


    public String getMeeting(){
        return "-LyYzCNhXW7Dtre1fSjB";
    }

    public Topic getTopic(int listNum){
        return topicList.get(listNum);
    }

    public String getTopicTitle(int listNum){
        //String title = getTopic(listNum).getTopicName();

        ///ArrayList<String> topicTitle = new ArrayList<>();
        //topicTitle.add("Title 1");
        //topicTitle.add("Title 2");
        //topicTitle.add("Title 3");
        return getTopic(listNum).getTopicName();
    }

    public String getTopicDesciption(int listNum){
        ArrayList<String> topicDesciption = new ArrayList<>();
        topicDesciption.add("Description 1");
        topicDesciption.add("Description 2");
        topicDesciption.add("Description 3");
        return topicDesciption.get(listNum);
    }

    public int getTopicTime(int listNum){
        ArrayList<Integer> topicTime = new ArrayList<>();
        topicTime.add(3);
        topicTime.add(122);
        topicTime.add(183);
        return topicTime.get(listNum);
    }


    public String toClock(int totalTime){

        int minuts = totalTime / 60;

        int secunds = totalTime - minuts * 60;

        String clock;
        if (secunds < 10 && secunds > -10 ){
            clock = minuts + ":0" + secunds;
        }
        else {
            clock = minuts + ":" + secunds;
        }

        clock = clock.replace("-", "");
        if (totalTime < 0) {
            clock = "-" + clock;
        }

        return clock;
    }



}
