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
    private int totalTime = 0;
    private String meetingOwner;
    private int meetingTotalTime;
    private int topicTotalTime;
    private double allocate;
    private MeetingDTO post;
    private Button btnNext;
    private boolean firstLoad = true;

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
        btnNext = findViewById(R.id.btn_next);



        String id = getIntent().getStringExtra("meetingID");

        System.out.println(id);

        topicList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Meetings").child(id);


        meetingOwner = getIntent().getStringExtra("owner");

        //Tjekker om der er en user logget på
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
        email = email.replace(".", ",");



        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                post = dataSnapshot.getValue(MeetingDTO.class);

                post.setMeetingStatus(3);


                if (post.getAgendalist() != null){
                    topicList = post.getAgendalist();
                }
                else {

                }
                meetingTotalTime = post.getDuration();
                topicListNum = topicList.size();



                if (post.getAgendaStatus() == topicListNum) {
                    Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }

                if (post.getAgendaStatus() != topicListCurNum){
                    topicListCurNum = post.getAgendaStatus();
                }

                if (firstLoad){
                    timerTRYTotal = meetingTotalTime;
                    firstLoad = false;
                }


                load();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });





        //Denne tråd tæller sekunder, som bruges til at vise tiden i timeren
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


//        load();





        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email.equals(meetingOwner)){
                    topicListCurNum++;

                    mReference.child("agendaStatus").setValue(topicListCurNum);


                    if (topicListCurNum == topicListNum){
                        Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
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



    public void load(){

        if (topicListCurNum == topicListNum) {

        }
        else {

            Thread w = new Thread(){
                public void run(){
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBar progressBar = findViewById(R.id.progressBardm);
                                progressBar.setVisibility(View.VISIBLE);

                                if (!email.equals(meetingOwner)) {
                                    btnNext.setBackgroundResource(R.drawable.btn_new_meeting_drawable_disable);
                                    btnNext.setClickable(false);
                                    btnNext.setText("Wait");
                                }

                                topicTitle.setText(getTopicTitle(topicListCurNum));
                                topicDescription.setText(getTopicDesciption(topicListCurNum));
                                topicTimer.setText(toClock(getTopicTime(topicListCurNum)));

                                if (topicListCurNum + 1 ==  topicListNum){
                                    nexttopic.setText("End of meeting");
                                }
                                else {
                                    nexttopic.setText(getTopicTitle(topicListCurNum + 1));
                                }

                                llclock.setBackgroundColor(Color.GREEN);

                                for (int i = 0;i < topicListNum;i++){
                                    totalTime += getTopicTime(i);
                                }

                                topicTotalTime = totalTime;



                                allocateTime();

                                timerTRY = (int) Math.round((getTopicTime((topicListCurNum))* allocate));
                                topicTotalTimer.setText(toClock(timerTRYTotal));



                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            w.start();
        }
    }




    public void allocateTime(){

        System.out.println("mTT " + meetingTotalTime);
        System.out.println("tTT " + topicTotalTime);

        double mtt = meetingTotalTime;
        double ttt = topicTotalTime;

        allocate = (mtt / ttt);
        System.out.println("allo " + allocate);

    }





    public String getMeeting(){
        String id = getIntent().getStringExtra("meetingID");

        System.out.println(id);
        return id;
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
