package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartmeeting.ContactElement;
import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterContactlist;
import com.example.smartmeeting.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Invite_Contacts extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    private ArrayList<ContactElement> kontakter = new ArrayList<>();
    ArrayList<String> kontakt_names = new ArrayList<>();
    ArrayList<String> kontakt_phone = new ArrayList<>();
    ArrayList<String> kontakt_email = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite__contacts);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        //TEST MIDTER KNAPPEN TIL AT ADDED NYE KONTAKTER
        Button btn = findViewById(R.id.btn_big);
        btn.setText("Add \n Contact");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), add_contact.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });



    }

    private void checkSharedPreferences() {
        kontakter.clear();
        kontakt_names.clear();

        int amountOfContact = mPreferences.getInt("nrofcontact", 0);

        for (int i = 0; i < amountOfContact; i++) {
            String mKontakt = mPreferences.getString(Integer.toString(i), null);
            if (mKontakt != null) {
                String[] kontaktString = mKontakt.split("     ", 4);
                ContactElement nyKontakt = new ContactElement(kontaktString[0], kontaktString[1], kontaktString[2], kontaktString[3]);
                kontakter.add(nyKontakt);
            }
        }

        //Sortere kontakter Fra a-å (DETTE BØR RYKKES IND I SAVE KONTAKT FORDI DU BRUGER DEN MINDRE)
        Collections.sort(kontakter, new Comparator<ContactElement>() {
            @Override
            public int compare(ContactElement o1, ContactElement o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (int i = 0; i < kontakter.size(); i++){
            kontakt_names.add(kontakter.get(i).getName());
            kontakt_email.add(kontakter.get(i).getEmail());
            kontakt_phone.add(kontakter.get(i).getNr());

        }

        //Listview bliver initialiseret, får en adapter (custom) og får clickable. Her efter bliver knap funktionen lave.
        ListView listView = findViewById(R.id.listview_Contacts);
        listView.setAdapter(new CustomAdapterContactlist(Invite_Contacts.this,kontakt_names, kontakt_email, kontakt_phone));
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String mail2 = kontakter.get(position).getEmail().replace("\n", "");

                Intent intent = new Intent();
                intent.putExtra("nykontakt", mail2);
                setResult(RESULT_OK, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSharedPreferences();
    }
}
