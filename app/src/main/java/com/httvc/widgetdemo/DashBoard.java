package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DashBoard extends View {
    private static final int ANGLE=120;
    private static final float RADIUS=Utils.dp2px(150);
    private static final float LENGTH=Utils.dp2px(100);
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash=new Path();
    private RectF rectF;
    private PathDashPathEffect effect;

    public DashBoard(Context context) {
        super(context);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));
        dash.addRect(0,0,Utils.dp2px(2),Utils.dp2px(10), Path.Direction.CW);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF((float) getWidth()/2-RADIUS, (float)getHeight()/2-RADIUS, (float)getWidth()/2+RADIUS, (float)getHeight()/2+RADIUS);
        Path arc=new Path();
        arc.addArc(rectF,90+(float)ANGLE/2,360-ANGLE);
        PathMeasure pathMeasure=new PathMeasure(arc,false);
        effect = new PathDashPathEffect(dash,
                (pathMeasure.getLength()-Utils.dp2px(2))/20, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画线
        canvas.drawArc(rectF,90+(float)ANGLE/2,360-ANGLE,false,paint);
        //画刻度
        paint.setPathEffect(effect);
        canvas.drawArc(rectF,90+(float)ANGLE/2,360-ANGLE,false,paint);
        paint.setPathEffect(null);

        //画指针
        canvas.drawLine((float) getWidth()/2,(float) getHeight()/2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5)))*LENGTH+(float) getWidth()/2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5)))*LENGTH+(float) getHeight()/2,
                paint);
    }

    int getAngleFromMark(int mark){
        return (int)(90+(float)ANGLE/2+(360-(float)ANGLE)/20*mark);
    }
}
