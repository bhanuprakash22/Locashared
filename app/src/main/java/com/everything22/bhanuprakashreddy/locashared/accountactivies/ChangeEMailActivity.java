package com.everything22.bhanuprakashreddy.locashared.accountactivies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.everything22.bhanuprakashreddy.locashared.LoginActivity;
import com.everything22.bhanuprakashreddy.locashared.R;

public class ChangeEMailActivity extends AppCompatActivity {


    private EditText changeEmail;
    private Button btnChange, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ChangeEMailActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        changeEmail = (EditText)findViewById(R.id.email);
        btnChange = (Button) findViewById(R.id.btn_change_mail);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = changeEmail.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), "Enter your Valid email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                if (user != null && !email.equals("")){
                    user.updateEmail(email).addOnCompleteListener(ChangeEMailActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(ChangeEMailActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                signOut();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(ChangeEMailActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }else if (changeEmail.getText().toString().trim().equals("")) {
                    changeEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
