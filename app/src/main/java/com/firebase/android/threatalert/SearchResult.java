package com.firebase.android.threatalert;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchResult extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView textViewUserEmail;
    private TextView textViewAlerts;
    private Button buttonBack;
    private Button buttonLogout;
    private String data;
    private String[] date;
    private String path;
    private CollectionReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        textViewAlerts = (TextView) findViewById(R.id.textViewAlerts);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final String[] alertText = new String[1];

        if (mAuth.getCurrentUser() == null) {
            // user not logged in
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        data = getIntent().getStringExtra("EXTRA_DATA");
        date = data.split("/");
        path = "Year/" + date[2] + "/Month/" + getMonth(Integer.valueOf(date[0])) + "/Day/";
        if(Integer.valueOf(date[1]) < 10) {
            path = path + "0";
        }
        path = path + date[1] + "/Alerts";
        mDocRef = FirebaseFirestore.getInstance().collection(path);

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        alertText[0] = alertText[0] + document.getData() + "\n\n";
                    }
                }
                textViewAlerts.setText(alertText[0]);
            }
        });
    }

    public void OnClick(View view) {
        if(view == buttonBack) {
            finish();
            startActivity(new Intent(this, AlertsActivity.class));
        }

        if(view == buttonLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private String getMonth(int x) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[x - 1];
    }
}
