package org.bb.android.focuspathmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import org.bb.android.library.FocusPathManager;

/**
 * Created by bysong on 16-3-11.
 */
public class FocusActivity extends Activity {
    protected FocusPathManager mFocusPathM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // step 1/2
        mFocusPathM = new FocusPathManager();
//        findViewById(R.id.left_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
//        findViewById(R.id.right_focus_colleague).setTag(FocusPathManager.VIEW_ID_MARK_FOCUS_COLLEAGUE, 1);
        FocusPathManager.markAsFocusColleague(findViewById(R.id.left_focus_colleague));
        FocusPathManager.markAsFocusColleague(findViewById(R.id.right_focus_colleague));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled =  super.onKeyDown(keyCode, event);

        // step 2/2
        if (!handled){
            handled = mFocusPathM.handleFocusKeyEvent(event, getWindow());
        }

        return handled;
    }

    public static class A extends FocusActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mFocusPathM.saveFocus(findViewById(R.id.button3));
        }
    }

    public static class IntraFocusColleagueById extends FocusActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            FocusPathManager.markAsFocusColleague(findViewById(R.id.left_focus_colleague));
            FocusPathManager.markAsFocusColleague(findViewById(R.id.right_focus_colleague));
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            boolean handled =  super.onKeyDown(keyCode, event);

            // step 2/2
            if (!handled){
                handled = mFocusPathM.handleFocusKeyEventIntraFocusColleagueById(event, getWindow().getDecorView());
            }

            return handled;
        }
    }

    public static class IntraFocusColleagueByTag extends FocusActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            FocusPathManager.markAsFocusColleague(findViewById(R.id.left_focus_colleague));
            FocusPathManager.markAsFocusColleague(findViewById(R.id.right_focus_colleague));
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            boolean handled =  super.onKeyDown(keyCode, event);

            // step 2/2
            if (!handled){
                handled = mFocusPathM.handleFocusKeyEventIntraFocusColleagueByTag(event, getWindow().getDecorView());
            }

            return handled;
        }
    }
}
