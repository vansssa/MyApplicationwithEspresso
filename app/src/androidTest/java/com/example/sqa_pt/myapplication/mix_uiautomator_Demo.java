package com.example.sqa_pt.myapplication;

import android.app.Activity;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;
/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

/**
 * Basic tests showcasing simple view matchers and actions like {@link ViewMatchers#withId},
 * {@link ViewActions#click} and {@link ViewActions#typeText}.
 * <p>
 * Note that there is no need to tell Espresso that a view is in a different {@link Activity}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class mix_uiautomator_Demo {

    public static final String STRING_TO_BE_TYPED = "Espresso";
    UiDevice mDevice;
    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);



    @Before
    public void setUp() throws Exception{
        //custom failure exception
        setFailureHandler(new failureMessage(getInstrumentation().getTargetContext()));
        mDevice = UiDevice.getInstance(getInstrumentation());
        //mActivityRule.launchActivity(new Intent());
    }

    @After
    public void tearDown() throws Exception {
        mDevice.pressHome();
    }




    // espresso with uiautomator
    @Test
    public void launchApp_checkactivity()
    {
        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard());
        onView(withId(R.id.activityChangeTextBtn)).perform(click());

        mDevice.pressHome();
        UiObject conutactsApp = mDevice.findObject(new UiSelector().text("Testapplication"));
        try {
            conutactsApp.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        // UIAutomator code: check that contact is present in it after sync was triggered
        UiObject contactName = mDevice.findObject(new UiSelector().text("Hello World!"));
        assertTrue(contactName.exists());
       // mDevice.pressHome();
    }


    //b. 動態資料的listview : T23868 （runway 判定）
    //⁃	espresso 無法取得值, 搭配uiautmator
    @Test
    public void getitem_content() {
        String result = "";

        //disable
        DataInteraction item = onData(anything()).inAdapterView(withId((R.id.likedlist))).atPosition(0);
        UiObject listview = new UiObject(new UiSelector().className("android.widget.TextView").
                resourceId("com.example.sqa_pt.myapplication:id/rowContentTextView").index(0));
        try {
            result= listview.getText().toString();
            item.onChildView(withId(R.id.rowContentTextView)).check(matches(withText(result)));
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

    }


}