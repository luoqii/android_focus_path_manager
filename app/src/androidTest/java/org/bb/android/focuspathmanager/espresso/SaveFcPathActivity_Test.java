package org.bb.android.focuspathmanager.espresso;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.bb.android.focuspathmanager.FocusActivity;
import org.bb.android.focuspathmanager.R;
import org.bb.android.focuspathmanager.SaveFCPathActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by qiiluo on 8/10/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SaveFcPathActivity_Test {

    @Rule
    public ActivityTestRule<SaveFCPathActivity> mActivityRule = new ActivityTestRule(SaveFCPathActivity.class);

    @Test
    public void focusPath() {
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_LEFT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button1)));

        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_UP));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_UP));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_UP));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button2)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_LEFT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button1)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button2)));

        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_DOWN));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_DOWN));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_DOWN));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button5)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_LEFT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button1)));
        onView(withId(R.id.button1)).perform(pressKey(KeyEvent.KEYCODE_DPAD_RIGHT));
        onView(FocusActivityTest.isFocused()).check(matches(withId(R.id.button5)));

    }


}
