package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import org.bb.android.library.FocusPathManager;

/**
 * Created by bysong on 16-3-11.
 */
public class MainActivity extends Activity {
    private FocusPathManager mFocusPathM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mFocusPathM = new FocusPathManager();
        findViewById(R.id.left_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
        findViewById(R.id.right_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled =  super.onKeyDown(keyCode, event);

        if (!handled){
            handled = mFocusPathM.handleFocusKeyEvent(event, getWindow());
        }
        return handled;
    }
}
