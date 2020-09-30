package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {
    private float progress=0;
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius=Utils.dp2px(50);

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    {
        paint.setColor(Color.RED);
    }
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);
    }
}
