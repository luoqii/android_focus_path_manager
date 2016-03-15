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
public class FocusActivity_A_Test extends ActivityInstrumentationTestCase2<FocusActivity.A> {
    private FocusActivity mActivity;

//    public FocusActivityTest(Class<FocusActivity> activityClass) {
//        super(activityClass);
//    }

    public FocusActivity_A_Test(){
        super(FocusActivity.A.class);
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
        assertIdIs(currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        assertIdIs(currentF, R.id.button3);
    }


    public static void assertIdIs(View view, int id){
        assertEquals(view.getId(), id);
    }
}
