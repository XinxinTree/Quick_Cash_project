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
public class EmployerViewJobEspressoTest {
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
    //Iteration 2,User story 3, UAT-1
    @Test
    public void CheckIfRedirectedOnViewButtonPress(){
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardViewSubmittedJobsButton)).perform(click());
        intended(hasComponent(EmployerViewSubmittedJobsActivity.class.getName()));

    }

    //Iteration 2, User story 3, UAT-2
    @Test
    public void CheckIfRedirectedOnCloseButtonPress(){
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("EmployerEspressoTestUser"));
        onView(withId(R.id.employerPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.employerCredit)).perform(typeText("0000000000000000"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employerDashboardViewSubmittedJobsButton)).perform(click());
        intended(hasComponent(EmployerViewSubmittedJobsActivity.class.getName()),times(2));
        onView(withId(R.id.employerViewSubmittedJobsCloseButton)).perform(click());
        intended(hasComponent(EmployerDashboardActivity.class.getName()),times(3));
    }
    //Iteration 2, User story 3, UAT-3
    @Test
    public void CheckIfJobsAreListed(){ }




}
