package org.bb.android.library;

import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by bysong on 16-3-11.
 */
public class FocusPathManager {
    private static final String TAG = FocusPathManager.class.getSimpleName();
    public static /*final*/ boolean DEBUG = false && BuildConfig.DEBUG;

    private Map<WeakReference<View>, WeakReference<View>> mFocusMap = new Hashtable<WeakReference<View>, WeakReference<View>>();

    public static final int VIEW_ID_MARK_FOCUS_COLLEAGUE = R.id.VIEW_ID_MARK_FOCUS_COLLEAGUE;
    /**
     * focus will keep in the same focus colleague.
     */
    // we do not care the value, as long as have one
    public static final int VIEW_ID_MY_SELF = R.id.VIEW_ID_MY_SELF;
    /**
     * help to remember focus path.
     */
    // we do not care the value, as long as have one
    public static final int VIEW_ID_NEXT_LEFT   = R.id.VIEW_ID_NEXT_LEFT;
    public static final int VIEW_ID_NEXT_RIGHT  = R.id.VIEW_ID_NEXT_RIGHT;
    public static final int VIEW_ID_NEXT_UP     = R.id.VIEW_ID_NEXT_UP;
    public static final int VIEW_ID_NEXT_DOWN   = R.id.VIEW_ID_NEXT_DOWN;

    private static final int TAG_NEXT_FOCUS_LEFT    = R.id.next_foucs_left;
    private static final int TAG_NEXT_FOCUS_RIGHT   = R.id.next_foucs_right;
    private static final int TAG_NEXT_FOCUS_DOWN    = R.id.next_foucs_down;
    private static final int TAG_NEXT_FOCUS_UP      = R.id.next_foucs_up;

    public boolean hasSavedFocusColleague(View colleague) {
//        assureIsFocuscolleague(colleague);
        for (WeakReference<View> s : mFocusMap.keySet()) {
            if (null != s && s.get() == colleague) {
                return true;
            }
        }

        return false;
    }

    public View getSavedFocusViewFromColleague(View colleague) {
        assureIsFocuscolleague(colleague);
        for (WeakReference<View> s : mFocusMap.keySet()) {
            if (null != s && s.get() == colleague) {
                return mFocusMap.get(s).get();
            }
        }

        return null;
    }

    public void saveColleagueAndFocusView(View colleague, View focus) {
        assureIsFocuscolleague(colleague);
        if (hasSavedFocusColleague(colleague)) {
            WeakReference<View> it = null;
            for (WeakReference<View> s : mFocusMap.keySet()) {
                if (null != s && s.get() == colleague) {
                    it = s;
                    break;
                }
            }

            if (null != it) {
                mFocusMap.remove(it);
            }
        }

        if (DEBUG) {
            // keep align+++++++++++++++++++++++++++================
            Log.d(TAG, "request save    focus for :" + colleague);
            Log.d(TAG, "request save    focus for :" + focus);
        }
        mFocusMap.put(new WeakReference<View>(colleague),
                new WeakReference<View>(focus));
    }

