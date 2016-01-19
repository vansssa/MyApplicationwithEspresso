package com.example.sqa_pt.myapplication;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

/**
 * Created by sqa-pt on 2015/12/29.
 */

@RunWith(AndroidJUnit4.class)
public class custom_matcher_Demo {

    UiDevice mDevice;
    int itemcount;
    @Rule
    public ActivityTestRule<demoliked> mActivityRule = new ActivityTestRule<>(
            demoliked.class);
    public ActivityTestRule<MainActivity> motherActiviyt = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void setUp() throws Exception {
        //custom failure exception
        //setFailureHandler(new failureMessage(getInstrumentation().getTargetContext(), mActivityRule.getActivity()));
        mDevice = UiDevice.getInstance(getInstrumentation());
        itemcount = mActivityRule.getActivity().listView.getCount() - 1;
        closeSoftKeyboard();

    }


    @After
    public void tearDown() throws Exception {

        mDevice.pressHome();
        Thread.sleep(3000);
    }


    @Test
    public void match_hint() {
        String result = "";
        //hasImeAction();
        onView(withId(R.id.editText2)).check(matches(withHint("search songs")));
    }

    //custom matcher
    static Matcher<View> withHint(final String substring) {
        return withHint(is(substring));
    }

    static Matcher<View> withHint(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public boolean matchesSafely(EditText view) {
                final CharSequence hint = view.getHint();
                return hint != null && stringMatcher.matches(hint.toString());
            }

            //error message
            @Override
            public void describeTo(Description description) {
                description.appendText("with hint: ");
                stringMatcher.describeTo(description);
            }
        };
    }

    private static DataInteraction onRow(String str) {
        return onData(hasEntry(Matchers.equalTo(demolistview.ROW_TEXT), is(str)));
    }





}
