package com.example.sqa_pt.myapplication;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sqa-pt on 2015/12/29.
 */

@RunWith(AndroidJUnit4.class)
public class empty_demo {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void setUp() throws Exception{
        //before test
        mActivityRule.launchActivity(new Intent());
    }

    @After
    public void teardown() throws  Exception{
        //after test
    }

    @Test
    public void simple_activity() {
        //write test case here
    }



}
