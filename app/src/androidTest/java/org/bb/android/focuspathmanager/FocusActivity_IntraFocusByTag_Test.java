package org.bb.android.focuspathmanager;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by bysong on 16-3-14.
 */
@RunWith(AndroidJUnit4.class)
public class FocusActivity_IntraFocusByTag_Test extends BaseActivityTest<FocusActivity.IntraFocusColleagueByTag> {
    private FocusActivity mActivity;

//    public FocusActivityTest(Class<FocusActivity> activityClass) {
//        super(activityClass);
//    }

    public FocusActivity_IntraFocusByTag_Test(){
        super(FocusActivity.IntraFocusColleagueByTag.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = (FocusActivity) getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testFocusPath(){
        View v = mActivity.getWindow().getDecorView();

        View currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button2);

        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button3);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        FocusActivityTest.assertIdIs(mActivity, currentF, R.id.button3);
    }
}
