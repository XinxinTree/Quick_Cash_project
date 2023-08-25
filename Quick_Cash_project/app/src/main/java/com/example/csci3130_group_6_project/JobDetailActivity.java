package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class JobDetailActivity extends AppCompatActivity {

    Employee employee;
    Employer employer;
    Job job;
    DatabaseReference JobRef = FirebaseDatabase.getInstance().getReference("Jobs");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        String userType = (String) getIntent().getSerializableExtra("userType");

        job = (Job) getIntent().getSerializableExtra("job");
        setUpJobForm();
        setUpBackButton(userType);

        if (userType.equals("employee")) {
            employee  = (Employee) getIntent().getSerializableExtra("userNode");
            setUpApplyButton();
        }
        else if (userType.equals("employer")) {
            employer = (Employer) getIntent().getSerializableExtra("userNode");
            if (!job.isOpen) {
                setUpPayButton();
            }
        }


    }


    public void setUpJobForm() {
        ListView jobDetailListView = findViewById(R.id.jobDetailListView);
        ArrayList<String> jobDetails = job.toStringArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                JobDetailActivity.this, android.R.layout.simple_list_item_1, jobDetails);
        jobDetailListView.setAdapter(adapter);

    }

    public void setUpApplyButton() {
        Button applyButton = findViewById(R.id.applyButton);
        Button payButton = findViewById(R.id.payButton);
        payButton.setVisibility(View.GONE);

        Intent toSearchJobActivity = new Intent(this,JobSearchingActivity.class);
        toSearchJobActivity.putExtra("userNode",employee);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeJob();
                startActivity(toSearchJobActivity);
            }
        });
    }

    public void setUpPayButton() {
        Button applyButton = findViewById(R.id.applyButton);
        Button payButton = findViewById(R.id.payButton);
        applyButton.setVisibility(View.GONE);
    }

    public void setUpBackButton(String userType) {
        Intent backIntent = null;
        if (userType.equals("employee")) {
            backIntent = new Intent(this,JobSearchingActivity.class);
            backIntent.putExtra("userNode",employee);
        }
        else if (userType.equals("employer")) {
            backIntent = new Intent(this,EmployerViewSubmittedJobsActivity.class);
            backIntent.putExtra("userNode",employer);
        }
        Button backButton = findViewById(R.id.backButton);
        Intent finalBackIntent = backIntent;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(finalBackIntent);
            }
        });
    }

    public void closeJob() {
        job.isOpen = false;
        JobRef.child(job.key).setValue(job);
    }

}
