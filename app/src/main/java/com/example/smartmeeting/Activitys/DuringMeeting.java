package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.smartmeeting.R;

import java.util.ArrayList;

public class DuringMeeting extends AppCompatActivity {

    String meetingID = getMeeting();
    ArrayList<String> topicList = getTopic(meetingID);
    int topicListNum = topicList.size();
    int topicListCurNum = 0;
    TextView topicDescription = findViewById(R.id.topiccontent);
    TextView topicTitle = findViewById(R.id.topictitle2);
    TextClock topicTimer = findViewById(R.id.clock);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_meeting);


        topicTitle.setText(getTopicTitle(meetingID, topicListCurNum));
        topicDescription.setText(getTopicDesciption(meetingID, topicListCurNum));
        topicTimer.setText(getTopicTime(meetingID, topicListCurNum));



        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicListCurNum++;

                if (topicListCurNum == topicListNum){

                }
                else {
                    topicTitle.setText(getTopicTitle(meetingID, topicListCurNum));
                    topicDescription.setText(getTopicDesciption(meetingID, topicListCurNum));
                    topicTimer.setText(getTopicTime(meetingID, topicListCurNum));
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

    public String getTopicTime(String meetingID, int listNum){
        ArrayList<String> topicTime = new ArrayList<>();
        topicTime.add("1:00");
        topicTime.add("2:00");
        topicTime.add("3:00");
        return topicTime.get(listNum);
    }


}
