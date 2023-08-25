package com.example.csci3130_group_6_project;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserLocationEspressoTest {

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

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130_group_6_project", appContext.getPackageName());
    }

    @Test
    public void checkIfEmployeeLocationIsEmpty() {
        // Test to make sure the employee location is empty by default
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(replaceText("Username"));
        onView(withId(R.id.password)).perform(replaceText("!Password12"), closeSoftKeyboard());
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeLocation)).check(matches(not(withText(""))));
    }

    @Test
    public void checkIfEmployerLocationIsEmpty() {
        // Test to make sure the employee location is empty by default
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(replaceText("Username123"));
        onView(withId(R.id.employerPassword)).perform(replaceText("!Password12"));
        onView(withId(R.id.employerCredit)).perform(replaceText("1234123412341234"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerlocation)).check(matches(withText("")));
    }

}
