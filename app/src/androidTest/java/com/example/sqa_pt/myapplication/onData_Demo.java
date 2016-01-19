package com.example.sqa_pt.myapplication;

import android.support.test.espresso.DataInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

/**
 * Created by sqa-pt on 2015/12/29.
 */

@RunWith(AndroidJUnit4.class)
public class onData_Demo {

    UiDevice mDevice;
    int itemcount;
    @Rule
    public ActivityTestRule<demoliked> mActivityRule = new ActivityTestRule<>(
            demoliked.class);


    @Before
    public void setUp() throws Exception {
        //custom failure exception
        setFailureHandler(new failureMessage(getInstrumentation().getTargetContext()));
        //mDevice = UiDevice.getInstance(getInstrumentation());
        itemcount = mActivityRule.getActivity().listView.getCount() - 1;
        // mActivityRule.launchActivity(new Intent());
    }
    //Check:onData


    //a. 非預知的資料記錄產生判定：History channel 判定：T23892
    @Test
    public void add_and_click_item() {

        onView(withId(R.id.addlike))
                .perform(click());

        //disable
        DataInteraction item = onData(anything()).inAdapterView(withId((R.id.likedlist))).atPosition(itemcount + 1);
        item.onChildView(withId(R.id.rowContentTextView)).check(matches(withText("item: 100  kkbox")));

    }




    private static DataInteraction onRow(String str) {
        return onData(hasEntry(Matchers.equalTo(demoliked.ROW_TEXT), is(str)));

    }
}
