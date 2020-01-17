package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class StartMeeting extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ArrayList<Topic> topicList;
    private ListView topicListView;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> arrayAdapter;
    private String email;
    private String meetingOwner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        topicList = new ArrayList<>();
        listItems = new ArrayList<>();



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

                if (post.getAgendalist() != null){
                    topicList = post.getAgendalist();
                }
                else {

                }
                if (post.getMeetingStatus() == 1){
                    Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                    startActivity(intent);
                    finish();
                }
                else if (post.getMeetingStatus() == 2){
                    Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                    startActivity(intent);
                    finish();

                }


                final TextView meetingTitle = findViewById(R.id.meetingTitle);
                String meetingTitleString;
                meetingTitleString = post.getMeetingName();
                meetingTitle.setText(meetingTitleString);
                meetingOwner = post.getCreatingUser();
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



        load();



        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email.replace(".", ",");

                if (email.equals(meetingOwner)){

                    mReference.child("meetingStatus").setValue(1);

                    Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                    startActivity(intent);
                    finish();
                }
                else {

                }
            }
        });
    }



    public void load (){
        Thread w = new Thread(){
            public void run(){
                try {
                    Thread.sleep(3000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar progressBar = findViewById(R.id.progressBarsm);
                            progressBar.setVisibility(View.VISIBLE);
                            for (int i = 0;i < topicList.size();i++){
                                listItems.add(getTopicTitle(i));
                            }
                            topicListView.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
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



    public String getMeeting(){
        return "-LynasKJ2n7g3d5srRI-";
    }

    public Topic getTopic(int listNum){
        return topicList.get(listNum);
    }

    public String getTopicTitle(int listNum){
        return getTopic(listNum).getTopicName();
    }
}
