package com.example.csci3130_group_6_project;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Job implements Serializable {

    public String jobType;
    public String description;
    public String date;
    public String duration;
    public String place;
    public String urgency;
    public double salary;
    public boolean isOpen;
    public String employer;
    public String employee;
    public String key;
    public boolean isPaid;

    public Job() {

    }

    public Job(String jobType, String description, String date, String duration,
               String place, String urgency, double salary, String employer, String employee) {
        this.jobType = jobType;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.place = place;
        this.urgency = urgency;
        this.salary = salary;
        this.employer = employer;
        this.employee = employee;
        this.isOpen = true;
    }

    @NonNull
    @Override
    public String toString() {
        return jobType + " " + employer;
    }

    ArrayList<String> toStringArray() {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("Job type:  " + jobType);
        attributes.add("Description:  " + description);
        attributes.add("Date:  " + date);
        attributes.add("Duration:  " + duration);
        attributes.add("Place:  " + place);
        attributes.add("Urgency:  " + urgency);
        attributes.add("Salary:  " + salary);
        attributes.add("Employer:  " + employer);
        attributes.add("Employee:  " + employee);
        return attributes;
    }

}
