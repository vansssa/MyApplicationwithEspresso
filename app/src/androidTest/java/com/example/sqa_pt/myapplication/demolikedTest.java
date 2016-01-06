package com.example.sqa_pt.myapplication;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
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
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
/**
 * Created by sqa-pt on 2015/12/29.
 */

@RunWith(AndroidJUnit4.class)
public class demolikedTest {

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
        setFailureHandler(new failureMessage(getInstrumentation().getTargetContext(), mActivityRule.getActivity()));
        mDevice = UiDevice.getInstance(getInstrumentation());
        itemcount = mActivityRule.getActivity().listView.getCount() - 1;
        //closeSoftKeyboard();

    }


    @After
    public void tearDown() throws Exception {

        mDevice.pressHome();
        Thread.sleep(3000);
    }


    //a. 非預知的資料記錄產生判定：History channel 判定：T23892
    @Test
    public void add_and_click_item() {

        onView(withId(R.id.addlike))
                .perform(click());

        //disable
        DataInteraction item = onData(anything()).inAdapterView(withId((R.id.likedlist))).atPosition(itemcount);
        item.onChildView(withId(R.id.rowContentTextView)).check(matches(isDisplayed()));

    }

    //b. 動態資料的listview : T23868 （runway 判定）
    //⁃	espresso 無法取得值, 搭配uiautmator

    @Test
    public void getitem_content() {
        String result = "";

        //disable
        DataInteraction item = onData(anything()).inAdapterView(withId((R.id.likedlist))).atPosition(0);
        UiObject listview = new UiObject(new UiSelector().className("android.widget.TextView").resourceId("com.example.sqa_pt.myapplication:id/rowContentTextView").index(0));
        try {
            result= listview.getText().toString();
            Log.i("VA", "get index [0] " + result);
            item.onChildView(withId(R.id.rowContentTextView)).check(matches(withText(result)));
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    //hasilbing: duplicate item content
    @Test
    public void a1_get_non_unique() {
        //ViewInteraction item=onView(allOf(withText("I'm focus"), hasSibling(withText("item: 6 kkbox"))));

        ViewInteraction view = onView(allOf(withText("I'm focus"), hasSibling(withText("item: 5  kkbox"))));
        view.perform(clearText());
    }

    //c. 單一物件的狀態（focus,select）:T23958
    @Test
    public void check_component_status() {

        //focused--inputtext
        onView(withId(R.id.editText2)).check(matches(hasFocus()));

        DataInteraction item = onData(anything()).inAdapterView(withId((R.id.likedlist))).atPosition(itemcount);

        //checked--radio
        item.onChildView(withId(R.id.row_checkbox)).perform(click()).check(matches(isNotChecked()));

        //checked--select
        item.onChildView(withId(R.id.row_radiobt)).check(matches(isChecked()));


    }


    @Test
    public void match_hint() {
        String result = "";
        onView(withId(R.id.editText2)).check(matches(withHint("search songs")));
    }




    private static DataInteraction onRow(String str) {
        return onData(hasEntry(Matchers.equalTo(demolistview.ROW_TEXT), is(str)));
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

            @Override
            public void describeTo(Description description) {
                description.appendText("with hint: ");
                stringMatcher.describeTo(description);
            }
        };
    }


}
