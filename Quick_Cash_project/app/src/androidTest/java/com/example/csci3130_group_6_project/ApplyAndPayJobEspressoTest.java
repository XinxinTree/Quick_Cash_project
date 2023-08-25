package com.example.csci3130_group_6_project;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.view.View;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class ApplyAndPayJobEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    public IntentsTestRule<MainActivity> myIntentRule = new IntentsTestRule<>(MainActivity.class);

    private View decorView;


    @Before
    public void setUp() {
        myRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


    public void insertTestJob() {
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(replaceText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(replaceText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(replaceText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardSubmitNewJob)).perform(click());

        onView(withId(R.id.newJobDescription)).perform(replaceText(" Test Description"), closeSoftKeyboard());
        onView(withId(R.id.newJobDate)).perform(replaceText("2023-10-22"),closeSoftKeyboard());
        onView(withId(R.id.newJobPlace)).perform(replaceText("Killam"), closeSoftKeyboard());
        onView(withId(R.id.newJobSalary)).perform(replaceText("200"), closeSoftKeyboard());
        onView(withId(R.id.newJobDuration)).perform(replaceText("2 hours"), closeSoftKeyboard());
        onView(withId(R.id.newJobSubmitJobButton)).perform(click());
        onView(withId(R.id.employerDashboardLogout)).perform(click());
    }

    @Test
    public void checkIfEmployeeSwitchedToJobDetailPage() {
        // Insert a job for test
        insertTestJob();

        // Setup to get to correct position
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.jobsListView)).atPosition(0).perform(click());
        intended(hasComponent(JobDetailActivity.class.getName()),times(3));
    }

    @Test
    public void checkIfEmployerSwitchedToJobDetailPage() {
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardViewSubmittedJobsButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.EmployerJobsSubmittedListView)).atPosition(0).perform(click());
        intended(hasComponent(JobDetailActivity.class.getName()));
    }

    @Test
    public void checkIfEmployeeCanSeeApplyJobButton() {
        // Setup to get to correct position
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.jobsListView)).atPosition(0).perform(click());

        // Apply button should be displayed, and Pay button should not be displayed
        onView(withId(R.id.applyButton)).check(matches(isDisplayed()));
        onView(withId(R.id.payButton)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkIfEmployerCanSeePayButton() {
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardViewSubmittedJobsButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.EmployerJobsSubmittedListView)).atPosition(0).perform(click());

        // Apply button should not be displayed, and Pay button should be displayed
        onView(withId(R.id.payButton)).check(matches(isDisplayed()));
        onView(withId(R.id.applyButton)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkIfEmployeeCanApplyJob() {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());

        // Check to make sure the list view matches what is intended
        final int[] countsBeforeApply = new int[1];
        onView(withId(R.id.jobsListView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                // Check if the listView count matches
                ListView listView = (ListView) view;

                countsBeforeApply[0] = listView.getCount();

                return true;
            }
            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.jobsListView)).atPosition(0).perform(click());
        onView(withId(R.id.applyButton)).perform(click());

        // Check to make sure the list view matches what is intended
        final int[] countsAfterApply = new int[1];
        onView(withId(R.id.jobsListView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                // Check if the listView count matches
                ListView listView = (ListView) view;

                countsAfterApply[0] = listView.getCount();

                return true;
            }
            @Override
            public void describeTo(Description description) {

            }
        }));

        // Assert the counts before and after match
        assert countsBeforeApply[0] != countsAfterApply[0];
    }

    public void checkIfEmployerCanPay() {

    }

}
