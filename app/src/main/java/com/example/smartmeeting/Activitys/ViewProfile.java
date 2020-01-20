package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartmeeting.MainLogic.DTO.user.UserDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

/**
 * @author Simon Philipsen
 */
public class ViewProfile extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private String email;
    private TextView textName;
    private TextView textPhone;
    private TextView textEmail;
    private TextView textCompany;
    private TextView textAddress;
    private TextView textCity;
    private TextView textZipCode;
    private TextView textCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        textName = findViewById(R.id.name);
        textPhone = findViewById(R.id.phonenumber);
        textEmail = findViewById(R.id.email);
        textCompany = findViewById(R.id.company);
        textAddress = findViewById(R.id.address);
        textCity = findViewById(R.id.city);
        textZipCode = findViewById(R.id.zip_code);
        textCountry = findViewById(R.id.country);


        Button editProfile = findViewById(R.id.btn_big);
        editProfile.setText("Edit \n Profile");
        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_profile_menu);
        myAwesomeTextView.setText("Profile");


        //Checker om der er en user logget på
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Users").child(email.replace(".",",")).child("userinfo");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDTO post = dataSnapshot.getValue(UserDTO.class);


                textName.setText(post.getName());
                textEmail.setText(post.getEmail());
                textPhone.setText(post.getPhoneNumber());
                textCompany.setText(post.getCompany());
                textAddress.setText(post.getAddress());
                textCity.setText(post.getCity());
                textZipCode.setText(Integer.toString(post.getZipCode()));
                textCountry.setText(post.getCountry());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startIntent = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(startIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            });

        //Menuen
        Button btn_profile = findViewById(R.id.btn_profile_menu);
        Button btn_meetings = findViewById(R.id.btn_meeting_menu);
        Button btn_groupe = findViewById(R.id.btn_groupes_menu);
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

        btn_groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), groups_list.class);
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

        btn_profile.setBackgroundResource(R.drawable.button_pressed);

    }
}
