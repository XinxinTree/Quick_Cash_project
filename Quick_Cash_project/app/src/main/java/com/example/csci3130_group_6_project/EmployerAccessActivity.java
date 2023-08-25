package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployerAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_access);

        //Get buttons by their ID
        Button employerRegisterButton = findViewById(R.id.switchToEmployerRegisterPageButton);
        Button employerLoginButton = findViewById(R.id.switchToEmployerLoginPageButton);

        employerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                registerEmployer();
            }
        });

        employerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                loginEmployer();
            }
        });
    }

    protected void registerEmployer() {
        Intent intent = new Intent(this, EmployerRegisterActivity.class);
        startActivity(intent);
    }

    protected void loginEmployer() {
        Intent intent = new Intent(this, EmployerLoginActivity.class);
        startActivity(intent);
    }
}
