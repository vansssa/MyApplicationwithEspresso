package com.example.sqa_pt.myapplication;

import android.content.Intent;
import android.support.test.espresso.DataInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;

import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

/**
 * Created by sqa-pt on 2015/12/29.
 */

@RunWith(AndroidJUnit4.class)
public class demolistviewTest {

    UiDevice mDevice;
    @Rule
    public ActivityTestRule<demolistview> mActivityRule = new ActivityTestRule<>(
            demolistview.class);


    @Before
    public void setUp() throws Exception{
        //custom failure exception
        setFailureHandler(new failureMessage(getInstrumentation().getTargetContext()));
        mDevice = UiDevice.getInstance(getInstrumentation());
        mActivityRule.launchActivity(new Intent());
    }
    //Check:onData
    @Test
    public void click_item_notin_currentview()
    {
        onRow("item: 30").onChildView(withId(R.id.rowContentTextView)).perform(click());
        //onData(withItemContent("item: 30")) .perform(click());

    }

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    private static DataInteraction onRow(String str) {
        return onData(hasEntry(Matchers.equalTo(demolistview.ROW_TEXT), is(str)));
    }


}
