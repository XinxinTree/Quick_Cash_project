package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployerNewJobFormActivity extends AppCompatActivity implements Serializable {
    FirebaseDatabase database;
    DatabaseReference employersJobsReference;
    Employer user;
    //The valid date format is set to yyyy-MM-dd
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public EmployerNewJobFormActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_new_job_format);

        // Get the employer submitting the job from intents so it can be put into firebase
        user = (Employer) getIntent().getSerializableExtra("user");

        // Create a spinner to put on the page for the type of the new job
        Spinner jobTypeSpinner = (Spinner) findViewById(R.id.newJobType);
        ArrayAdapter<CharSequence> jobTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.jobTypes_array, android.R.layout.simple_spinner_item);
        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeSpinner.setAdapter(jobTypeAdapter);

        // Create a spinner to put on the page for the urgency of the new job
        Spinner jobUrgencySpinner = (Spinner) findViewById(R.id.newJobUrgency);
        ArrayAdapter<CharSequence> jobUrgencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.urgency_array, android.R.layout.simple_spinner_item);
        jobUrgencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobUrgencySpinner.setAdapter(jobUrgencyAdapter);

        // Add a listener for the "Submit" button
        Button submitNewJobButton = findViewById(R.id.newJobSubmitJobButton);
        submitNewJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitNewJob();
            }
        });

        // Configure the path in firebase for the new jobs
        database = FirebaseDatabase.getInstance("https://csci3130-group6-project-default-rtdb.firebaseio.com/");
        employersJobsReference = database.getReference("Jobs");

    }

    // Getter methods for each field of a job
    String getJobType(){
        Spinner spinner = findViewById(R.id.newJobType);
        return spinner.getSelectedItem().toString();
    }

    String getDate(){
        EditText DateBox = findViewById(R.id.newJobDate);
        return DateBox.getText().toString().trim();
    }

    String getJobDescription(){
        EditText JobDescriptionBox = findViewById(R.id.newJobDescription);
        return JobDescriptionBox.getText().toString().trim();
    }

    String getJobDuration(){
        EditText JobDurationBox = findViewById(R.id.newJobDuration);
        return JobDurationBox.getText().toString().trim();
    }

    String getJobPlace(){
        EditText JobPlaceBox = findViewById(R.id.newJobPlace);
        return JobPlaceBox.getText().toString().trim();
    }

    String getJobUrgency(){
        Spinner spinner = findViewById(R.id.newJobUrgency);
        return spinner.getSelectedItem().toString();
    }

    String getJobSalary(){
        EditText JobSalary = findViewById(R.id.newJobSalary);
        return JobSalary.getText().toString().trim();
    }

    // Boolean methods to check if fields were not filled in by the user
    static boolean isJobTypeEmpty(String JobType){
        return JobType.isEmpty();
    }

    static boolean isDateEmpty(String Date){
        return Date.isEmpty();
    }

    public boolean isJobDescriptionEmpty(String jobDescription){
        return jobDescription.isEmpty();
    }

    public boolean isJobDurationEmpty(String jobDuration){
        return jobDuration.isEmpty();
    }

    public boolean isJobPlaceEmpty( String jobPlace){
        return jobPlace.isEmpty();
    }

    public boolean isJobUrgencyEmpty(String jobUrgency){
        return jobUrgency.isEmpty();
    }

    public boolean isJobSalaryEmpty(String jobSalary){
        return jobSalary.isEmpty();
    }

    // Boolean method to check if the date is in a valid format and is in the future
    public boolean isDateValid(String date) {
        date = date.replace("-", "");
        if (date.length() > 8) {
            Toast.makeText(getApplicationContext(), "yyyy/MM/dd should have at most 8 digits",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(date.substring(4, 6)) > 12) {
            Toast.makeText(getApplicationContext(), "MM has a wrong format",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            int now = Integer.parseInt(LocalDate.now().format(dtf));
            int expireDateInt = Integer.parseInt(date);
            if (now > expireDateInt) {
                Toast.makeText(getApplicationContext(), "job has expired",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    // Methods to show a toast if something the user entered was incorrect
    void showEmptyFieldToast(String fieldName){
        Toast.makeText(this, fieldName + " is a required field!", Toast.LENGTH_LONG).show();
    }

    void showInvalidFieldToast(String fieldName){
        Toast.makeText( this, fieldName + "is invalid field!", Toast.LENGTH_SHORT).show();
    }

    // Method to switch back to the employer dashboard once the job is submitted
    void switchToDashboard(){
        Intent intent = new Intent(this, EmployerDashboardActivity.class);
        intent.putExtra("userNode", user);
        startActivity(intent);
    }

    // Method to get fields for the job and add the new job to firebase
    protected void submitNewJob(){
        // Get all fields using their getter method
        String jobType = getJobType();
        String jobDate = getDate();
        String jobDescription = getJobDescription();
        String jobDuration = getJobDuration();
        String jobPlace = getJobPlace();
        String jobUrgency = getJobUrgency();
        String jobSalary = getJobSalary();
        double salary = Double.parseDouble(jobSalary);

        // Verify that the user did not leave any fields empty, and that formats for some inputs are correct
        if(isJobTypeEmpty(jobType)){
            showEmptyFieldToast(jobType);
            return;
        }
        else if(isDateEmpty(jobDate)){
            showEmptyFieldToast(jobDate);
            return;
        }
        else if(!isDateValid(jobDate)){
            showInvalidFieldToast(jobDate);
            return;
        }
        else if(isJobPlaceEmpty(jobPlace)){
            showEmptyFieldToast(jobPlace);
            return;
        }
        else if(isJobUrgencyEmpty(jobUrgency)){
            showEmptyFieldToast(jobUrgency);
            return;
        }
        else if(isJobSalaryEmpty(jobSalary)){
            showEmptyFieldToast(jobSalary);
            return;
        }

        // Create the date object and put it in the database
        Job newJob = new Job(jobType,jobDescription,jobDate,jobDuration,jobPlace,jobUrgency,salary, user.username, null);
        DatabaseReference newPostRef = employersJobsReference.push();
        newJob.key = newPostRef.getKey();
        newPostRef.setValue(newJob);

        // Switch back to dashboard
        switchToDashboard();
    }
}
