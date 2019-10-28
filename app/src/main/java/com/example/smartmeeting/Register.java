package com.example.smartmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //progresbar
        progressDialog = new ProgressDialog(this);


        //Finder alle objekterne i XML filen.
        Button btnSignup = findViewById(R.id.btn_signup);
        final EditText txtEmail = findViewById(R.id.text_email);
        final EditText txtPassword = findViewById(R.id.text_password);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView registerText = findViewById(R.id.text_view_login);

        progressBar.setVisibility(View.GONE);

        //sign up knappen
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEmail.getText().toString().equals("") && !txtPassword.getText().toString().equals("") ) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                finish();

                            } else {
                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
