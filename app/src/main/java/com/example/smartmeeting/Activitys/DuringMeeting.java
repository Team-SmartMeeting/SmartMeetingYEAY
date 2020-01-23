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

/**
 * @author Andreas Østergaard Schliemann
 */

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
    private int allocatedTimer;
    private ArrayList<Integer> timeList;
    private ArrayList<Boolean> activetopics;

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

        topicList = new ArrayList<>();
        timeList = new ArrayList<>();
        activetopics = new ArrayList<>();

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


        //Denne tråd lytter til databasen og aktivere nedenstående metode,
        //hver gang noget data, inden for det område den lytter til, ændre sig.
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                post = dataSnapshot.getValue(MeetingDTO.class);


                if (post.getAgendalist() != null){
                    topicList = post.getAgendalist();
                }

                meetingTotalTime = post.getDuration();
                topicListNum = topicList.size();

                //Sender brugeren over til EndMeeting, hvis alle punkterne på dagsordnen er blevet gennemgået
                if (post.getAgendaStatus() == topicListNum) {
                    Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }

                //Holder styr på hvilkt punkt på dagsordnen man er noget til
                if (post.getAgendaStatus() != topicListCurNum){
                    topicListCurNum = post.getAgendaStatus();
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

                                //Skifter timerens farve når tiden er ved at løbe ud
                                if (timerTRY == allocatedTimer / 10) {
                                    llclock.setBackgroundColor(Color.YELLOW);
                                }
                                if (timerTRY < 1){
                                    llclock.setBackgroundColor(Color.RED);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.equals(meetingOwner)){

                    topicListCurNum++;

                    mReference.child("agendaStatus").setValue(topicListCurNum);
                }
            }
        });
    }


    //Denne metode opdatere hele layoutet
    public void load(){

        if (firstLoad){

        }
        else {
            activetopics.set(topicListCurNum - 1, false);
            distributeTime();
        }

        //Men kun hvis man er gået vidre til et nyt punkt på dagsordnen
        if (!(topicListCurNum == topicListNum)) {
            ProgressBar progressBar = findViewById(R.id.progressBardm);
            progressBar.setVisibility(View.VISIBLE);

            //Det er kun den person, der har lavet mødet, meetingOwner, som kan gå vidre til næste punkt på dagsordnen
            if (!email.equals(meetingOwner)) {
                btnNext.setBackgroundResource(R.drawable.btn_new_meeting_drawable_disable);
                btnNext.setClickable(false);
                btnNext.setText("Wait");
            }

            topicTitle.setText(getTopicTitle(topicListCurNum));
            topicDescription.setText(getTopicDesciption(topicListCurNum));
            topicTimer.setText(toClock(getTopicTime(topicListCurNum)));
            llclock.setBackgroundColor(Color.GREEN);

            if (topicListCurNum + 1 ==  topicListNum){
                nexttopic.setText("End of meeting");
            }
            else {
                nexttopic.setText(getTopicTitle(topicListCurNum + 1));
            }

            //Dette stykke kode skal kun køres èn gang
            if (firstLoad){

                for (int i = 0;i < topicListNum;i++){
                    timeList.add(getTopicTime(i));
                    activetopics.add(true);
                    totalTime += getTopicTime(i);
                }

                topicTotalTime = totalTime;
                allocateTime();

                for (int i = 0;i < timeList.size();i++){
                    allocatedTimer = (int) ((timeList.get(i)* allocate));
                    timeList.set(i, allocatedTimer);
                }

                timerTRYTotal = meetingTotalTime;
                firstLoad = false;
            }

            timerTRY = timeList.get(topicListCurNum);
            topicTotalTimer.setText(toClock(timerTRYTotal));
            progressBar.setVisibility(View.GONE);
        }
    }




    //Allokerer tid til hvert punkt på dagsordnen proportionalt,
    //hvid den totale tid for alle punkter på dagsordnen er længere
    // eller kortere end tiden for hele mødet
    public void allocateTime(){

        double mtt = meetingTotalTime;
        double ttt = topicTotalTime;

        allocate = (mtt / ttt);
    }

    //Tager den overskydende tid fra det afsluttede punkt på dagsordnen og
    //fordeler det propotionalt ud over de resterende punkter
    public void distributeTime(){

        int remainingTime = timerTRY;
        int remainingTopicsTime = 0;

        for (int i = 0;i < activetopics.size();i++) {
                if (activetopics.get(i)) {
                remainingTopicsTime += timeList.get(i);
            }
        }

        double rtm = remainingTime;
        double rtp = remainingTopicsTime;
        double factor = rtm / rtp + 1;

        for (int i = 0;i < timeList.size();i++){
            if (activetopics.get(i)) {
                timeList.set(i, (int) Math.round(timeList.get(i) * factor));
            }
        }
    }




    public String getMeeting(){
        String id = getIntent().getStringExtra("meetingID");
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
