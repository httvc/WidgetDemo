package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PieChart extends View {
    private static final int RADIUS = (int) Utils.dp2px(150);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int LENGTH = (int) Utils.dp2px(20);
    private static final int PULLED_OUT_INDEX = 2;
    RectF bounds = new RectF();
    int[] angles = {60, 100, 120, 80};
    int[] colors = {Color.parseColor("#2979ff"),
            Color.parseColor("#c2185b"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF8F00")};

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set((float) getWidth() / 2 - RADIUS,
                (float) getHeight() / 2 - RADIUS,
                (float) getWidth() / 2 + RADIUS,
                (float) getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == PULLED_OUT_INDEX) {
                canvas.translate((float) Math.cos(Math.toRadians(
                        currentAngle + (float) angles[i] / 2)) * LENGTH,
                        (float) Math.sin(Math.toRadians(
                                currentAngle + (float) angles[i] / 2)) * LENGTH
                );
            }
            canvas.drawArc(bounds, currentAngle,
                    angles[i],
                    true, paint);
            canvas.restore();
            currentAngle += angles[i];
        }
    }
}
