package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployerViewSubmittedJobsActivity extends AppCompatActivity implements Serializable {

    ArrayList<Job> jobs;
    Employer user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_submitted_jobs);
        Intent intent = getIntent();
        user = (Employer) intent.getSerializableExtra("userNode");
        Intent backToEmployerDashboardIntent = new Intent(this, EmployerDashboardActivity.class);
        backToEmployerDashboardIntent.putExtra("userNode",user);


        jobs = new ArrayList<>();

        Button closeButton = findViewById(R.id.employerViewSubmittedJobsCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backToEmployerDashboardIntent);
            }
        });

        // Get the jobs then show them on the page
        getJobs(user.username);

    }

    /**
     * A method to retrieve all of the jobs from the database.
     * The "jobs" list will be populated with jobs submitted by the current employer.
     */
    public void getJobs(String employerUsername){
        FirebaseDatabase.getInstance().getReference().child("Jobs")
                .orderByChild("employer").equalTo(employerUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    /**
                     * Search through the data snapshot to find all jobs posted by the current employer.
                     * @param snapshot The snapshot of the current data in the database.
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Job job = ds.getValue(Job.class);
                            jobs.add(job);
                        }
                        showJobs(jobs);
                    }

                    /**
                     * A method to handle the cancellation of the event listener.
                     * @param error The error message recieved from the database.
                     */
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.err.println(error);
                    }
                });
    }

    /**
     * A method to show the jobs on the page for the user.
     * @param jobs the list of jobs to show.
     */
    public void showJobs(ArrayList<Job> jobs){
        // Get the listView from the page to show jobs in
        ListView employerJobsSubmittedListView = (ListView) findViewById(R.id.EmployerJobsSubmittedListView);

        ArrayAdapter<Job> adapter = new ArrayAdapter<Job>(
                EmployerViewSubmittedJobsActivity.this, android.R.layout.simple_list_item_1, jobs
        );

        // Show the jobs
        employerJobsSubmittedListView.setAdapter(adapter);
        employerJobsSubmittedListView.setClickable(true);
        employerJobsSubmittedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Job job = (Job) employerJobsSubmittedListView.getItemAtPosition(i);
                switchToJobDetail(job);
            }
        });
    }


    public void switchToJobDetail(Job job) {
        Intent toJobDetail = new Intent(this,JobDetailActivity.class);
        toJobDetail.putExtra("userType","employer");
        toJobDetail.putExtra("userNode",user);
        toJobDetail.putExtra("job",job);
        startActivity(toJobDetail);
    }
}
