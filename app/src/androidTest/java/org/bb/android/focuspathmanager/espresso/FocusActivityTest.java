package org.bb.android.focuspathmanager.espresso;

import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;

import org.bb.android.focuspathmanager.FocusActivity;
import org.bb.android.focuspathmanager.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by qiiluo on 8/10/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FocusActivityTest {

    @Rule
    public ActivityTestRule<FocusActivity> mActivityRule = new ActivityTestRule(FocusActivity.class);

    @Test
    public void focusPath() {
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button2)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_DOWN));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button3)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_LEFT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button1)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button3)));

    }

    /**
     * Returns a matcher that matches {@link View}s that are focused.
     */
    public static Matcher<View> isFocused() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is focused");
            }

            @Override
            public boolean matchesSafely(View view) {
                return view.isFocused();
            }
        };
    }
}
