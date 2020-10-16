package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ProvinceView extends View {
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        paint.setTextSize(Utils.dp2px(100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("北京市",getWidth()/2,getHeight()/2,paint);
    }
}
