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
    private String meetingOwner, id;
    private Button btnStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meeting);

        topicList = new ArrayList<>();
        listItems = new ArrayList<>();

        btnStart = findViewById(R.id.btn_start);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
        email = email.replace(".", ",");

        getMeeting();


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Meetings").child(id);

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
                if (post.getMeetingStatus() == 1){
                    Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                    intent.putExtra("meetingID", id);
                    intent.putExtra("owner", meetingOwner);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else if (post.getMeetingStatus() == 2){
                    Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                    intent.putExtra("meetingID", id);
                    intent.putExtra("owner", meetingOwner);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();

                }


                final TextView meetingTitle = findViewById(R.id.meetingTitle);
                String meetingTitleString;
                meetingTitleString = post.getMeetingName();
                meetingTitle.setText(meetingTitleString);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        topicListView = findViewById(R.id.listview_topics);
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listItems);



        load();




        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email.replace(".", ",");

                if (email.equals(meetingOwner)){

                    mReference.child("meetingStatus").setValue(1);

                    Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                    intent.putExtra("meetingID", id);
                    intent.putExtra("owner", meetingOwner);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                    Thread.sleep(300);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar progressBar = findViewById(R.id.progressBarsm);
                            progressBar.setVisibility(View.VISIBLE);

                            for (int i = 0;i < topicList.size();i++){
                                listItems.add(getTopicTitle(i));
                            }

                            if (!email.equals(meetingOwner)) {
                                btnStart.setBackgroundResource(R.drawable.btn_new_meeting_drawable_disable);
                                btnStart.setClickable(false);
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



    public void getMeeting(){
        id = getIntent().getStringExtra("meetingID");
        System.out.println(id);
    }

    public Topic getTopic(int listNum){
        return topicList.get(listNum);
    }

    public String getTopicTitle(int listNum){
        return getTopic(listNum).getTopicName();
    }
}
