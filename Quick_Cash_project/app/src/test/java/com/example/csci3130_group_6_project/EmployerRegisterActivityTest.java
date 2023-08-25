package com.example.csci3130_group_6_project;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmployerRegisterActivityTest {

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


    @Test
    public void checkIsUsernameEmpty() {
        assertTrue(EmployerRegisterActivity.isUsernameEmpty(""));
        assertFalse(EmployerRegisterActivity.isUsernameEmpty("Username"));
    }

    @Test
    public void checkIsPasswordEmpty() {
        assertTrue(EmployerRegisterActivity.isPasswordEmpty(""));
        assertFalse(EmployerRegisterActivity.isPasswordEmpty("Password"));
    }

    @Test
    public void checkIsCreditCardNumberEmpty() {
        assertTrue(EmployerRegisterActivity.isCreditCardNumberEmpty(""));
        assertFalse(EmployerRegisterActivity.isCreditCardNumberEmpty("0000000000000000"));
    }

    @Test
    public void checkIsCreditCardExpiryEmpty() {
        assertTrue(EmployerRegisterActivity.isCreditCardExpiryEmpty(""));
        assertFalse(EmployerRegisterActivity.isCreditCardExpiryEmpty("00/00"));
    }

    @Test
    public void checkIsCreditCardCVVEmpty() {
        assertTrue(EmployerRegisterActivity.isCreditCardCVVEmpty(""));
        assertFalse(EmployerRegisterActivity.isCreditCardCVVEmpty("000"));
    }

    @Test
    public void isFirstNameEmpty() {
        assertTrue(EmployerRegisterActivity.isFirstNameEmpty(""));
        assertFalse(EmployerRegisterActivity.isFirstNameEmpty("First"));
    }

    @Test
    public void isLastNameEmpty() {
        assertTrue(EmployerRegisterActivity.isLastNameEmpty(""));
        assertFalse(EmployerRegisterActivity.isLastNameEmpty("Last"));
    }

    @Test
    public void isEmailEmpty() {
        assertTrue(EmployerRegisterActivity.isEmailEmpty(""));
        assertFalse(EmployerRegisterActivity.isEmailEmpty("email"));
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(EmployerRegisterActivity.isValidEmailAddress("abc123@dal.ca"));
    }

    @Test
    public void checkIfEmailIsInValid() {
        assertFalse(EmployeeRegisterActivity.isValidEmailAddress("abc123@123456"));
        assertFalse(EmployeeRegisterActivity.isValidEmailAddress("ab564646"));
    }

    @Test
    public void checkIfPasswordHasSpecialChar() {
        assertTrue(PasswordCheck.checkContainSpecialChar("Abc123!"));
        assertFalse(PasswordCheck.checkContainSpecialChar("Abc123"));
    }

    @Test
    public void checkIfPasswordContainDigit() {
        assertTrue(PasswordCheck.checkContainDigit("Abcdef123!"));
        assertFalse(PasswordCheck.checkContainDigit("Abcdef!"));
    }

    @Test
    public void checkIfPasswordContainLowerCase() {
        assertTrue(PasswordCheck.checkContainLowerCase("Abcdef123!"));
        assertFalse(PasswordCheck.checkContainLowerCase("ABCDEF123!"));
    }

    @Test
    public void checkIfPasswordContainUpperCase() {
        assertTrue(PasswordCheck.checkContainUpperCase("Abcdef123!"));
        assertFalse(PasswordCheck.checkContainUpperCase("abcdef123!"));
    }
}