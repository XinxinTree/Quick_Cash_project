package com.example.csci3130_group_6_project;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;


public class SearchJobsTest {

    static JobFilter firstJobFilter;
    static JobFilter secondJobFilter;
    static ArrayList<Job> jobs;
    static Job firstJob;
    static Job secondJob;

    @BeforeClass
    public static void setup() {
        jobs = new ArrayList<>();
        firstJob = new Job(Enums.jobTypes.PCRepair.toString(),"","","100",
                "Halifax","High",50,"employer","employee");
        secondJob = new Job(Enums.jobTypes.babysitting.toString(),"","","80",
                "Vancouver","High",100,"employer","employee");
        jobs.add(firstJob);
        jobs.add(secondJob);
        Preference firstPreference = new Preference(Enums.jobTypes.PCRepair.toString(),"20","70",
                "100","Halifax");
        Preference secondPreference = new Preference(Enums.jobTypes.babysitting.toString(),"80","120",
                "80","Vancouver");
        firstJobFilter = new JobFilter(jobs,firstPreference);
        secondJobFilter = new JobFilter(jobs,secondPreference);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testFilterJobType() {
        assertTrue(firstJobFilter.filterJobType(firstJob,Enums.jobTypes.PCRepair.toString()));
        assertTrue(secondJobFilter.filterJobType(secondJob,Enums.jobTypes.babysitting.toString()));
        assertFalse(firstJobFilter.filterJobType(firstJob,Enums.jobTypes.babysitting.toString()));
        assertFalse(secondJobFilter.filterJobType(secondJob,Enums.jobTypes.PCRepair.toString()));
    }

    @Test
    public void testFilterSalaryRange() {
        assertTrue(firstJobFilter.filterSalaryRange(firstJob,"20","70"));
        assertFalse(firstJobFilter.filterSalaryRange(firstJob,"80","120"));
        assertTrue(secondJobFilter.filterSalaryRange(secondJob,"80","120"));
        assertFalse(secondJobFilter.filterSalaryRange(secondJob,"20","70"));
    }

    @Test
    public void testFilterJobDuration() {
        assertTrue(firstJobFilter.filterDuration(firstJob,"100"));
        assertFalse(firstJobFilter.filterDuration(firstJob,"80"));
        assertTrue(secondJobFilter.filterDuration(secondJob,"80"));
        assertFalse(secondJobFilter.filterDuration(secondJob,"100"));
    }

    @Test
    public void testFilterJobPlace() {
        assertTrue(firstJobFilter.filterJobPlace(firstJob,"Halifax"));
        assertFalse(firstJobFilter.filterJobPlace(firstJob,"Vancouver"));
        assertTrue(secondJobFilter.filterJobPlace(secondJob,"Vancouver"));
        assertFalse(secondJobFilter.filterJobPlace(secondJob,"Halifax"));
    }

    @Test
    public void testFilterJobs() {
        ArrayList<Job> firstFilteredJobs = firstJobFilter.filterJobs();
        assertEquals(firstFilteredJobs.size(),1);
        Job firstJob = firstFilteredJobs.get(0);
        assertEquals(firstJob.jobType,Enums.jobTypes.PCRepair.toString());
        assertEquals(firstJob.duration,"100");
        assertEquals(firstJob.place,"Halifax");

        ArrayList<Job> secondFilteredJobs = secondJobFilter.filterJobs();
        assertEquals(secondFilteredJobs.size(),1);
        Job secondJob = secondFilteredJobs.get(0);
        assertEquals(secondJob.jobType,Enums.jobTypes.babysitting.toString());
        assertEquals(secondJob.duration,"80");
        assertEquals(secondJob.place,"Vancouver");

    }
}
