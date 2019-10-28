package com.example.smartmeeting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //progresbar
        progressDialog = new ProgressDialog(this);

        //Finder alle objekterne i XML filen.
        Button btnLogin = findViewById(R.id.btn_login);
        final EditText txtEmail = findViewById(R.id.text_email);
        final EditText txtPassword = findViewById(R.id.text_password);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView registerText = findViewById(R.id.text_view_register);
        TextView forgotPassword = findViewById(R.id.text_view_forget_password);

        progressBar.setVisibility(View.GONE);



        //Login knappen
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEmail.getText().toString().equals("") && !txtPassword.getText().toString().equals("") ) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                //SKAL LOGGE IND HER OG GEMME BRUGERE I SHARED PREFFERANCES.
                                Intent in = new Intent(getApplicationContext(), MeetingOverview.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



                            } else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        //Registrere teksten som clickable tekst... (HAR ADDED android:clickable="true")
        registerText.setClickable(true);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Register.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Forgot password teksten som clickable tekst....
        forgotPassword.setClickable(true);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });




    }





}
