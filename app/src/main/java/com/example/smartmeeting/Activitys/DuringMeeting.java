package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.smartmeeting.R;

import java.util.ArrayList;

public class DuringMeeting extends AppCompatActivity {

    String meetingID = getMeeting();
    ArrayList<String> topicList = getTopic(meetingID);
    int topicListNum = topicList.size();
    int topicListCurNum = 0;
    TextView topicDescription;
    TextView topicTitle;
    TextView topicTimer;
    TextView topicTotalTimer;
    TextView nexttopic;
    LinearLayout llclock;

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

        topicTitle.setText(getTopicTitle(meetingID, topicListCurNum));
        topicDescription.setText(getTopicDesciption(meetingID, topicListCurNum));
        topicTimer.setText(toClock(getTopicTime(meetingID, topicListCurNum)));
        nexttopic.setText(getTopicTitle(meetingID, topicListCurNum + 1));
        //llclock.setBackground("");

        int totalTime = 0;

        for (int i = 0;i < topicListNum;i++){
            totalTime += getTopicTime(meetingID, i);
        }



        topicTotalTimer.setText(toClock(totalTime));






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
                    topicTitle.setText(getTopicTitle(meetingID, topicListCurNum));
                    topicDescription.setText(getTopicDesciption(meetingID, topicListCurNum));
                    topicTimer.setText(toClock(getTopicTime(meetingID, topicListCurNum)));

                    if (topicListCurNum ==  topicListNum - 1){
                        nexttopic.setText("End of meeting");
                    }
                    else {
                        nexttopic.setText(getTopicTitle(meetingID, topicListCurNum + 1));
                    }
                }
            }
        });

    }


    public String getMeeting(){
        return "meetingID";
    }

    public ArrayList<String> getTopic(String meetingID){
        ArrayList<String> topics = new ArrayList<>();
        topics.add("TopicID1");
        topics.add("TopicID2");
        topics.add("TopicID3");
        return topics;
    }

    public String getTopicTitle(String meetingID, int listNum){
        ArrayList<String> topicTitle = new ArrayList<>();
        topicTitle.add("Title 1");
        topicTitle.add("Title 2");
        topicTitle.add("Title 3");
        return topicTitle.get(listNum);
    }

    public String getTopicDesciption(String meetingID, int listNum){
        ArrayList<String> topicDesciption = new ArrayList<>();
        topicDesciption.add("Description 1");
        topicDesciption.add("Description 2");
        topicDesciption.add("Description 3");
        return topicDesciption.get(listNum);
    }

    public int getTopicTime(String meetingID, int listNum){
        ArrayList<Integer> topicTime = new ArrayList<>();
        topicTime.add(71);
        topicTime.add(122);
        topicTime.add(183);
        return topicTime.get(listNum);
    }


    public String toClock(int totalTime){

        int minuts = totalTime / 60;

        int secunds = totalTime - minuts * 60;

        String clock;
        if (secunds < 10){
            clock = minuts + ":0" + secunds;
        }
        else {
            clock = minuts + ":" + secunds;
        }

        return clock;
    }


}
