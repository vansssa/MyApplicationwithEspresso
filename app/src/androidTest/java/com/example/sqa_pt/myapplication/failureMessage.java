package com.example.sqa_pt.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.EspressoException;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by sqa-pt on 2015/12/28.
 */
public class failureMessage implements FailureHandler {


    private final FailureHandler delegate;
    private Activity mActiviyt;
    private String mTestName;
    public failureMessage(Context targetContext) {
        delegate = new DefaultFailureHandler(targetContext);
    }

    public failureMessage(Context targetContext,Activity mActivityRule) {
        delegate = new DefaultFailureHandler(targetContext);
        mActiviyt=mActivityRule;
    }

    @Override
    public void handle(Throwable error, Matcher<View> viewMatcher) {
        try {

            delegate.handle(error, viewMatcher);
        } catch (NoMatchingViewException e) {
            takeScreenshot(mActiviyt,"NoMatchingViewException");
            throw new NoHierarchyException(e);
        } catch (AmbiguousViewMatcherException e) {
            takeScreenshot(mActiviyt,"AmbiguousViewMatcherException");
            throw new NoHierarchyException(e);
        }
    }

    public class NoHierarchyException extends RuntimeException implements EspressoException {
        public NoHierarchyException(NoMatchingViewException e) {
            super(new NoMatchingViewException.Builder().from(e).includeViewHierarchy(false).build());
        }

        public NoHierarchyException(AmbiguousViewMatcherException e) {
            super(new AmbiguousViewMatcherException.Builder().from(e).includeViewHierarchy(false).build());
        }
    }

    //take screenshot
    public static void takeScreenshot( Activity activity,String name)
    {

        // In Testdroid Cloud, taken screenshots are always stored
        // under /test-screenshots/ folder and this ensures those screenshots
        // be shown under Test Results
        String path =
                "sdcard/" + name +".png";
        Log.i("VA", path);
        View scrView = activity.getWindow().getDecorView().getRootView();
        scrView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(scrView.getDrawingCache());
        scrView.setDrawingCacheEnabled(false);

        OutputStream out = null;
        File imageFile = new File(path);

        try {
            out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
        } catch (FileNotFoundException e) {
            // exception
        } catch (IOException e) {
            // exception
        } finally {

            try {
                if (out != null) {
                    out.close();
                }

            } catch (Exception exc) {
            }

        }
    }
}
