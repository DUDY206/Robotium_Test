package com.example.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String INPUT_USERNAME_LOGIN = "username";
    private static final String INPUT_PASSWORD_LOGIN = "1";
    private static String MAX_USER_ID = "17";
    private Solo solo;
    @Rule
    public ActivityTestRule<Login> activityRule
            = new ActivityTestRule<>(Login.class,false,false);

    @Before
    public void setUp() throws Exception {

        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityRule.getActivity());
//        Activity launchedActivity = activityRule.launchActivity(intent);
    }


    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void asd(){
        solo.unlockScreen();
        assertEquals("4","4");
    }


    @Test
    public void Test1_Empty_Info_Login() throws InterruptedException {
        Thread.sleep(5000);
        solo.clickOnView(solo.getView(R.id.login_userName));
        solo.enterText(0, "");
        solo.clickOnView(solo.getView(R.id.login_password));
        solo.enterText(0, "");

        solo.clickOnView(solo.getView(R.id.btn_Login));
        assertTrue("ERROR:", solo.searchText("User_name password required"));
    }
    @Test
    public void Test2_Wrong_Info_Login(){
        solo.clickOnView(solo.getView(R.id.login_userName));
        solo.enterText(0, "wrong_user_name");
        solo.clickOnView(solo.getView(R.id.login_password));
        solo.enterText(1, "wrong_password");
        solo.clickOnView(solo.getView(R.id.btn_Login));
        assertTrue("ERROR:", solo.searchText("Invalid user_name and password"));
    }
    @Test
    public void Test3_Empty_Info_Register(){
        solo.clickOnView(solo.getView(R.id.txt_register));
        solo.clickOnView(solo.getView(R.id.userName));
        solo.clearEditText(0);
        solo.clickOnView(solo.getView(R.id.btn_register));
        assertTrue("ERROR:", solo.searchText("User_name password required"));
    }
    @Test
    public void Test4_NotConfirmed_Password_Register(){
        solo.clickOnView(solo.getView(R.id.txt_register));
        solo.clickOnView(solo.getView(R.id.password));
        solo.enterText(1, "1");
        solo.clickOnView(solo.getView(R.id.confirmPassword));
        solo.enterText(2, "2");
        solo.clickOnView(solo.getView(R.id.btn_register));
        assertTrue("ERROR:", solo.searchText("Password is not confirmed"));
    }
    @Test
    public void Test5_NotAgreed_Terms_Register(){
        solo.clickOnView(solo.getView(R.id.txt_register));
        solo.clickOnView(solo.getView(R.id.password));
        solo.enterText(1, "1");
        solo.clickOnView(solo.getView(R.id.confirmPassword));
        solo.enterText(2, "1");
        solo.clickOnView(solo.getView(R.id.btn_register));
        assertTrue("ERROR:", solo.searchText("You have to agree all the terms"));
    }
    @Test
    public void Test6_Invalid_PhoneNumber(){
        solo.clickOnView(solo.getView(R.id.txt_register));
        solo.clickOnView(solo.getView(R.id.userName));
        solo.clearEditText(0);
        solo.enterText(0, INPUT_USERNAME_LOGIN+MAX_USER_ID);
        solo.enterText(1, "1");
        solo.enterText(2, "1");
        solo.clickOnCheckBox(0);
        solo.enterText(4, "abc");
        solo.clickOnView(solo.getView(R.id.btn_register));
        assertTrue("ERROR:", solo.searchText("Invalid phone number"));
    }
    @Test
    public void Test7_Register_Successfully(){
        solo.clickOnView(solo.getView(R.id.txt_register));
        solo.clickOnView(solo.getView(R.id.userName));
        solo.clearEditText(0);
        solo.enterText(0, INPUT_USERNAME_LOGIN+MAX_USER_ID);
        solo.enterText(1, "1");
        solo.enterText(2, "1");
        solo.clickOnCheckBox(0);
        solo.enterText(4, "123");

        solo.clickOnView(solo.getView(R.id.btn_register));
        assertTrue("SUCCESS:", solo.searchText("Username:"+INPUT_USERNAME_LOGIN+MAX_USER_ID));
    }
    @Test
    public void Test8_Register_ExistUser(){
        solo.clickOnView(solo.getView(R.id.txt_register));
        solo.clickOnView(solo.getView(R.id.userName));
        solo.clearEditText(0);
        solo.enterText(0, INPUT_USERNAME_LOGIN+MAX_USER_ID);
        solo.enterText(1, "1");
        solo.enterText(2, "1");
        solo.clickOnCheckBox(0);
        solo.enterText(4, "123");

        solo.clickOnView(solo.getView(R.id.btn_register));
        assertTrue("ERROR:", solo.searchText("Exist user_name"));
    }
    @Test
    public void Test9_Login_Successfully(){
        solo.enterText(0, INPUT_USERNAME_LOGIN+MAX_USER_ID);
        solo.enterText(1, "1");
        solo.clickOnView(solo.getView(R.id.btn_Login));
        assertTrue("SUCCESS:", solo.searchText("Username:"+INPUT_USERNAME_LOGIN+MAX_USER_ID));
    }
}