    public boolean restoreColleagueFocus(View colleague, int direction){
        boolean handled = false;
        View view2restore = getSavedFocusViewFromColleague(colleague);
        if (null != view2restore) {
            if (DEBUG) {
                // keep
                // align+++++++++++++++++++++++++++================
                Log.d(TAG, "request restore focus for focuscolleague:"
                        + colleague);
                Log.d(TAG, "request restore focus for view        :"
                        + view2restore);
            }
            handled = view2restore.requestFocus();

            if (handled) {
                view2restore.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
        }

        return handled;
    }

    static void assureIsFocuscolleague(View colleague) {
        if (!isFocusColleague(colleague)) {
            throw new IllegalArgumentException("input is NOT a valid colleague, colleague" + colleague);
        }
    }

    public void saveFocus(View focus) {
        View c = findFocusColleagueUp(focus);
        if (c != null) {
            saveColleagueAndFocusView(c, focus);
        }
    }

    public static View findFocusColleagueUp(View child) {
        if (null == child) {
            return null;
        }
        if (isFocusColleague(child)) {
            return child;
        }

        View c = null;
        ViewParent p = child.getParent();
        while (p != null && p instanceof View) {
            if (isFocusColleague((View) p)) {
                c = (View) p;
                break;
            }
            p = ((View) p).getParent();
        }

        return c;
    }

    public static boolean isFocusColleague(View view) {
        if (null == view) {
            return false;
        }

        Object tag = view.getTag();
        return (view instanceof ISimpleFocusColleague
                || view.getTag(VIEW_ID_MARK_FOCUS_COLLEAGUE) != null
                || (tag != null && tag instanceof Integer && VIEW_ID_MARK_FOCUS_COLLEAGUE == (Integer)tag));
    }

    public boolean handleFocusKeyEvent(KeyEvent event, Window window) {
        return handleFocusKeyEvent(event, window, true);
    }

    /**
     * @param event
     * @param window
     * @param saveColleagueFocusPathAlso also remember focusColleague focus path.
     * @return
     * @see #remmberFocusState(View, View, int)
     */
    public boolean handleFocusKeyEvent(KeyEvent event, Window window, boolean saveColleagueFocusPathAlso) {
        int keyCode = event.getKeyCode();
        int action = event.getAction();
        if (action != KeyEvent.ACTION_DOWN) {
            return false;
        }

        boolean handled = false;

        int direction = -1;
        direction = keyCode2Direction(keyCode);

        // validate direction code.
        if (-1 == direction) {
            return handled;
        }

        View decorView = window.getDecorView();
        View currentfocus = decorView.findFocus();
        View nextFocus = FocusFinder.getInstance().findNextFocus(
                (ViewGroup) decorView, currentfocus, direction);

        if (DEBUG){
            debugFC(decorView);
        }

        // focus will change.
        if (nextFocus == null || currentfocus == null) {
            return handled;
        }

        if (DEBUG) {
            Log.d(TAG, "currentfocus: " + currentfocus);
            Log.d(TAG, "   nextFocus: " + nextFocus);
            Log.d(TAG, "   direction: " + direction);
        }

        View currentFocusColleague = findFocusColleagueUp(currentfocus);
        View nextFocusColleague = null;
        if (null == currentFocusColleague) {
            return handled;
        }
        nextFocusColleague = findFocusColleagueUp(nextFocus);
        if (nextFocusColleague == currentFocusColleague) {
            return handled;
        }

        // focus will escape from one colleague to another colleague.
        int nextId = getNextFocusId(currentFocusColleague, direction);
        if (nextId == VIEW_ID_MY_SELF) {
            handled = true;
            if (DEBUG) {
                Log.i(TAG, "ignore & consume this event.");
            }

            return handled;
        }

        if (    (null == nextFocusColleague
                    || !saveColleagueFocusPathAlso)// when no save focus colleague, user specified id always take effect.
                && nextId != View.NO_ID) {
            // user specified id .
            View newColleagueView = decorView.findViewById(nextId);
            if (null != newColleagueView
                    && isFocusColleague(newColleagueView)) {
                nextFocusColleague = newColleagueView;
                if (DEBUG) {
                    Log.i(TAG, "replace nextFocusColleague by id. nextFocusColleague: " + nextFocusColleague);
                }
            }
        }
        if (saveColleagueFocusPathAlso) {
            View nextCandidate = getNextFocusViewByTag(currentFocusColleague, direction);
            if (null != nextCandidate) {
                nextFocusColleague = nextCandidate;
                if (DEBUG) {
                    Log.i(TAG, "replace nextFocusColleague by tag. nextFocusColleague: " + nextFocusColleague);
                }
            }
        }

        if (currentFocusColleague == null || nextFocusColleague == null || nextFocusColleague == currentFocusColleague) {
            return handled;
        }

        if (DEBUG) {
            Log.d(TAG, "currentFocusColleague: " + currentFocusColleague);
            Log.d(TAG, "   nextFocusColleague: " + nextFocusColleague);
            Log.d(TAG, "           direction: " + direction);
        }

        // save and restore.
        //#1/2 save focus
        saveColleagueAndFocusView(currentFocusColleague, currentfocus);
        //#2/2 try to restore focus.
        if (hasSavedFocusColleague(nextFocusColleague)) {
            handled = restoreColleagueFocus(nextFocusColleague, direction);
            if (handled && saveColleagueFocusPathAlso) {
                remmberFocusStateByTag(currentFocusColleague, nextFocusColleague, direction, true);
            }
        } else {
            if (DEBUG) {
                Log.d(TAG, "request focus for focuscolleague:"
                        + nextFocusColleague);
            }
            handled = ((View) nextFocusColleague).requestFocus();

            if (handled) {
                if (saveColleagueFocusPathAlso) {
                    remmberFocusStateByTag(currentFocusColleague, nextFocusColleague, direction, true);
                }
                ((View) nextFocusColleague).playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
        }

        return handled;
    }

    public static void debugFC(View root) {
        doDebugFC(root, 0);
    }

    public static void doDebugFC(View root, int fcCount){
        if (isFocusColleague(root)) {
            Log.d(TAG, "found fc #" + fcCount + " " + root);
            fcCount++;
        }

        if (root instanceof  ViewGroup) {
            ViewGroup g = (ViewGroup) root;
            int childCount = g.getChildCount();
            for (int i = 0; i < childCount; i++) {
                doDebugFC(g.getChildAt(i), fcCount);
            }
        }
    }

    private static int getNextFocusId(View view, int focusDirection) {
        int nextId = View.NO_ID;
        if (focusDirection == View.FOCUS_DOWN) {
            nextId = view.getNextFocusDownId();
        } else if (focusDirection == View.FOCUS_UP) {
            nextId = view.getNextFocusUpId();
        } else if (focusDirection == View.FOCUS_LEFT) {
            nextId = view.getNextFocusLeftId();
        } else if (focusDirection == View.FOCUS_RIGHT) {
            nextId = view.getNextFocusRightId();
        }
        return nextId;
    }

    private static View getNextFocusViewByTag(View view, int focusDirection) {
        View next = null;
        if (-1 != focusDirection && view != null) {
            Object tag = null;
            int key = 0;
            if (focusDirection == View.FOCUS_DOWN) {
                key = TAG_NEXT_FOCUS_DOWN;
            } else if (focusDirection == View.FOCUS_UP) {
                key = TAG_NEXT_FOCUS_UP;
            } else if (focusDirection == View.FOCUS_LEFT) {
                key = TAG_NEXT_FOCUS_LEFT;
            } else if (focusDirection == View.FOCUS_RIGHT) {
                key = TAG_NEXT_FOCUS_RIGHT;
            }
            tag = view.getTag(key);
            if (null != tag) {
                next = ((WeakReference<View>) tag).get();
            }
        }

        return next;
    }

    public static boolean handleFocusKeyEventIntraFocusColleagueByTag(KeyEvent event, View view) {
        boolean handled = false;
        int direction = -1;
        int keyCode = event.getKeyCode();
        direction = keyCode2Direction(keyCode);
        if (null != view) {
            View n = getNextFocusViewByTag(view.findFocus(), direction);
            if (n != null) {
                if (DEBUG) {
                    Log.d(TAG, "try give focus to view: " + n);
                }
                handled = n.requestFocus();
                if (handled) {
                    n.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                }
            }
        }

        return handled;
    }

    public static boolean handleFocusKeyEventIntraFocusColleagueById(KeyEvent event, View view) {
        boolean handled = false;
        int direction = -1;
        int keyCode = event.getKeyCode();
        direction = keyCode2Direction(keyCode);
        if (null != view) {
            View focus = view.findFocus();
            View n = null;
            if (focus != null ) {
                n = view.findViewById(getNextFocusId(focus, direction));
            }
            if (n != null) {
                if (DEBUG) {
                    Log.d(TAG, "try give focus to view: " + n);
                }
                handled = n.requestFocus();
                if (handled) {
                    n.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                }
            }
        }

        return handled;
    }

    private static int keyCode2Direction(int keyCode) {
        int direction = -1;
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            direction = View.FOCUS_DOWN;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            direction = View.FOCUS_UP;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            direction = View.FOCUS_LEFT;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            direction = View.FOCUS_RIGHT;
        }
        return direction;
    }

    public static void remmberFocusState(View from, View to, KeyEvent keyEvent, boolean force) {
        if (keyEvent != null) {
            int direction = keyCode2Direction(keyEvent.getKeyCode());
            remmberFocusStateByTag(from, to, direction, force);
        }
    }

    /**
     * @param from
     * @param to
     * @param directionCode
     * @see #remmberFocusState(View, View, int)
     * @see #handleFocusKeyEventIntraFocusColleague(android.view.KeyEvent, android.view.View)
     */
    public static void remmberFocusStateByTag(View from, View to, int focusDirection, boolean force) {
        if (null == from || null == to || from == to) {
            return;
        }

        if (!assureValidDirection(focusDirection)){
            return;
        }

        if (DEBUG) {
            // ==========++++++++++++++------------
            Log.d(TAG, "old focus view: " + from);
            Log.d(TAG, "new focus view: " + to);
            // ==========++++++++++++++++++------------
            logFocus("new foucs before ", to);
        }
        WeakReference<View> r = new WeakReference<View>(from);
        switch (focusDirection) {
            case View.FOCUS_LEFT:
                if (force
                        || to.getTag(TAG_NEXT_FOCUS_RIGHT) == null) {
                    to.setTag(TAG_NEXT_FOCUS_RIGHT, r);
                }
                break;
            case View.FOCUS_RIGHT:
                if (force
                        || to.getTag(TAG_NEXT_FOCUS_LEFT) == null) {
                    to.setTag(TAG_NEXT_FOCUS_LEFT, r);
                }
                break;
            case View.FOCUS_DOWN:
                if (force
                        || to.getTag(TAG_NEXT_FOCUS_UP) == null) {
                    to.setTag(TAG_NEXT_FOCUS_UP, r);
                }
                break;
            case View.FOCUS_UP:
                if (force
                        || to.getTag(TAG_NEXT_FOCUS_DOWN) == null) {
                    to.setTag(TAG_NEXT_FOCUS_DOWN, r);
                }
                break;

            default:
                break;
        }

        if (DEBUG) {
            // ==========++++++++++++++++++------------
            logFocus("new foucs after  ", to);
        }

    }

    public static void remmberFocusStateById(View from, View to, KeyEvent keyEvent, boolean unUsed) {
        if (null == keyEvent) {
            return;
        }

        int direction = keyCode2Direction(keyEvent.getKeyCode());
        remmberFocusStateById(from, to, direction, false);
    }

    public static void remmberFocusStateById(View from, View to, int focusDirection, boolean unUsed) {
        remmberFocusStateById(from, to, focusDirection);
    }

    /**
     * we assume that view's ids are different.
     *
     * @param from
     * @param to
     * @param directionCode
     * @see #remmberFocusStateById(View, View, int)
     */
    public static void remmberFocusStateById(View from, View to, int focusDirection) {
        if (null == from || null == to || from == to) {
            return;
        }

        if (!assureValidDirection(focusDirection)) {
            return;
        }

        int fromId = from.getId();
        int toId = to.getId();
        if (fromId == toId // focus cycle
                || View.NO_ID == fromId) {
            return;
        }

        if (DEBUG) {
            // ==========++++++++++++++------------
            Log.d(TAG, "old focus id: " + from.getId() + " view: " + from);
            Log.d(TAG, "new focus id: " + to.getId() + " view: " + to);
            // ==========++++++++++++++++++------------
            logFocusById("new foucs before ", to);
        }
        switch (focusDirection) {
            case View.FOCUS_LEFT:
                to.setNextFocusRightId(fromId);
                break;
            case View.FOCUS_RIGHT:
                to.setNextFocusLeftId(fromId);
                break;
            case View.FOCUS_DOWN:
                to.setNextFocusUpId(fromId);
                break;
            case View.FOCUS_UP:
                to.setNextFocusDownId(fromId);
                break;

            default:
                break;
        }

        if (DEBUG) {
            // ==========++++++++++++++++++------------
            logFocusById("new foucs after  ", to);
        }

    }

    static boolean assureValidDirection(int direction) {
        if (direction == View.FOCUS_DOWN
                || direction == View.FOCUS_UP
                || direction == View.FOCUS_LEFT
                || direction == View.FOCUS_RIGHT) {
            return true;
        } else {
            Log.w(TAG, "invalid direction code");
//            throw new IllegalArgumentException("invalid direction code");
        }

        return false;
    }

    private static void logFocusById(String tag, View view) {
        Log.d(TAG,
                tag + " d:" + view.getNextFocusDownId() + " u:"
                        + view.getNextFocusUpId() + " l:"
                        + view.getNextFocusLeftId() + " r:"
                        + view.getNextFocusRightId()
        );

    }

    private static void logFocus(String tag, View view) {
        String log = "focus state for view: " + view + "\n";
        WeakReference<View> s = (WeakReference<View>) view.getTag(TAG_NEXT_FOCUS_DOWN);
        log += " d: ";
        if (null != s && s.get() != null) {
            log += s.get() + "\n";
        } else {
            log += "\n";
        }
        s = (WeakReference<View>) view.getTag(TAG_NEXT_FOCUS_UP);
        log += " u: ";
        if (null != s && s.get() != null) {
            log += s.get() + "\n";
        } else {
            log += "\n";
        }
        s = (WeakReference<View>) view.getTag(TAG_NEXT_FOCUS_LEFT);
        log += " l: ";
        if (null != s && s.get() != null) {
            log += s.get() + "\n";
        } else {
            log += "\n";
        }
        s = (WeakReference<View>) view.getTag(TAG_NEXT_FOCUS_RIGHT);
        log += " r: ";
        if (null != s && s.get() != null) {
            log += s.get();
        } else {
            log += "\n";
        }

        Log.d(TAG, tag + log);
    }

    private static View nextFocus(View v, int direction) {
        View next = null;
        Object tag = null;
        switch (direction) {
            case View.FOCUS_DOWN:
                tag = v.getTag(VIEW_ID_NEXT_DOWN);
                ;
                break;
            case View.FOCUS_UP:
                tag = v.getTag(VIEW_ID_NEXT_UP);
                ;
                break;
            case View.FOCUS_LEFT:
                tag = v.getTag(VIEW_ID_NEXT_LEFT);
                ;
                break;
            case View.FOCUS_RIGHT:
                tag = v.getTag(VIEW_ID_NEXT_RIGHT);
                ;
                break;
        }
        if (null != tag && tag instanceof WeakReference) {
            next = ((WeakReference<View>) tag).get();
        }

        return next;
    }

    private static int direction2Key(int direction) {
        int key = VIEW_ID_NEXT_DOWN;
        switch (direction) {
            case View.FOCUS_DOWN:
                key = VIEW_ID_NEXT_UP;
                break;
            case View.FOCUS_UP:
                key = VIEW_ID_NEXT_DOWN;
                break;
            case View.FOCUS_LEFT:
                key = VIEW_ID_NEXT_RIGHT;
                break;
            case View.FOCUS_RIGHT:
                key = VIEW_ID_NEXT_LEFT;
                break;

            default:
                break;
        }
        return key;
    }

    /**
     * <p/>
     * stupid simple focus colleague, this is a markable(null) impl interface,
     * FocusPathManager will manage save & restore focus automatically.
     * <p/>
     * instead of impling this null interface, you can set a {@link FocusPathManager#VIEW_ID_MARK_FOCUS_COLLEAGUE}
     * to colleague view.
     * 
     * @see FocusPathManager#VIEW_ID_MARK_FOCUS_COLLEAGUE
     */
    public static interface ISimpleFocusColleague {

    }

}

