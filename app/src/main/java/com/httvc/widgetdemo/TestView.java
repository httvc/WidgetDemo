package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestView extends View {
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path=new Path();
    PathMeasure pathMeasure;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addRect((float)getWidth()/2-150,(float)getHeight()/2-300,
                (float)getWidth()/2+150,(float)getHeight()/2,
                Path.Direction.CCW);
        path.addCircle((float)getWidth()/2,(float)getHeight()/2,150,
                Path.Direction.CCW);
        pathMeasure=new PathMeasure(path,false);
        pathMeasure.getLength();//周长
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        *  WINDING
        * EVEN_ODD
        * INVERSE_WINDING
        * INVERSE_EVEN_ODD
        * */
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(path,paint);
    }
}
