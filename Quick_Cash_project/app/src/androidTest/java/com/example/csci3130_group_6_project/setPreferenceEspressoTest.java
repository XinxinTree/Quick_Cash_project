package com.example.csci3130_group_6_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;
import java.util.regex.Matcher;

public class setPreferenceEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    private View decorView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


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

    @Test
    public void checkIfSwitchedToJobsPage() {
        // Check to make sure the user is switched to the go to job list page on new page on the button press
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());
        intended(hasComponent(JobSearchingActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchedToUpdatePreferencePage() {
        // Check to see if the user is switch to the update preference page on a button press
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());
        onView(withId(R.id.employeeSetProfile)).perform(click());
        intended(hasComponent(SetPreferenceActivity.class.getName()),times(2));
    }

    @Test
    public void checkIfEmployeeUpdatesPreference() {
        // Test to make sure the preference is saved in the database
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());
        onView(withId(R.id.employeeSetProfile)).perform(click());
        onView(withId(R.id.jobType)).perform(click());
        onView(withText("Repairing a computer")).perform(click());
        onView(withId(R.id.lowestSalary)).perform(replaceText("0"));
        onView(withId(R.id.highestSalary)).perform(replaceText("100"));
        onView(withId(R.id.place)).perform(replaceText("Halifax"));
        onView(withId(R.id.employeeSetPreferenceButton)).perform(click());
        DatabaseReference reference = database.getReference("users").child("employees").
                child("Username");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee employee = snapshot.getValue(Employee.class);
                assert Objects.requireNonNull(employee).preference != null;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Test
    public void checkIfEmployeeCanApplyPreference() {
        // Test to make sure the preference is saved
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeGoToJobList)).perform(click());

        // Check to make sure the list view matches what is intended
        final int[] countsBeforeFilter = new int[1];
        onView(withId(R.id.jobsListView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                // Check if the listView count matches
                ListView listView = (ListView) view;

                countsBeforeFilter[0] = listView.getCount();

                return true;
            }
            @Override
            public void describeTo(Description description) {

            }
        }));

        onView(withId(R.id.applyPreference)).perform(click());


        // Check to make sure the list view matches what is intended
        final int[] countsAfterFilter = new int[1];
        onView(withId(R.id.jobsListView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                // Check if the listView count matches
                ListView listView = (ListView) view;

                countsAfterFilter[0] = listView.getCount();

                return true;
            }
            @Override
            public void describeTo(Description description) {

            }
        }));

        // Assert the counts before and after match
        assert countsBeforeFilter[0] != countsAfterFilter[0];
    }
}
