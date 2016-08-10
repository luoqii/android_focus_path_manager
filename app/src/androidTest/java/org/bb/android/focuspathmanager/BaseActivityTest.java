package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.os.Build;
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

    // http://stackoverflow.com/questions/2799097/how-can-i-detect-when-an-android-application-is-running-in-the-emulator
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
