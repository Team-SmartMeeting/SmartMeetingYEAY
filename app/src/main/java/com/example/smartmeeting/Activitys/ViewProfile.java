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

public class ViewProfile extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private SharedPreferences mPreferences;
    private UserDTO currentUser;
    private SharedPreferences.Editor mEditor;

    String email;
    TextView textName;
    TextView textPhone;
    TextView textEmail;
    TextView textCompany;
    TextView textAddress;
    TextView textZipCode;
    TextView textCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        currentUser = new UserDTO();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();


        TextView textName = findViewById(R.id.name);
        TextView textPhone = findViewById(R.id.phonenumber);
        TextView textEmail = findViewById(R.id.email);
        TextView textCompany = findViewById(R.id.company);
        TextView textAddress = findViewById(R.id.address);
        TextView textZipCode = findViewById(R.id.zip_code);
        TextView textCountry = findViewById(R.id.country);


        Button editProfile = findViewById(R.id.btn_big);
        editProfile.setText("Edit \n Profile");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            email = user.getEmail();
            textEmail.setText(email);
        }
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
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

    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()){


            currentUser.setName(ds.child(email.replace(".",",")).getValue(UserDTO.class).getName());
            currentUser.setEmail(ds.getValue(UserDTO.class).getEmail());
            currentUser.setPhoneNumber(ds.getValue(UserDTO.class).getPhoneNumber());
            currentUser.setCompany(ds.getValue(UserDTO.class).getCompany());
            currentUser.setAddress(ds.getValue(UserDTO.class).getAddress());
            currentUser.setZipCode(ds.getValue(UserDTO.class).getZipCode());
            currentUser.setCountry(ds.getValue(UserDTO.class).getCountry());




            textName.setText(currentUser.getName());
            textEmail.setText(currentUser.getEmail());
            textPhone.setText(currentUser.getPhoneNumber());
            textCompany.setText(currentUser.getCompany());
            textAddress.setText(currentUser.getAddress());
            textZipCode.setText(Integer.toString(currentUser.getZipCode()));
            textCountry.setText(currentUser.getCountry());
        }
    }
}
