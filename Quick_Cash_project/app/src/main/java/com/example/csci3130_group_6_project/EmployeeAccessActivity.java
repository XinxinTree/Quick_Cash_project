package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_access);

        //Get buttons by their ID
        Button employeeRegisterButton = findViewById(R.id.switchToEmployeeRegisterPageButton);
        Button employeeLoginButton = findViewById(R.id.switchToEmployeeLoginPageButton);


        employeeRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                registerEmployee();
            }
        });

        employeeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                loginEmployee();
            }
        });
    }

    protected void registerEmployee() {
        Intent intent = new Intent(this, EmployeeRegisterActivity.class);
        startActivity(intent);
    }

    protected void loginEmployee() {
        Intent intent = new Intent(this, EmployeeLoginActivity.class);
        startActivity(intent);

    }
}
