package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private static final float WIDTH = Utils.dp2px(300);
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap picture = Utils.getPicture(getResources(), (int) WIDTH);
        //canvas.clipRect(100,100,400,400);
      //  canvas.translate(100,200);
        canvas.translate(200,100);
        canvas.drawRect(0,0,WIDTH,WIDTH,paint);
        canvas.rotate(90,WIDTH/2,WIDTH/2);
        canvas.drawBitmap(picture,0,0,paint);
    }
}
