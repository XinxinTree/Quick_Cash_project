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

public class EmployeeLoginActivity extends AppCompatActivity implements Serializable {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userReference = null;
    static Intent intent;
    static Employee extractedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        Button button = findViewById(R.id.employeeLoginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeDB();
                validate();
            }
        });
        intent = new Intent(this,EmployeeDashboardActivity.class);
    }

    protected String getUsername() {
        EditText name = findViewById(R.id.userName);
        return name.getText().toString().trim();
    }

    protected String getPassword() {
        EditText password = findViewById(R.id.password);
        return password.getText().toString().trim();
    }

    protected void validate() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                extractedUser = snapshot.getValue(Employee.class);
                if (extractedUser != null) {
                    if(extractedUser.password.equals(getPassword())){
                        // Pass Employee to Dashboard Activity
                        intent.putExtra("userNode",extractedUser);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeLoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void initializeDB() {
        String username = getUsername();
        userReference = database.getReference("users").child("employees").
                child(username);
    }


}



