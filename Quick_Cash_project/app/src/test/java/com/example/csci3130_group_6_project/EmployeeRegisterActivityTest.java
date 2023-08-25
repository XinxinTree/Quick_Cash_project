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
public class EmployeeRegisterActivityTest {

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


    @Test
    public void checkIsUsernameEmpty() {
            assertTrue(EmployeeRegisterActivity.isUsernameEmpty(""));
            assertFalse(EmployeeRegisterActivity.isUsernameEmpty("Username"));
    }

    @Test
    public void checkIsPasswordEmpty() {
        assertTrue(EmployeeRegisterActivity.isPasswordEmpty(""));
        assertFalse(EmployeeRegisterActivity.isPasswordEmpty("Password"));
    }

    @Test
    public void checkIsCreditCardNumberEmpty() {
        assertTrue(EmployeeRegisterActivity.isCreditCardNumberEmpty(""));
        assertFalse(EmployeeRegisterActivity.isCreditCardNumberEmpty("0000000000000000"));
    }

    @Test
    public void checkIsCreditCardExpiryEmpty() {
        assertTrue(EmployeeRegisterActivity.isCreditCardExpiryEmpty(""));
        assertFalse(EmployeeRegisterActivity.isCreditCardExpiryEmpty("00/00"));
    }

    @Test
    public void checkIsCreditCardCVVEmpty() {
        assertTrue(EmployeeRegisterActivity.isCreditCardCVVEmpty(""));
        assertFalse(EmployeeRegisterActivity.isCreditCardCVVEmpty("000"));
    }

    @Test
    public void isFirstNameEmpty() {
        assertTrue(EmployeeRegisterActivity.isFirstNameEmpty(""));
        assertFalse(EmployeeRegisterActivity.isFirstNameEmpty("First"));
    }

    @Test
    public void isLastNameEmpty() {
        assertTrue(EmployeeRegisterActivity.isLastNameEmpty(""));
        assertFalse(EmployeeRegisterActivity.isLastNameEmpty("Last"));
    }

    @Test
    public void isEmailEmpty() {
        assertTrue(EmployeeRegisterActivity.isEmailEmpty(""));
        assertFalse(EmployeeRegisterActivity.isEmailEmpty("email"));
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(EmployeeRegisterActivity.isValidEmailAddress("abc123@dal.ca"));
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