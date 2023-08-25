package com.example.csci3130_group_6_project;

import java.util.ArrayList;

public class JobFilter{

    private ArrayList<Job> jobs;
    private Preference preference;

    public JobFilter(ArrayList<Job> jobs, Preference preference) {
        this.jobs = jobs;
        this.preference = preference;
    }

    // A method to check the type of the job
    public boolean filterJobType(Job job, String jobType) {
        // Make sure jobtype is not empty or null
        if (jobType == null || jobType.equals("")) {
            return true;
        }
        return job.jobType.equals(jobType);
    }

    // A method to check the range of salary for the job
    public boolean filterSalaryRange(Job job, String lowestSalary, String highestSalary) {
        // Make sure fields are not empty
        if (lowestSalary == null || lowestSalary.equals("")) {
            return true;
        }
        if (highestSalary == null || highestSalary.equals("")) {
            return true;
        }
        return job.salary < Double.parseDouble(highestSalary) && job.salary > Double.parseDouble(lowestSalary);
    }

    // A method to filter the place of the job to make sure it matches
    public static boolean filterJobPlace(Job job, String place) {
        // Make sure place is not null or empty
        if (place == null || place.equals("")) {
            return true;
        }
        return job.place.equals(place);
    }

    // A method to filer the duration of the job
    public  boolean filterDuration(Job job, String duration) {
        // Make sure duration is not null or empty
        if (duration == null || duration.equals("")) {
            return true;
        }
        return job.duration.equals(duration);
    }

    // A method that calls all filer methods using the parameters of the job
    public boolean filterJob(Job job, Preference preference) {
        return filterJobType(job,preference.jobType) &&
                filterSalaryRange(job,preference.lowestSalary,preference.highestSalary) &&
                filterDuration(job,preference.duration);
    }

    // Fill the jobs arraylist
    public ArrayList<Job> filterJobs() {
        ArrayList<Job> filteredJobs = new ArrayList<>();
        for (Job job:jobs
             ) {
            if (filterJob(job,preference)) {
                filteredJobs.add(job);
            }
        }
        return filteredJobs;
    }
}
