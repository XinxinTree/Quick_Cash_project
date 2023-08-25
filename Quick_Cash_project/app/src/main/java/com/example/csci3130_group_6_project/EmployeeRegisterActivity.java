package com.example.csci3130_group_6_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;
import androidx.test.espresso.remote.EspressoRemoteMessage;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeRegisterActivity extends AppCompatActivity implements Serializable {

    FirebaseDatabase database;
    DatabaseReference employeesReference;

    public EmployeeRegisterActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        Button registerButton = findViewById(R.id.newEmployeeRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeRegister();
            }
        });

        database = FirebaseDatabase.getInstance("https://csci3130-group6-project-default-rtdb.firebaseio.com/");
        employeesReference = database.getReference("users").child("employees");
    }

    String getUsername(){
        EditText usernameBox = findViewById(R.id.newEmployeeUsername);
        return usernameBox.getText().toString().trim();
    }

    String getEmail() {
        EditText emailBox = findViewById(R.id.newEmployeeEmail);
        return emailBox.getText().toString().trim();
    }

    String getFirstName(){
        EditText firstNameBox = findViewById(R.id.newEmployeeFirstName);
        return firstNameBox.getText().toString().trim();
    }

    String getLastname(){
        EditText lastNameBox = findViewById(R.id.newEmployeeLastName);
        return lastNameBox.getText().toString().trim();
    }

    String getCreditCardNumber(){
        EditText creditCardNumberBox = findViewById(R.id.newEmployeeCreditCardNumber);
        return creditCardNumberBox.getText().toString().trim();
    }

    String getCreditCardExpiry(){
        EditText creditCardExpiryBox = findViewById(R.id.newEmployeeCreditCardExpiry);
        return creditCardExpiryBox.getText().toString().trim();
    }

    String getCreditCardCVV(){
        EditText creditCardCVVBox = findViewById(R.id.newEmployeeCreditCardCVV);
        return creditCardCVVBox.getText().toString().trim();
    }

    String getPassword(){
        EditText passwordBox = findViewById(R.id.newEmployeePassword);
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

    static  boolean isCreditCardCVVEmpty(String creditCardCVV){
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

    void showEmptyFieldToast(String fieldName){
        Toast.makeText(this, fieldName + " is a required field!", Toast.LENGTH_LONG).show();

    }

    void switchToDashboard(Employee employee){
        Intent intent = new Intent(this, EmployeeDashboardActivity.class);
        intent.putExtra("userNode",employee);
        startActivity(intent);
    }

    protected void employeeRegister() {
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

        Employee employee = new Employee(username,email,firstName,lastName,
                creditCardNumber,creditCardExpiry,creditCardCVV,password);
        validateDuplicate(employee);
    }

    protected void validateDuplicate(Employee employee) {
        employeesReference.child(employee.username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee dataBaseEmployee = snapshot.getValue(Employee.class);
                if (dataBaseEmployee == null) {
                    employeesReference.child(employee.username).setValue(employee);
                    switchToDashboard(employee);
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
