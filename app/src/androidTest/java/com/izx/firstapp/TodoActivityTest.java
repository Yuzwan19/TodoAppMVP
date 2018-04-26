package com.izx.firstapp;

import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * Created by Izcax on 3/25/18.
 */
public class TodoActivityTest {

    @Rule
    public ActivityTestRule<TodoActivity> activityTestRule = new ActivityTestRule<TodoActivity>(TodoActivity.class);

    @Test
    public void testEmptyInput() throws Exception {
        onView(withId(R.id.edt_add)).perform(typeText(""));
        onView(withId(R.id.btn_add)).perform(click());
        onView(withText("Please do not empty"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}