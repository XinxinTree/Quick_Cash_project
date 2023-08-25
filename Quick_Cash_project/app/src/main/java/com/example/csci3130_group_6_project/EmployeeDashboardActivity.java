package com.example.csci3130_group_6_project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class EmployeeDashboardActivity extends AppCompatActivity implements Serializable {

    Employee user;
    final String fileName = "jobCount.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // activity setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);
        Intent toMainActivity = new Intent(this, MainActivity.class);

        // Extract user
        user = (Employee) getIntent().getSerializableExtra("userNode");
        Toast.makeText(this.getApplicationContext(), user.username, Toast.LENGTH_LONG).show();

        // Setup logout button
        Button logoutButton = findViewById(R.id.employeeDashboardLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toMainActivity);

            }
        });
        LocationUtils.getInstance(EmployeeDashboardActivity.this).getLocation();

        // Setup see jobs button
        Button seeJobListButton = findViewById(R.id.employeeGoToJobList);
        Intent toJobSearchingActivity = new Intent(this, JobSearchingActivity.class);
        toJobSearchingActivity.putExtra("userNode", user);
        seeJobListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toJobSearchingActivity);
            }
        });

        // Look if there are new jobs
        int previousCount = Integer.parseInt(readFileOnInternalStorage());
        countJobs(previousCount);

    }

    // Method to extract user's address
    public void setAddress(Location location, String addressStr) {
        if (location != null) {
            String address = "Latitude：" + location.getLatitude() + "Longitude：" + ((Location) location).getLongitude();
            System.out.println(address);
            TextView Location = (TextView) findViewById(R.id.employeeLocation);
            Location.setText(address + " address:" + addressStr);
        }
    }

    // Method to if there are new see jobs in database
    public void countJobs(int previousCount) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final int[] count = {0};

        // Look at jobs
        DatabaseReference jobsReference = database.getReference().child("Jobs");
        jobsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot adSnapshot : snapshot.getChildren()) {
                    Job job = adSnapshot.getValue(Job.class);
                    if (JobFilter.filterJobPlace(job, user.preference.place)) {
                        count[0]++;
                    }
                }
                // If there are new jobs, make a notification
                if (count[0] > previousCount) {
                    makeNotification();
                }
                writeFileOnInternalStorage(String.valueOf(count[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Method to write to internal Storage
    public void writeFileOnInternalStorage(String sBody) {
        // add-write text into file
        try {
            FileOutputStream fileout = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(sBody);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to read from internal Storage
    public String readFileOnInternalStorage() {
        String s = "0";
        //reading text from file
        try {
            FileInputStream fileIn = openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    // Method to make a notification says that there is a new job
    public void makeNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "new_job")
                        .setSmallIcon(R.drawable.notificationicon)
                        .setContentTitle("New job")
                        .setContentText("Here is a new job in your local area!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("new_job", "new_job", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManagerCompat.notify(1, mBuilder.build());
        }

    }

}
