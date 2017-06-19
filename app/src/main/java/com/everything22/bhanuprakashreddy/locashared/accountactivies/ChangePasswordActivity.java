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

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText changePassword;
    private Button btnChange, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        changePassword = (EditText)findViewById(R.id.password);
        btnChange = (Button) findViewById(R.id.btn_new_password);
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

                String newPassword = changePassword.getText().toString();
                if (TextUtils.isEmpty(newPassword)){
                    Toast.makeText(getApplication(), "Enter your New Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                if (user != null && !newPassword.equals("")){
                    if (newPassword.length() < 6) {
                        changePassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    }else{
                        user.updatePassword(newPassword).addOnCompleteListener(ChangePasswordActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                    signOut();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }else if (newPassword.equals("")) {
                    changePassword.setError("Enter password");
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
