package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MultiTouchView2 extends View {
    private static final float IMAGE_WIDTH = Utils.dp2px(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    private float offsetX;
    private float offsetY;
    private float downY;
    private float downX;
    private float originalOffsetX;
    private float originalOffsetY;
    int trackingPointId;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getPicture(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            //多点触控中index代表手指个数，id代表那个手指不会改变
            case MotionEvent.ACTION_DOWN:
                trackingPointId = event.getPointerId(0);
                downX = event.getX();//多点触控中event.getX()代表index为0的手指，id不变横坐标
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointId);
                offsetX = originalOffsetX + event.getX(index) - downX;
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);//多点触控中event.getX()代表index为0的手指，id不变横坐标
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointId = event.getPointerId(newIndex);
                    downX = event.getX(actionIndex);//多点触控中event.getX()代表index为0的手指，id不变横坐标
                    downY = event.getY(actionIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
