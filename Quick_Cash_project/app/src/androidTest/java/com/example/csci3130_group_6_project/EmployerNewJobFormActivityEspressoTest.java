package com.example.csci3130_group_6_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployerNewJobFormActivityEspressoTest {

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


    // Iteration 2, User story 2, UAT-1
    @Test
    public void CheckIfRedirectedOnButtonPress() {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardSubmitNewJob)).perform(click());
        intended(hasComponent(EmployerNewJobFormActivity.class.getName()),times(2));
    }

    // Iteration 2, User story 2, UAT-2
    @Test
    public void CheckIfNewJobActivityHasFields() {
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardSubmitNewJob)).perform(click());

        // New functionality being tested
        intended(hasComponent(EmployerNewJobFormActivity.class.getName()),times(5));
        onView(withId(R.id.newJobDescription)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.newJobDuration)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.newJobPlace)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.newJobSalary)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.newJobDate)).perform(typeText(""),closeSoftKeyboard());
    }

    //Iteration 2, user story 2, UAT-3
    @Test
    public void CheckIfEmptyFieldsCausesError(){
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardSubmitNewJob)).perform(click());

        intended(hasComponent(EmployerNewJobFormActivity.class.getName()),times(4));
        onView(withId(R.id.newJobDescription)).perform(typeText(" Test Description 12345"), closeSoftKeyboard());
        onView(withId(R.id.newJobDate)).perform(typeText("2023-10-22"),closeSoftKeyboard());
        onView(withId(R.id.newJobPlace)).perform(typeText("Killam"), closeSoftKeyboard());
        onView(withId(R.id.newJobSalary)).perform(typeText("200"), closeSoftKeyboard());
        onView(withId(R.id.newJobSubmitJobButton)).perform(click());

        // Make sure the page does not switch, since duration is empty
        intended(hasComponent(EmployerNewJobFormActivity.class.getName()),times(4));
    }

    // Iteration 2, User Story 2, UAT-4
    @Test
    public void CheckIfJobWasPosted(){
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardSubmitNewJob)).perform(click());

        intended(hasComponent(EmployerNewJobFormActivity.class.getName()),times(3));
        onView(withId(R.id.newJobDescription)).perform(typeText("Unique Test Description 12345"), closeSoftKeyboard());
        onView(withId(R.id.newJobDuration)).perform(typeText("2 hours"), closeSoftKeyboard());
        onView(withId(R.id.newJobDate)).perform(typeText("2023-10-22"),closeSoftKeyboard());
        onView(withId(R.id.newJobPlace)).perform(typeText("Killam"), closeSoftKeyboard());
        onView(withId(R.id.newJobSalary)).perform(typeText("200"), closeSoftKeyboard());
        onView(withId(R.id.newJobSubmitJobButton)).perform(click());

        // Check if the job exists in firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jobs");
        Query query = reference.orderByChild("description").equalTo("Unique Test Description 12345");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long numRecords = snapshot.getChildrenCount();

                assertTrue(numRecords > 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String errorRead = error.getMessage();
                System.err.println(errorRead);
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    // Iteration 2, User Story 2, UAT-5
    @Test
    public void CheckIfRedirectedToDashboardOnValidJob(){
        // Setup to get to correct position
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardSubmitNewJob)).perform(click());

        intended(hasComponent(EmployerNewJobFormActivity.class.getName()));
        onView(withId(R.id.newJobDescription)).perform(typeText(" Test Description"), closeSoftKeyboard());
        onView(withId(R.id.newJobDate)).perform(typeText("2023-10-22"),closeSoftKeyboard());
        onView(withId(R.id.newJobPlace)).perform(typeText("Killam"), closeSoftKeyboard());
        onView(withId(R.id.newJobSalary)).perform(typeText("200"), closeSoftKeyboard());
        onView(withId(R.id.newJobDuration)).perform(typeText("2 hours"), closeSoftKeyboard());
        onView(withId(R.id.newJobSubmitJobButton)).perform(click());

        // Make sure the page switches to the dashboard
        intended(hasComponent(EmployerDashboardActivity.class.getName()),times(2));
    }

}
