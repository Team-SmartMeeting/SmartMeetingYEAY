package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartmeeting.R;


public class InviteToMeeting extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_meeting);
        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_meeting_menu);
        myAwesomeTextView.setText("Meetings");

        //SÆTTER TINGENE OP
        Button btn_contact = findViewById(R.id.btn_invite_from_contact);
        Button btn_emil = findViewById(R.id.btn_invite_from_email);


        //KNAPPER BLIVER SAT TIL
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(InviteToMeeting.this, InviteByEmail.class);
                startActivityForResult(in, 3);

            }
        });



        btn_emil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(InviteToMeeting.this, InviteByEmail.class);
                startActivityForResult(in, 4);


            }
        });




        //Menuen
        Button btn_profile = findViewById(R.id.btn_profile_menu);
        Button btn_meetings = findViewById(R.id.btn_meeting_menu);
        Button btn_contacts = findViewById(R.id.btn_contacts_menu);

        btn_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });


        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

    }


    //taget fra stackoverflow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3){
            if (resultCode == RESULT_OK){
                //Find en bruger igennem kontakten (brug måske email også her)
                String nytTopic = data.getStringExtra("nykontakt");

                Intent intent = new Intent();
                intent.putExtra("nymail2", nytTopic);
                setResult(RESULT_OK, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();


            }

        } else if (requestCode == 4){
            if (resultCode == RESULT_OK){
                //Find en bruger igennem emailen
                String nytTopic = data.getStringExtra("nymail");

                Intent intent = new Intent();
                intent.putExtra("nymail2", nytTopic);
                setResult(RESULT_OK, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();


            }

        }
    }

}
