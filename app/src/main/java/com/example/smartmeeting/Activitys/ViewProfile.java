package com.example.smartmeeting.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmeeting.MainLogic.DTO.user.UserDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        ////Textviews
        textName = findViewById(R.id.name);
        textPhone = findViewById(R.id.phonenumber);
        textEmail = findViewById(R.id.email);
        textCompany = findViewById(R.id.company);
        textAddress = findViewById(R.id.address);
        textCity = findViewById(R.id.city);
        textZipCode = findViewById(R.id.zip_code);
        textCountry = findViewById(R.id.country);

        //button og buttontext
        Button editProfile = findViewById(R.id.btn_big);
        editProfile.setText("Edit \n Profile");


        //Checker om der er en user logget på
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            //Ændre animationen til at køre fra højre til venstre. Når der skiftes intent
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            //Afslutter Profileview
            finish();
        }

        //Finder en bestemt bruger via en path /Users/Email/userinfo
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Users").child(email.replace(".",",")).child("userinfo");


        //sætter en Valuelistener til at lytte for data ændringer
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Bruger data fra vores Datasnapshot og ændrer vores Textviews
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

            //Hvis der sker en fejl kan du evt lave nogen fejlbeskeder for at printe dem ud.
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            //Når man trykker på edit profile.
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startIntent = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(startIntent);
                    //Ændre animationen til at køre fra højre til venstre. Når der skiftes intent
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });

        //Menuen
        Button btn_profile = findViewById(R.id.btn_profile_menu);
        Button btn_meetings = findViewById(R.id.btn_meeting_menu);
        Button btn_contacts = findViewById(R.id.btn_contacts_menu);

        //Sætter menu texten under ikonet,
        TextView menuText = findViewById(R.id.btn_profile_menu);
        menuText.setText("Profile");

        btn_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                //Afslutter Profileview
                finish();

            }
        });

        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                //Afslutter Profileview
                finish();
            }
        });

        //Viser grafik når vi er på ProfileView
        btn_profile.setBackgroundResource(R.drawable.button_pressed);

    }
}
