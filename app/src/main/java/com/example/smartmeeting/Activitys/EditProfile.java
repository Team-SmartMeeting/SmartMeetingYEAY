package com.example.smartmeeting.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        //Firebase Instance og reference
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        //Text views
        final TextView textName =  findViewById(R.id.name);
        final TextView textPhone =  findViewById(R.id.phonenumber);
        final TextView textEmail =  findViewById(R.id.email);
        final TextView textCompany =  findViewById(R.id.company);
        final TextView textAddress =  findViewById(R.id.address);
        final TextView textCity =     findViewById(R.id.city);
        final TextView textZipCode =  findViewById(R.id.zip_code);
        final TextView textCountry =  findViewById(R.id.country);

        //Firebase Instance og reference på path /Users/email/userinfo
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Users").child(email.replace(".",",")).child("userinfo");

        //sætter en Valuelistener til at lytte for data ændringer og henter dem
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDTO post = dataSnapshot.getValue(UserDTO.class);

                //Sætter alle textviews til den hentede data
                textName.setText(post.getName());
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


        //opretter savebutton
        Button saveProfile = findViewById(R.id.btn_big);
        saveProfile.setText("Save \n Changes");
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checker om felter er tommme!
                if (!textName.getText().toString().equals("") && !textPhone.getText().toString().equals("") && !textCompany.getText().toString().equals("") && !textAddress.getText().toString().equals("")&& !textCity.getText().toString().equals("") && !textZipCode.getText().toString().equals("") && !textCountry.getText().toString().equals("")) {


                    //opretter en Profil med UserDTO
                    UserDTO profile = new UserDTO(textName.getText().toString(), String.valueOf(user.getEmail()).replace(".",","),textPhone.getText().toString(), textCompany.getText().toString(),textAddress.getText().toString(),textCity.getText().toString(), Integer.parseInt(textZipCode.getText().toString()),textCountry.getText().toString());
                    //Checker om User er i databasen
                    if (user != null) {
                        ref.child(String.valueOf(user.getEmail()).replace(".",",")).child("userinfo"). setValue(profile);
                    } else {
                        Toast.makeText(EditProfile.this, "You are not logged in",Toast.LENGTH_LONG).show();
                    }
                    //Data er gemt i database og går tilbage til Viewprofile
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }else {
                    //Fejl  besked
                    Toast.makeText(EditProfile.this, "Alle felter skal udfyldes",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
