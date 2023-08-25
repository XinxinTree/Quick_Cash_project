package com.example.csci3130_group_6_project;

import java.io.Serializable;

public class Employer implements Serializable {
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String creditCardNumber;
    public String creditCardExpiry;
    public String creditCardCVV;
    public String password;

    public Employer() {

    }

    public Employer(String username, String email, String firstName, String lastName,
                    String creditCardNumber, String creditCardExpiry, String creditCardCVV,
                    String password) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.creditCardCVV = creditCardCVV;
        this.password = password;
    }
}
