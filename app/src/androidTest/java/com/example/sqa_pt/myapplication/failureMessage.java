package com.example.sqa_pt.myapplication;

import android.content.Context;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.EspressoException;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by sqa-pt on 2015/12/28.
 */
public class failureMessage implements FailureHandler {


    private final FailureHandler delegate;

    public failureMessage(Context targetContext) {
        delegate = new DefaultFailureHandler(targetContext);
    }

    @Override
    public void handle(Throwable error, Matcher<View> viewMatcher) {
        try {
            delegate.handle(error, viewMatcher);
        } catch (NoMatchingViewException e) {
            throw new NoHierarchyException(e);
        } catch (AmbiguousViewMatcherException e) {
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
}
