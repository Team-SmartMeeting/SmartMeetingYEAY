package com.example.smartmeeting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.smartmeeting.meetings.MeetingDTO;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    Menu menu;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);

        //Firebase

        //progresbar
        progressDialog = new ProgressDialog(this);






        //TEST DEL TIL LISTE
        ArrayList<MeetingDTO> arrayList = new ArrayList<>();

        MeetingDTO meeting1 = new MeetingDTO("Android","jeg", LocalDate.of(2019, Month.APRIL, 25), LocalTime.of(12,30));
        MeetingDTO meeting2 = new MeetingDTO("er","elsker", LocalDate.of(2019, Month.APRIL, 28), LocalTime.of(10,10));
        MeetingDTO meeting3 = new MeetingDTO("Fedt","kage", LocalDate.of(2019, Month.APRIL, 24), LocalTime.of(17,35));

        arrayList.add(meeting1);
        arrayList.add(meeting2);
        arrayList.add(meeting3);

        ArrayList<String> stringlist = new ArrayList<>();

        for (int i = 0; i<3; i++) {
            stringlist.add(arrayList.get(i).getName());
        }

//        stringlist.add("hej");
//        stringlist.add("med");
//        stringlist.add("dig");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, stringlist);

        listView.setAdapter(arrayAdapter);


    }








     // Metoderne til menuen
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.home){
            //Home
            setTitle("Meetings");
        } else if (item.getItemId() == R.id.contacts){
            setTitle("Contacts");
        } else if (item.getItemId() == R.id.groupe){
            setTitle("Groups");
        } else if (item.getItemId() == R.id.profile){
            setTitle("Profile");
        } else if (item.getItemId() == R.id.settings){
            setTitle("Settings");
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


}
