package com.httvc.widgetdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;

public class TouchLayout extends ViewGroup {

    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int delta=(int) ev.getY();//纵向移动距离
        if (Math.abs(delta)>4){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
               // setBackgroundColor(PRESSED_COLOR);
                return true;

            case MotionEvent.ACTION_UP:
              //  setBackgroundColor(NORMAL_COLOR);

                // For this particular app we want the main work to happen
                // on ACTION_UP rather than ACTION_DOWN. So this is where
                // we will call performClick().
                performClick();
                return true;
        }
        return false;
    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    @Override
    public boolean performClick() {
        super.performClick();
        launchMissile();
        return true;
    }

    private void launchMissile() {
        Toast.makeText(getContext(), "Missile launched", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
