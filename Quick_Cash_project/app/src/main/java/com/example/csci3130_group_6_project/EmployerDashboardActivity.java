package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class EmployerDashboardActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);
        Intent toMainActivity = new Intent(this, MainActivity.class);

        Employer userNode = (Employer) getIntent().getSerializableExtra("userNode");
        Toast.makeText(this.getApplicationContext(), userNode.username, Toast.LENGTH_LONG).show();

        // Setup logout button
        Button logoutButton = findViewById(R.id.employerDashboardLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onLogOutSelected();
            }
        });

        // Setup submit new job button
        Button submitNewJobButton = findViewById(R.id.employerDashboardSubmitNewJob);
        submitNewJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmployerNewJobSelected(userNode);
            }
        });

        // Set a listener for the "View Submitted Jobs" button
        // Switch to the toEmployerViewSubmittedJobs when the button is pressed
        Button viewSubmittedJobsButton = findViewById(R.id.employerDashboardViewSubmittedJobsButton);
        viewSubmittedJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewSubmittedJobsSelected(userNode);
            }
        });
        LocationUtils.getInstance( EmployerDashboardActivity.this ).getLocation();

    }

    // Method to extract the user's address
    public void setAddress(Location location,String addressStr){
        if (location != null) {
            String address = "Latitude：" + location.getLatitude() + "Longitude：" + ((Location) location).getLongitude();
            System.out.println(address);
            TextView Location = (TextView) findViewById(R.id.employerlocation);
            Location.setText(address + " address:" +addressStr);
        }
    }

    // Method to call when the user selects the view submited job button
    protected void onViewSubmittedJobsSelected(Employer employer){
        Intent intent = new Intent(this, EmployerViewSubmittedJobsActivity.class);
        intent.putExtra("userNode", employer);
        startActivity(intent);
    }

    // Method to call when the user selects the submit new job button
    protected void onEmployerNewJobSelected(Employer user) {
        Intent show = new Intent(this, EmployerNewJobFormActivity.class);
        show.putExtra("user",user);
        startActivity(show);
    }

    // Method to call when the user selects the logout button
    protected void onLogOutSelected(){
        Intent show = new Intent(this,MainActivity.class);
        startActivity(show);
    }

}
