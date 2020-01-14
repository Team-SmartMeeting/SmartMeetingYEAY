package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterAgenda;
import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.user.User;
import com.example.smartmeeting.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserInvited extends AppCompatActivity {

    ListView listView;
    ArrayList<String> emails;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invited);

        gson = new Gson();

        emails = new ArrayList<>();

        Button btn_add_user = findViewById(R.id.btn_add_user);
        Button btn_big = findViewById(R.id.btn_big);

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(UserInvited.this, InviteToMeeting.class);
                startActivityForResult(in, 2);
            }
        });

    }

    //taget fra stackoverflow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                String nyUser = data.getStringExtra("nymail2");
                emails.add(nyUser);

                UpdateList();
            }

        }
    }

    public void UpdateList(){

        //CLEAR ALLE ARRAYLISTER SÅ DE ER KLAR TIL AT BLIVE REPOSTET

        //TILFØJER ALLE TOPICS TIL ARRAYLISTER

        // DER SKAL LAVES EN NY ADAPTER TIL AT SMIDE DATAEN IND I LISTEN
//        listView.setAdapter(new CustomAdapterAgenda(UserInvited.this, emails));


//        listView.setClickable(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Agenda.this, popup.class);
//                intent.putExtra("name",agenda.get(position).getTopicName());
//                intent.putExtra("email",agenda.get(position).getTopicDuration());
//                intent.putExtra("number",agenda.get(position).getDescription());
//                startActivity(intent);
//
//            }
//        });




    }
}
