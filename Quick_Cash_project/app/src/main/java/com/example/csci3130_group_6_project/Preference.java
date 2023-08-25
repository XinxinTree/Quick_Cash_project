package com.example.csci3130_group_6_project;

import java.io.Serializable;

public class Preference implements Serializable {

    public String jobType;

    public String lowestSalary;

    public String highestSalary;

    public String duration;
    public String place;

    public Preference() {

    }
    public Preference (String jobType, String lowestSalary, String highestSalary, String duration, String place) {
        this.jobType = jobType;
        this.lowestSalary = lowestSalary;
        this.highestSalary = highestSalary;
        this.duration = duration;
        this.place = place;
    }
}
