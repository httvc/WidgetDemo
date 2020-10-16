package com.httvc.widgetdemo;

import android.content.Context;
import android.util.AttributeSet;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int size = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(size,size);
    }
}
