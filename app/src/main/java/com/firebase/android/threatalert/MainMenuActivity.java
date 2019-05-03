package com.firebase.android.threatalert;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView textViewUserEmail;
    private Button buttonAlerts;
    private Button buttonStatistics;
    private Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonAlerts = (Button) findViewById(R.id.buttonAlerts);
        buttonStatistics = (Button) findViewById(R.id.buttonStatistics);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null) {
            // user already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();
        textViewUserEmail.setText("Logged in as " + user.getEmail());
    }

    public void OnClick(View view) {
        if(view == buttonAlerts) {
            finish();
            startActivity(new Intent(this, AlertsActivity.class));
        }

        if(view == buttonStatistics) {
            Toast.makeText(this, "This feature is not implemented yet", Toast.LENGTH_LONG).show();
        }

        if(view == buttonLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
