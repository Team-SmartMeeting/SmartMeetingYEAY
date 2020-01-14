package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import com.example.smartmeeting.MainLogic.DTO.user.UserDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;


/**
 * @author Simon Philipsen
 */

public class EditProfile extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        final TextView textName = (TextView) findViewById(R.id.name);
        final TextView textPhone = (TextView) findViewById(R.id.phonenumber);
        final TextView textEmail = (TextView) findViewById(R.id.email);
        final TextView textCompany = (TextView) findViewById(R.id.company);
        final TextView textAddress = (TextView) findViewById(R.id.address);
        final TextView textZipCode = (TextView) findViewById(R.id.zip_code);
        final TextView textCountry = (TextView) findViewById(R.id.country);


        Button editProfile = (Button) findViewById(R.id.btn_big);
        editProfile.setText("Save \n Changes");
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checker om felter er tommme!
                if (!textName.getText().toString().equals("") && !textPhone.getText().toString().equals("") && !textEmail.getText().toString().equals("") && !textCompany.getText().toString().equals("") && !textAddress.getText().toString().equals("") && !textZipCode.getText().toString().equals("") && !textCountry.getText().toString().equals("")) {


                    //opretter en Profil
                    UserDTO profile = new UserDTO(textName.getText().toString(), textEmail.getText().toString(),textPhone.getText().toString(), textCompany.getText().toString(),textAddress.getText().toString(), Integer.parseInt(textZipCode.getText().toString()),textCountry.getText().toString());

                    //Profile I STRING FORMAT! (taget fra stackoverflow)
                    //-------------------------------------------------
                    Gson gson = new Gson();
                    String myJson = gson.toJson(profile);
                    //-------------------------------------------------

                    Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                    intent.putExtra("UserDTO", myJson);
                    startActivity(intent);
                }
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
                finish();

            }
        });

        btn_groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), groups_list.class);
                startActivity(intent);
                finish();

            }
        });

        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                finish();
            }
        });

        btn_profile.setBackgroundResource(R.drawable.button_pressed);


    }
}
