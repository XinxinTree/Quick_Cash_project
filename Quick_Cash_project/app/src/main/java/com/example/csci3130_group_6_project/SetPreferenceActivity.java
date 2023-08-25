package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class SetPreferenceActivity extends AppCompatActivity{

    Employee user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_preference);
        user = (Employee) getIntent().getSerializableExtra("userNode");

        Spinner spinner = (Spinner) findViewById(R.id.jobType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jobTypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button employeeSetPreferenceButton = findViewById(R.id.employeeSetPreferenceButton);
        Intent toJobSearchingActivity = new Intent(this,JobSearchingActivity.class);
        toJobSearchingActivity.putExtra("userNode",user);
        employeeSetPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preference preference = new Preference(getJobType(),getLowestSalary(),
                        getHighestSalary(),getDuration(),getPlace());
                updateUserPreference(preference);
                startActivity(toJobSearchingActivity);
            }
        });
    }

    public String getJobType() {
        Spinner spinner = findViewById(R.id.jobType);
        return spinner.getSelectedItem().toString();
    }

    public String getLowestSalary() {
        EditText lowestSalary = findViewById(R.id.lowestSalary);
        return lowestSalary.getText().toString().trim();
    }

    public String getHighestSalary() {
        EditText highestSalary = findViewById(R.id.highestSalary);
        return highestSalary.getText().toString().trim();
    }

    public String getPlace() {
        EditText place = findViewById(R.id.place);
        return place.getText().toString().trim();
    }

    public String getDuration() {
        EditText duration = findViewById(R.id.duration);
        return duration.getText().toString().trim();
    }

    public void updateUserPreference(Preference preference) {
        user.preference = preference;
        DatabaseReference userReference = database.getReference("users").
                child("employees").child(user.username);
        userReference.setValue(user);
    }
}
