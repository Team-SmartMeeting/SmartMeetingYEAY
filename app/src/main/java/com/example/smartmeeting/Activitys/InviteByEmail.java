package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartmeeting.R;

public class InviteByEmail extends AppCompatActivity {

    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_by_email);
        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_meeting_menu);
        myAwesomeTextView.setText("Meetings");

        email = findViewById(R.id.edittext_email_to_inv);

        Button btn = findViewById(R.id.btn_invite_from_email_lastbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail2 = email.getText().toString().replace("\n", "");

                Intent intent = new Intent();
                intent.putExtra("nymail", mail2);
                setResult(RESULT_OK, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

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
}
