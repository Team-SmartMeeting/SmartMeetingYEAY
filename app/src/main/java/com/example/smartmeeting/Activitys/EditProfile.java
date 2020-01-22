package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;


/**
 * @author Simon Philipsen
 */

public class EditProfile extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    DatabaseReference ref;
    FirebaseDatabase database;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_profile_menu);
        myAwesomeTextView.setText("Profile");

        //Firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        final TextView textName =  findViewById(R.id.name);
        final TextView textPhone =  findViewById(R.id.phonenumber);
        final TextView textEmail =  findViewById(R.id.email);
        final TextView textCompany =  findViewById(R.id.company);
        final TextView textAddress =  findViewById(R.id.address);
        final TextView textCity =     findViewById(R.id.city);
        final TextView textZipCode =  findViewById(R.id.zip_code);
        final TextView textCountry =  findViewById(R.id.country);


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Users").child(email.replace(".",",")).child("userinfo");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDTO post = dataSnapshot.getValue(UserDTO.class);

                textName.setText(post.getName());
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



        Button editProfile = findViewById(R.id.btn_big);
        editProfile.setText("Save \n Changes");
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checker om felter er tommme!
                if (!textName.getText().toString().equals("") && !textPhone.getText().toString().equals("") && !textCompany.getText().toString().equals("") && !textAddress.getText().toString().equals("")&& !textCity.getText().toString().equals("") && !textZipCode.getText().toString().equals("") && !textCountry.getText().toString().equals("")) {


                    //opretter en Profil
                    UserDTO profile = new UserDTO(textName.getText().toString(), String.valueOf(user.getEmail()).replace(".",","),textPhone.getText().toString(), textCompany.getText().toString(),textAddress.getText().toString(),textCity.getText().toString(), Integer.parseInt(textZipCode.getText().toString()),textCountry.getText().toString());

                    if (user != null) {
                        ref.child(String.valueOf(user.getEmail()).replace(".",",")).child("userinfo"). setValue(profile);
                    } else {
                        System.out.println("You are not logged in");
                    }

                    Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }
        });


        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        btn_profile.setBackgroundResource(R.drawable.button_pressed);


    }
}
