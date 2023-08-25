package com.example.csci3130_group_6_project;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

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
    public void checkIfLandingPageIsVisible() {
        onView(withId(R.id.button_employer)).check(matches(withText(R.string.i_m_a_employer)));
        onView(withId(R.id.button_employee)).check(matches(withText(R.string.i_m_a_employee)));
    }
    @Test
    public void checkIfSwitchedToEmployerAccessPage(){
        onView(withId(R.id.button_employer)).perform(click());
        intended(hasComponent(EmployerAccessActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchedToEmployerRegistrationPage() {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerRegisterPageButton)).perform(click());
        intended(hasComponent(EmployerRegisterActivity.class.getName()),times(3));
    }

    @Test
    public void checkIfSwitchedToEmployerLoginPage() {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        intended(hasComponent(EmployerLoginActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchedToEmployeeAccessPage(){
        onView(withId(R.id.button_employee)).perform(click());
        intended(hasComponent(EmployeeAccessActivity.class.getName()),times(6));
    }

    @Test
    public void checkIfSwitchedToEmployeeRegistrationPage () {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeRegisterPageButton)).perform(click());
        intended(hasComponent(EmployeeRegisterActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchedToEmployeeLoginPage () {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        intended(hasComponent(EmployeeLoginActivity.class.getName()));
    }

    @Test
    public void checkIfEmployeeRegistered () {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployeeUsername)).perform(typeText("Username"));
        onView(withId(R.id.newEmployeeEmail)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.newEmployeeFirstName)).perform(typeText("First"));
        onView(withId(R.id.newEmployeeLastName)).perform(typeText("Last"));
        onView(withId(R.id.newEmployeeCreditCardNumber)).perform(typeText("1234123412341234"));
        onView(withId(R.id.newEmployeeCreditCardExpiry)).perform(typeText("2312"));
        onView(withId(R.id.newEmployeeCreditCardCVV)).perform(replaceText("123"));
        onView(withId(R.id.newEmployeePassword)).perform(replaceText("!Password12"), closeSoftKeyboard());
        onView(withId(R.id.newEmployeeRegisterButton)).perform(click());
        intended(hasComponent(EmployeeDashboardActivity.class.getName()),times(1));
    }

    @Test
    public void checkIfEmployerRegistered () {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployerUsername)).perform(replaceText("Username"));
        onView(withId(R.id.newEmployerEmail)).perform(replaceText("test@dal.ca"));
        onView(withId(R.id.newEmployerFirstName)).perform(replaceText("First"));
        onView(withId(R.id.newEmployerLastName)).perform(replaceText("Last"));
        onView(withId(R.id.newEmployerCreditCardNumber)).perform(replaceText("1234123412341234"));
        onView(withId(R.id.newEmployerCreditCardExpiry)).perform(replaceText("2312"));
        onView(withId(R.id.newEmployerCreditCardCVV)).perform(replaceText("123"));
        onView(withId(R.id.newEmployerPassword)).perform(replaceText("!Password12"), closeSoftKeyboard());
        onView(withId(R.id.newEmployerRegisterButton)).perform(click());
        intended(hasComponent(EmployerDashboardActivity.class.getName()),times(0));
    }


    @Test
    public void checkIfEmployerRegisterEmailIsInvalid () {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployerUsername)).perform(replaceText("Username"));
        onView(withId(R.id.newEmployerEmail)).perform(replaceText("test@123456"));
        onView(withId(R.id.newEmployerFirstName)).perform(replaceText("First"));
        onView(withId(R.id.newEmployerLastName)).perform(replaceText("Last"));
        onView(withId(R.id.newEmployerCreditCardNumber)).perform(replaceText("1234123412341234"));
        onView(withId(R.id.newEmployerCreditCardExpiry)).perform(replaceText("2312"));
        onView(withId(R.id.newEmployerCreditCardCVV)).perform(replaceText("123"));
        onView(withId(R.id.newEmployerPassword)).perform(replaceText("Password123!"), closeSoftKeyboard());
        onView(withId(R.id.newEmployerRegisterButton)).perform(click());
        intended(hasComponent(EmployerRegisterActivity.class.getName()));
    }

    @Test
    public void checkIfEmployerRegisterPasswordIsInvalid () {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployerUsername)).perform(replaceText("Username"));
        onView(withId(R.id.newEmployerEmail)).perform(replaceText("test@dal.ca"));
        onView(withId(R.id.newEmployerFirstName)).perform(replaceText("First"));
        onView(withId(R.id.newEmployerLastName)).perform(replaceText("Last"));
        onView(withId(R.id.newEmployerCreditCardNumber)).perform(replaceText("1234123412341234"));
        onView(withId(R.id.newEmployerCreditCardExpiry)).perform(replaceText("2312"));
        onView(withId(R.id.newEmployerCreditCardCVV)).perform(replaceText("123"));
        onView(withId(R.id.newEmployerPassword)).perform(replaceText("Password"), closeSoftKeyboard());
        onView(withId(R.id.newEmployerRegisterButton)).perform(click());
        intended(hasComponent(EmployerRegisterActivity.class.getName()),times(5));
    }

    @Test
    public void checkIfEmployeeRegisteredEmailIsInvalid () {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployeeUsername)).perform(replaceText("Username"));
        onView(withId(R.id.newEmployeeEmail)).perform(replaceText("test@123456"));
        onView(withId(R.id.newEmployeeFirstName)).perform(replaceText("First"));
        onView(withId(R.id.newEmployeeLastName)).perform(replaceText("Last"));
        onView(withId(R.id.newEmployeeCreditCardNumber)).perform(replaceText("1234123412341234"));
        onView(withId(R.id.newEmployeeCreditCardExpiry)).perform(replaceText("2312"));
        onView(withId(R.id.newEmployeeCreditCardCVV)).perform(replaceText("123"));
        onView(withId(R.id.newEmployeePassword)).perform(replaceText("Password123!"), closeSoftKeyboard());
        onView(withId(R.id.newEmployeeRegisterButton)).perform(click());
        intended(hasComponent(EmployeeRegisterActivity.class.getName()),times(2));
    }

    @Test
    public void checkIfEmployeeRegisteredPasswordIsInvalid () {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployeeUsername)).perform(replaceText("Username"));
        onView(withId(R.id.newEmployeeEmail)).perform(replaceText("test@dal.ca"));
        onView(withId(R.id.newEmployeeFirstName)).perform(replaceText("First"));
        onView(withId(R.id.newEmployeeLastName)).perform(replaceText("Last"));
        onView(withId(R.id.newEmployeeCreditCardNumber)).perform(replaceText("1234123412341234"));
        onView(withId(R.id.newEmployeeCreditCardExpiry)).perform(replaceText("2312"));
        onView(withId(R.id.newEmployeeCreditCardCVV)).perform(replaceText("123"));
        onView(withId(R.id.newEmployeePassword)).perform(replaceText("Password123"), closeSoftKeyboard());
        onView(withId(R.id.newEmployeeRegisterButton)).perform(click());
        intended(hasComponent(EmployerRegisterActivity.class.getName()),times(3));
    }
    @Test
    public void checkIfEmployeeDashboardSwitchedToMain() {
        onView(withId(R.id.button_employee)).perform(click());
        onView(withId(R.id.switchToEmployeeLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("Username"));
        onView(withId(R.id.password)).perform(typeText("!Password12"),closeSoftKeyboard());
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.employeeDashboardLogout)).perform();
        intended(hasComponent(EmployeeDashboardActivity.class.getName()));
    }

    public void checkIfEmployerDashboardSwitchedToMain() {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("Username"));
        onView(withId(R.id.password)).perform(typeText("!Password12"),closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.employeeDashboardLogout)).perform();
        intended(hasComponent(EmployeeDashboardActivity.class.getName()));
    }

    @Test
    public void checkIfEmployerLoggedIn () {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerLoginPageButton)).perform(click());
        onView(withId(R.id.employerUsername)).perform(typeText("Username"));
        onView(withId(R.id.employerPassword)).perform(typeText("!Password12"), closeSoftKeyboard());
        onView(withId(R.id.employerLoginButton)).perform(click());
        intended(hasComponent(EmployerDashboardActivity.class.getName()),times(1));
    }

    @Test
    public void testCreditCardValidation() {
        onView(withId(R.id.button_employer)).perform(click());
        onView(withId(R.id.switchToEmployerRegisterPageButton)).perform(click());
        onView(withId(R.id.newEmployerCreditCardNumber)).perform(typeText("1234567890123456"));
        onView(withId(R.id.newEmployerCreditCardExpiry)).perform(typeText("2312"));
        onView(withId(R.id.newEmployerCreditCardCVV)).perform(typeText("123"));
        onView(withId(R.id.newEmployerRegisterButton)).perform(click());
        intended(hasComponent(EmployerRegisterActivity.class.getName()),times(2));
    }
}