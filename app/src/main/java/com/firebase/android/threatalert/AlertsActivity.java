package com.firebase.android.threatalert;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AlertsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView textViewUserEmail;
    private TextView textViewDate;
    private Button buttonSearch;
    private Button buttonBack;
    private Button buttonLogout;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AlertsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                textViewDate.setText(date);
            }
        };
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
        if(view == buttonSearch) {
            String date = (String)textViewDate.getText();
            if(!(date.equals(""))) {
                Intent intent = new Intent(getBaseContext(), SearchResult.class);
                intent.putExtra("EXTRA_DATA", date);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Click on Select a Date", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if(view == buttonBack) {
            finish();
            startActivity(new Intent(this, MainMenuActivity.class));
        }

        if(view == buttonLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
