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
public class SaveFCPathActivity_B_Test extends BaseActivityTest<SaveFCPathActivity.B> {
    private SaveFCPathActivity mActivity;

//    public FocusActivityTest(Class<FocusActivity> activityClass) {
//        super(activityClass);
//    }

    public SaveFCPathActivity_B_Test(){
        super(SaveFCPathActivity.B.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = (SaveFCPathActivity) getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testFocusPath(){

        // always failed in n 6 device
        View v = mActivity.getWindow().getDecorView();

        View currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button4);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button4);

        sendKeys("DPAD_UP");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button4);
    }
}
