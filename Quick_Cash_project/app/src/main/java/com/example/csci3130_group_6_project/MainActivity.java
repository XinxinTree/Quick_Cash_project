package com.example.csci3130_group_6_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    FirebaseMessaging firebaseMessaging;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button testFirebase = findViewById(R.id.testFirebase);
        Button employerLanding = findViewById(R.id.button_employer);
        Button employeeLanding = findViewById(R.id.button_employee);
        testFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFirebaseConnection();
            }
        });
        employerLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmployerSelected();
            }
        });
        employeeLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmployeeSelected();
            }
        });

        firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("all");
    }

    protected void testFirebaseConnection() {
        int testValue = (int)(Math.random()*123456);
        Toast.makeText(getApplicationContext(),
                "Value: " + testValue + "has been inserted. Check firebase", Toast.LENGTH_SHORT).show();
        databaseReference.child("test").child(String.valueOf(testValue)).setValue(testValue);
    }

    protected void onEmployerSelected() {
        Intent show = new Intent(this, EmployerAccessActivity.class);
        startActivity(show);
    }

    protected void onEmployeeSelected() {
        Intent show = new Intent(this, EmployeeAccessActivity.class);
        startActivity(show);
    }
}