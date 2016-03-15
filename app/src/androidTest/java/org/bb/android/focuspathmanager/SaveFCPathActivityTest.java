package org.bb.android.focuspathmanager;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by bysong on 16-3-14.
 */
@RunWith(AndroidJUnit4.class)
public class SaveFCPathActivityTest extends ActivityInstrumentationTestCase2<SaveFCPathActivity> {
    private SaveFCPathActivity mActivity;

//    public FocusActivityTest(Class<FocusActivity> activityClass) {
//        super(activityClass);
//    }

    public SaveFCPathActivityTest(){
        super(SaveFCPathActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testFocusPath(){
        View v = mActivity.getWindow().getDecorView();

        View currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button2);

        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button3);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button3);

        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button4);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button4);

        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button5);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(currentF, R.id.button1);
    }
}
