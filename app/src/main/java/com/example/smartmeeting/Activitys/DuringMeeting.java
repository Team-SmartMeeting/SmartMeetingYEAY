package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private String email;
    private int timerTRY;
    private int timerTRYTotal;
    int totalTime = 0;
    private String meetingOwner;

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


        //Checker om der er en user logget på
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {finish();}

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Meetings").child(getMeeting());

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MeetingDTO post = dataSnapshot.getValue(MeetingDTO.class);

                meetingOwner = post.getCreatingUser();

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


        Thread w = new Thread(){
            public void run(){
                try {
                    Thread.sleep(2000);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar progressBar = findViewById(R.id.progressBardm);
                            progressBar.setVisibility(View.VISIBLE);
                            topicTitle.setText(getTopicTitle(topicListCurNum));
                            topicDescription.setText(getTopicDesciption(topicListCurNum));
                            topicTimer.setText(toClock(getTopicTime(topicListCurNum)));
                            nexttopic.setText(getTopicTitle(topicListCurNum + 1));
                            llclock.setBackgroundColor(Color.GREEN);
                            timerTRY = getTopicTime(topicListCurNum);
                            for (int i = 0;i < topicListNum;i++){
                                totalTime += getTopicTime(i);
                            }
                            timerTRYTotal = totalTime;
                            topicTotalTimer.setText(toClock(totalTime));
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        w.start();




        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email.equals(meetingOwner)){
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

                        if (topicListCurNum + 1 ==  topicListNum){
                            nexttopic.setText("End of meeting");
                        }
                        else {
                            nexttopic.setText(getTopicTitle(topicListCurNum + 1));
                        }
                    }
                }
                else {

                }


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

    public String getTopicDesciption(int listNum){
        return getTopic(listNum).getDescription();
    }

    public int getTopicTime(int listNum){
        return getTopic(listNum).getTopicDuration();
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
