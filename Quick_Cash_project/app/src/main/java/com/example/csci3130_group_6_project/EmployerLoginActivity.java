package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class EmployerLoginActivity extends AppCompatActivity implements Serializable {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = null;
    static Intent intent;
    static Employer extractedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_login);
        Button button = findViewById(R.id.employerLoginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeDB();
                validate();
            }
        });
        intent = new Intent(this,EmployerDashboardActivity.class);
    }

    protected String getUsername() {
        EditText name = findViewById(R.id.employerUsername);
        return name.getText().toString().trim();
    }

    protected String getPassword() {
        EditText password = findViewById(R.id.employerPassword);
        return password.getText().toString().trim();
    }

    protected void validate() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                extractedUser = snapshot.getValue(Employer.class);
                if (extractedUser != null) {
                    if(extractedUser.password.equals(getPassword())){
                        // Pass Employee to Dashboard Activity
                        intent.putExtra("userNode",extractedUser);
                        intent.putExtra("username", getUsername());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployerLoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initializeDB() {
        String username = getUsername();
        userReference = database.getReference("users").child("employers").
                child(username);
    }


}
