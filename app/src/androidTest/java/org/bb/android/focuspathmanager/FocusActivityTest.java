package org.bb.android.focuspathmanager;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewParentCompat;
import android.support.v4.widget.TextViewCompat;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

/**
 * Created by bysong on 16-3-14.
 */
@RunWith(AndroidJUnit4.class)
public class FocusActivityTest extends ActivityInstrumentationTestCase2<FocusActivity> {
    private FocusActivity mActivity;

//    public FocusActivityTest(Class<FocusActivity> activityClass) {
//        super(activityClass);
//    }

    public FocusActivityTest(){
        super(FocusActivity.class);
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
        assertIdIs(currentF, R.id.button2);

        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        assertIdIs(currentF, R.id.button3);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        assertIdIs(currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        assertIdIs(currentF, R.id.button3);
    }


    public static void assertIdIs(View view, int id)
    {
        String expectStr = viewStr(view);
        String actualStr = viewStr(getRootView(view));
        assertEquals("expected:" + expectStr + " but actual: " + actualStr , view.getId(), id);
    }

    private static View getRootView(View view) {
        View root = null;
        if (view != null){
            root = view.getRootView();
        }
        return root;
    }

    public static String viewStr(View view){
            String expectStr = "";
            if (view != null){
                if (view instanceof TextView){
                    expectStr = ((TextView)view).getText().toString();
                } else {
                    expectStr = view.toString();
                }
            }

        return expectStr;
    }
}
