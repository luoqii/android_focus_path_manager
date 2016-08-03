package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

/**
 * Created by bysong on 16-3-15.
 */
// TODO avoid case object return by getActivity().
public abstract class BaseActivityTest<T> extends ActivityInstrumentationTestCase2 {


    public BaseActivityTest(Class activityClass) {
        super(activityClass);
    }

    public static void assertIdIs(View view, int id){
        assertEquals(view.getId(), id);
    }

    @Override
    public void sendKeys(int... keys) {
        super.sendKeys(keys);
        try {
            //
            wait(getSendKeyWaitTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * wait time between keys, in millis
     * for some devices, we must wait, wait, and wait.
     */
    public long getSendKeyWaitTime() {
        return 2000L;
//        return 0L;
    }
}
