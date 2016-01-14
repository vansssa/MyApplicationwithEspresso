package com.example.sqa_pt.myapplication;

import android.content.Intent;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;

/**
 * Created by sqa-pt on 2015/12/30.
 */
@RunWith(AndroidJUnit4.class)
public class webview_demo {
    UiDevice mDevice;
    @Rule
    public ActivityTestRule<webActivity> mActivityRule = new ActivityTestRule<webActivity>(webActivity.class, false, false) {
        @Override
        protected void afterActivityLaunched() {
            // Enable JS!
            onWebView().forceJavascriptEnabled();
        }
    };


    @Before
    public void setUp() throws Exception{
        //custom failure exception
        setFailureHandler(new failureMessage(getInstrumentation().getTargetContext()));
        mDevice = UiDevice.getInstance(getInstrumentation());
        mActivityRule.launchActivity(new Intent());
    }


    @Test
    public void check_changeText()
    {
        onWebView()
                // Find the input element by ID
                .withElement(findElement(Locator.ID, "text_input"))
                .perform(clearElement())
                .perform(DriverAtoms.webKeys("UTAPASS"))
                .withElement(findElement(Locator.ID, "changeTextBtn")).perform(webClick())
                .withElement(findElement(Locator.ID, "message"))
                .check(webMatches(getText(), Matchers.containsString("UTAPASS")));
    }
}

