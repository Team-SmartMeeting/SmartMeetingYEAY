package com.example.smartmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    //Share preferances:
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //progresbar
        progressDialog = new ProgressDialog(this);

        //Sætter mine shared preferences op
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();


        //Finder alle objekterne i XML filen.
        Button btnSignup = findViewById(R.id.btn_signup);
        final EditText txtEmail = findViewById(R.id.text_email);
        final EditText txtPassword = findViewById(R.id.text_password);
        final EditText txtPassword2 = findViewById(R.id.text_password_re);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView registerText = findViewById(R.id.text_view_login);

        progressBar.setVisibility(View.GONE);

        //sign up knappen
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kontroller at password og bruger navn IKKE er tomme
                if (!txtEmail.getText().toString().equals("") && !txtPassword.getText().toString().equals("") ) {

                    //Kontroller at de 2 password felter er det samme.
                    if (txtPassword.getText().toString().equals(txtPassword2.getText().toString())) {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            mEditor.putString("newUser","0");
                                            mEditor.commit();
                                            finish();
                                            Intent in = new Intent(getApplicationContext(), MeetingOverview.class);
                                            startActivity(in);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                        }
                                    });



                                } else {
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    // Hvis passwordene er forskellige, printer en fejl message ude
                    else {
                        Toast.makeText(Register.this, "Passwordet matchede ikke med det i re enter password",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //TEKSTEN
        registerText.setClickable(true);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
