package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Circle1VIew extends View {
    private static final int RADIUS=(int)Utils.dp2px(80);
    private static final int PADDING=(int)Utils.dp2px(30);

    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public Circle1VIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=(PADDING+RADIUS)*2;
        int height=(PADDING+RADIUS)*2;

        int widrhSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(resolveSize(width,widthMeasureSpec),
                resolveSize(height,heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(PADDING+RADIUS,PADDING+RADIUS,RADIUS,paint);
    }
}
