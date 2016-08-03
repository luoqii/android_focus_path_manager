package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import junit.framework.Assert;

import org.bb.android.library.FocusPathManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by bysong on 16-3-14.
 */
@RunWith(AndroidJUnit4.class)
public class FocusActivityTest extends BaseActivityTest<FocusActivity> {
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
        assertIdIs(mActivity, currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        assertIdIs(mActivity, currentF, R.id.button2);

        sendKeys("DPAD_DOWN");
        currentF = v.findFocus();
        assertIdIs(mActivity, currentF, R.id.button3);

        sendKeys("DPAD_LEFT");
        currentF = v.findFocus();
        assertIdIs(mActivity, currentF, R.id.button1);

        sendKeys("DPAD_RIGHT");
        currentF = v.findFocus();
        assertIdIs(mActivity, currentF, R.id.button3);
    }


    public static void assertIdIs(Activity act, View view, int id)
    {
        String expectStr = viewStr(view);
        String actualStr = viewStr(act.findViewById(id));
        String idStr = id + "";
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
            String expectStr = "Not Exist";
            if (view != null){
                if (view instanceof TextView){
                    expectStr = ((TextView)view).getText().toString();
                } else {
                    expectStr = view.toString();
                }
            }

        return expectStr;
    }

    @Test
    public void test_isFocusColleague(){
        View colleague = new View(mActivity);
        FocusPathManager pm = new FocusPathManager();

        Assert.assertFalse(pm.isFocusColleague(colleague));

//        colleague.setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
        FocusPathManager.markAsFocusColleague(colleague);
        Assert.assertTrue(pm.isFocusColleague(colleague));

        FocusPathManager.unMarkAsFocusColleague(colleague);
        Assert.assertFalse(pm.isFocusColleague(colleague));

        FocusPathManager.markAsFocusColleague(colleague);
        Assert.assertTrue(pm.isFocusColleague(colleague));
    }

    @Test
    public void test_saveColleagueAndFocusView(){
        View colleague = new View(mActivity);
        FocusPathManager pm = new FocusPathManager();
//        colleague.setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 0);
        FocusPathManager.markAsFocusColleague(colleague);

        View focus = new View(mActivity);
        pm.saveColleagueAndFocusView(colleague, focus);
        View savedFocus = pm.getSavedFocusViewFromColleague(colleague);

        Assert.assertEquals(focus, savedFocus);
        Assert.assertTrue(pm.hasSavedFocusColleague(colleague));
    }
}
