package org.bb.android.library;

import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by qiiluo on 1/10/17.
 */
// for 名称：焦点控制方法及装置
//申请号：CN201410372612.3

public class FocusSaveRestoreManager {

    private final FocusFinder mFocusFinder;

    public FocusSaveRestoreManager(){
        mFocusFinder = FocusFinder.getInstance();
    }

    public boolean handlekeyEvent(View currentFocus, Window window, KeyEvent key) {
        return handleKeyEvent(currentFocus, key);
    }

    /**
     * handle key event
     *
     * @param currentFocus
     * @param key
     * @return true if this keyevent has been consumed, false to let android system handle it.
     */

    public boolean handleKeyEvent(View currentFocus, KeyEvent key) {
        boolean handled = false;
        int direction = getDirection(key);

        /*
         *  check if there is a saved focus view in this direction.
         */
        View candidateFocus = getSavedFocusVieww(currentFocus, direction);
        if (null != candidateFocus) {
            /*
             * Yes, we have one, give foucs to it.
             */
            handled = candidateFocus.requestFocus();
        } else {
            /*
             * No, so we find one by android's default policy
             */
            candidateFocus = mFocusFinder.findNextFocus((ViewGroup) currentFocus.getRootView(),
                                                                    currentFocus,
                                                                    direction);
            if (null != candidateFocus){
                /*
                 * Cool, we find one, so
                 *  1. give focus to it
                 *  2. save this focus view and direction for future using.
                 */
                handled = candidateFocus.requestFocus();
                saveFocusView(currentFocus, candidateFocus, direction);
            }
        }

        return handled;
    }

    private void saveFocusView(View currentFocus, View candidateFocus, int whichDirection) {

    }

    private View getSavedFocusVieww(View currentFocus, int whichDirection) {
        return currentFocus;
    }

    private int getDirection(KeyEvent key) {
        return FocusPathManager.keyCode2Direction(key.getKeyCode());
    }
}
