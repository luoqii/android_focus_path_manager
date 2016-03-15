package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

/**
 * Created by bysong on 16-3-15.
 */
// TODO
public class BaseActivityTest<T extends Activity> extends ActivityInstrumentationTestCase2 {


    public BaseActivityTest(Class<T> activityClass) {
        super(activityClass);
    }

    public static void assertIdIs(View view, int id){
        assertEquals(view.getId(), id);
    }
}
