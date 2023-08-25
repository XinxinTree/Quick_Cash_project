package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.util.PatternsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class EmployerRegisterActivity extends AppCompatActivity implements Serializable {

    FirebaseDatabase database;
    DatabaseReference employersReference;

    public EmployerRegisterActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);

        Button registerButton = findViewById(R.id.newEmployerRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employerRegister();
            }
        });

        database = FirebaseDatabase.getInstance("https://csci3130-group6-project-default-rtdb.firebaseio.com/");
        employersReference = database.getReference("users").child("employers");
    }

    String getUsername(){
        EditText usernameBox = findViewById(R.id.newEmployerUsername);
        return usernameBox.getText().toString().trim();
    }

    String getEmail() {
        EditText emailBox = findViewById(R.id.newEmployerEmail);
        return emailBox.getText().toString().trim();
    }

    String getFirstName(){
        EditText firstNameBox = findViewById(R.id.newEmployerFirstName);
        return firstNameBox.getText().toString().trim();
    }

    String getLastname(){
        EditText lastNameBox = findViewById(R.id.newEmployerLastName);
        return lastNameBox.getText().toString().trim();
    }

    String getCreditCardNumber(){
        EditText creditCardNumberBox = findViewById(R.id.newEmployerCreditCardNumber);
        return creditCardNumberBox.getText().toString().trim();
    }

    String getCreditCardExpiry(){
        EditText creditCardExpiryBox = findViewById(R.id.newEmployerCreditCardExpiry);
        return creditCardExpiryBox.getText().toString().trim();
    }

    String getCreditCardCVV(){
        EditText creditCardCVVBox = findViewById(R.id.newEmployerCreditCardCVV);
        return creditCardCVVBox.getText().toString().trim();
    }

    String getPassword(){
        EditText passwordBox = findViewById(R.id.newEmployerPassword);
        return passwordBox.getText().toString().trim();
    }

    static boolean isUsernameEmpty(String username){
        return username.isEmpty();
    }

    static boolean isEmailEmpty(String email){return email.isEmpty();}

    static boolean isPasswordEmpty(String password){
        return password.isEmpty();
    }

    static boolean isCreditCardNumberEmpty(String creditCardNumber){
        return creditCardNumber.isEmpty();
    }

    static boolean isCreditCardExpiryEmpty(String creditCardExpiry){
        return creditCardExpiry.isEmpty();
    }

    static boolean isCreditCardCVVEmpty(String creditCardCVV){
        return creditCardCVV.isEmpty();
    }

    static boolean isFirstNameEmpty(String firstName) {
        return firstName.isEmpty();
    }

    static boolean isLastNameEmpty(String lastName) {
        return lastName.isEmpty();
    }

    static boolean isValidEmailAddress(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isCreditCardValid(String cardNumber, String expireDate, String cvv) {
        // field empty checks
        if (isCreditCardNumberEmpty(cardNumber)){
            showEmptyFieldToast("Credit Card Number");
            return false;
        }
        else if (isCreditCardExpiryEmpty(expireDate)){
            showEmptyFieldToast("Credit Card Expiry");
            return false;
        }
        else if (isCreditCardCVVEmpty(cvv)){
            showEmptyFieldToast("Credit Card CVV");
            return false;
        }

        // field length checks
        if (cardNumber.length() != 16) {
            Toast.makeText(getApplicationContext(),"Card Number should have 16 digits",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (expireDate.length() != 4) {
            Toast.makeText(getApplicationContext(),"MM/YY should have 4 digits",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(expireDate.substring(2)) > 12) {
            Toast.makeText(getApplicationContext(),"MM has a wrong format",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cvv.length() != 3) {
            Toast.makeText(getApplicationContext(),"cvv should have 3 digits",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        // card expire date check
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMM");
            int now = Integer.parseInt(LocalDate.now().format(dtf));
            int expireDateInt = Integer.parseInt(expireDate);
            if (now >= expireDateInt) {
                Toast.makeText(getApplicationContext(),"Card has expired",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    void switchToDashboard(Employer employer){
        Intent intent = new Intent(this, EmployerDashboardActivity.class);
        intent.putExtra("userNode",employer);
        intent.putExtra("username", employer);
        startActivity(intent);
    }

    void showEmptyFieldToast(String fieldName){
        Toast.makeText(this, fieldName + " is a required field!", Toast.LENGTH_LONG).show();
    }


    protected void employerRegister() {
        String username = getUsername();
        String email = getEmail();
        String firstName = getFirstName();
        String lastName = getLastname();
        String creditCardNumber = getCreditCardNumber();
        String creditCardExpiry = getCreditCardExpiry();
        String creditCardCVV = getCreditCardCVV();
        String password = getPassword();

        //Make sure important fields are not empty
        if (isUsernameEmpty(username)){
            showEmptyFieldToast("Username");
            return;
        }
        else if (isEmailEmpty(email)){
            showEmptyFieldToast("Email");
            return;
        }
        else if (isPasswordEmpty(password)){
            showEmptyFieldToast("Password");
            return;
        }
        else if (isFirstNameEmpty(firstName)) {
            showEmptyFieldToast("First Name");
            return;
        }
        else if (isLastNameEmpty(lastName)) {
            showEmptyFieldToast("Last Name");
            return;
        }
        else if (!isCreditCardValid(creditCardNumber,creditCardExpiry,creditCardCVV)) {
            return;
        }
        else if (!isValidEmailAddress(email)) {
            Toast.makeText(this, email + " is not a valid email address!", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!PasswordCheck.checkPasswordLength(password, 8)) {
            Toast.makeText(this, "password length must be greater than 8 characters", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!PasswordCheck.checkContainCase(password) || !PasswordCheck.checkContainDigit(password) ||
                !PasswordCheck.checkContainLowerCase(password) || !PasswordCheck.checkContainUpperCase(password) ||
                !PasswordCheck.checkContainSpecialChar(password)) {
            Toast.makeText(this, "Does not contain both uppercase letters, lowercase letters, numbers and special characters and therefore does not met requirements", Toast.LENGTH_LONG).show();
            return;
        }

        Employer employer = new Employer(username,email,firstName,lastName,
                creditCardNumber,creditCardExpiry,creditCardCVV,password);
        validateDuplicate(employer);
    }

    protected void validateDuplicate(Employer employer) {
        employersReference.child(employer.username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employer dataBaseEmployer = snapshot.getValue(Employer.class);
                if (dataBaseEmployer == null) {
                    employersReference.child(employer.username).setValue(employer);
                    switchToDashboard(employer);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Username already exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
