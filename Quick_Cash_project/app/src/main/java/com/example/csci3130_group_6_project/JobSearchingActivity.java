package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class JobSearchingActivity extends AppCompatActivity implements Serializable {

    ArrayList<Job> jobs;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Employee user;
    JobFilter jobFilter;
    CountDownLatch done = new CountDownLatch(1);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);

        // Create a jobs arraylist and get intents
        jobs = new ArrayList<>();
        user = (Employee) getIntent().getSerializableExtra("userNode");

        // Create intents for the SetPreferenceActivity class
        Intent toSetPreferenceActivity = new Intent(this,SetPreferenceActivity.class);
        toSetPreferenceActivity.putExtra("userNode",user);

        // Get the button to set the profile and set a listener
        Button setProfileButton = findViewById(R.id.employeeSetProfile);
        setProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toSetPreferenceActivity);
            }
        });

        // Get the button to apply preference and set a listener
        Button applyPreferenceButton = findViewById(R.id.applyPreference);
        applyPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterJobs(user.preference);
            }
        });

        // Call the method to get the jobs
        getJobs();
    }

    public void getJobs() {
        // Get the database and add an event listener
        DatabaseReference jobsReference = database.getReference().child("Jobs");
        jobsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Loop through all the children and save each job to the arrayList
                for (DataSnapshot adSnapshot: snapshot.getChildren()) {
                    Job job = adSnapshot.getValue(Job.class);
                    if (job != null) {
                        if (job.isOpen) {
                            jobs.add(job);
                        }
                    }
                }
                // Call a method to show jobs
                showJobs(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // A method to filter the jobs to only get those that meet the criteria
    public ArrayList<Job> filterJobs(Preference preference) {
        jobFilter = new JobFilter(jobs,preference);
        showJobs(jobFilter.filterJobs());
        return jobFilter.filterJobs();
    }

    // A method to show the jobs on screen to the user
    public void showJobs(ArrayList<Job> filteredJobs)  {
        ListView jobsListView = (ListView) findViewById(R.id.jobsListView);
        ArrayAdapter<Job> adapter = new ArrayAdapter<Job>(
                JobSearchingActivity.this, android.R.layout.simple_list_item_1, filteredJobs);
        jobsListView.setAdapter(adapter);
        jobsListView.setClickable(true);
        jobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Job job = (Job) jobsListView.getItemAtPosition(i);
                switchToJobDetail(job);
            }
        });
    }

    public void switchToJobDetail(Job job) {
        Intent toJobDetail = new Intent(this,JobDetailActivity.class);
        toJobDetail.putExtra("userType","employee");
        toJobDetail.putExtra("userNode",user);
        toJobDetail.putExtra("job",job);
        startActivity(toJobDetail);
    }

    public Preference getPreference() {
        return null;
    }
}
