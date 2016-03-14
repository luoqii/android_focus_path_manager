package org.bb.android.focuspathmanager;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;

/**
 * Created by bysong on 16-3-14.
 */
public class FocusActivityTest extends ActivityInstrumentationTestCase2<FocusActivity> {
    public FocusActivityTest(Class<FocusActivity> activityClass) {
        super(activityClass);
    }

    public void testFocusPath(){
        FocusActivity act = getActivity();
        View v = act.getWindow().getDecorView();
        View currentF = v.findFocus();
        assertIdIs(currentF, R.id.button1);
    }

    public static void assertIdIs(View view, int id){
        assertEquals(view.getId(), id);
    }
}
