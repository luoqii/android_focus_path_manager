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
public class FocusActivity_IntraFocusById_Test extends BaseActivityTest<FocusActivity.IntraFocusColleagueById> {
    private FocusActivity mActivity;

//    public FocusActivityTest(Class<FocusActivity> activityClass) {
//        super(activityClass);
//    }

    public FocusActivity_IntraFocusById_Test(){
        super(FocusActivity.IntraFocusColleagueById.class);
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
        sendKeys("DPAD_UP");
        sendKeys("DPAD_UP");
        currentF = v.findFocus();
        sendKeys("DPAD_LEFT");
        sendKeys("DPAD_RIGHT");
        assertEquals(v.findFocus(), currentF);

        sendKeys("DPAD_RIGHT");
        sendKeys("DPAD_DOWN");
        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        sendKeys("DPAD_LEFT");
        sendKeys("DPAD_RIGHT");
        assertEquals(v.findFocus(), currentF);
    }
}
