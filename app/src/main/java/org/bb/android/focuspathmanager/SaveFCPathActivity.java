package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import org.bb.android.library.FocusPathManager;

/**
 * Created by bysong on 16-3-11.
 */
public class SaveFCPathActivity extends Activity {
    private FocusPathManager mFocusPathM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save_fc_path);

        // step 1/2
        mFocusPathM = new FocusPathManager();
//        findViewById(R.id.left_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
//        findViewById(R.id.right_top_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
//        findViewById(R.id.right_bottom_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
        mFocusPathM.markAsFocusColleague(findViewById(R.id.left_focus_colleague));
        mFocusPathM.markAsFocusColleague(findViewById(R.id.right_top_focus_colleague));
        mFocusPathM.markAsFocusColleague(findViewById(R.id.right_bottom_focus_colleague));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled =  super.onKeyDown(keyCode, event);

        // step 2/2
        if (!handled){
            handled = mFocusPathM.handleFocusKeyEvent(event, getWindow(), true);
        }

        return handled;
    }

    public static class A extends SaveFCPathActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            findViewById(R.id.left_focus_colleague).setNextFocusRightId(FocusPathManager.VIEW_ID_MY_SELF);
            findViewById(R.id.right_bottom_focus_colleague).setNextFocusUpId(FocusPathManager.VIEW_ID_MY_SELF);
        }
    }

    public static class B extends SaveFCPathActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            findViewById(R.id.left_focus_colleague).setNextFocusRightId(R.id.right_bottom_focus_colleague);
            findViewById(R.id.right_bottom_focus_colleague).setNextFocusUpId(FocusPathManager.VIEW_ID_MY_SELF);
        }
    }
}
