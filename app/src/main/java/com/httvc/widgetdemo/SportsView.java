package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SportsView extends View {
    private static final int RADIUS = (int) Utils.dp2px(150);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int RING_WIDTH = (int) Utils.dp2px(20);
    private static final int PULLED_OUT_INDEX = 2;
    private static final int CIRCLE_COLOR= Color.parseColor("#90a4ae");
    private static final int HIGHLIGHT_COLOR= Color.parseColor("#ff4081");
    RectF rectF;
    Rect rect=new Rect();
    Paint.FontMetrics fontMetrics=new Paint.FontMetrics();
    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(100));
        //设置字体
      //  paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),".ttf"));

        //文字居中
        paint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF=new RectF((float) getWidth()/2-RADIUS,(float) getHeight()/2-RADIUS, (float) getWidth()/2+RADIUS, (float) getHeight()/2+RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle( (float) getWidth()/2,(float) getHeight()/2,
                RADIUS,paint);

        //绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawArc(rectF,-90,
                225,false,paint);

        //绘制文字
        paint.setStyle(Paint.Style.FILL);

        //设置固定的文字的时候偏移
        paint.getTextBounds("abab",0,"abab".length(),rect);
        //int offset=(rect.top+rect.bottom)/2;

        //设置动态文字的偏移
        paint.getFontMetrics(fontMetrics);
        float offset=(fontMetrics.ascent+fontMetrics.descent)/2;
        canvas.drawText("abab",(float) getWidth()/2,
                (float) getHeight()/2-offset,paint);
    }
}
