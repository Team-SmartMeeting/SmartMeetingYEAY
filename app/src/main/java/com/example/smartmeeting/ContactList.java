package com.example.smartmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactList extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private ArrayList<ContactElement> kontakter = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        ArrayList<String> kontakt_names = new ArrayList<>();


        for (int i = 0; i < kontakter.size(); i++){
            kontakt_names.add(kontakter.get(i).getName());
        }


        ListView listView = findViewById(R.id.listview_Contacts);

        listView.setAdapter(new CustomAdapter(ContactList.this,kontakt_names));


    }

    private void checkSharedPreferences() {
        int amountOfContact = mPreferences.getInt("nr_of_contact", 0);

        for (int i = 0; i < amountOfContact; i++) {
            String mKontakt = mPreferences.getString(Integer.toString(i), null);
            if (mKontakt != null) {
                String[] kontaktString = mKontakt.split("|", 3);
                ContactElement nyKontakt = new ContactElement(kontaktString[0], kontaktString[1], kontaktString[2]);
            }
        }

        //Sortere kontakter Fra a-å (DETTE BØR RYKKES IND I SAVE KONTAKT FORDI DU BRUGER DEN MINDRE)
        Collections.sort(kontakter, new Comparator<ContactElement>() {
            @Override
            public int compare(ContactElement o1, ContactElement o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

    }
}
