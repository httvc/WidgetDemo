package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CameraView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float WIDTH=Utils.dp2px(200);
    private static final float D_W=Utils.dp2px(100);

    Camera camera = new Camera();

    float topFlip=0;
    float bottomFlip=0;
    float flipRotation=0;

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        camera.setLocation(0,0,
                -8*Utils.getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制上半部分
        canvas.save();
        canvas.translate(D_W+WIDTH/2,D_W+WIDTH/2 );
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect((float) (-WIDTH*Math.sqrt(2)),(float)(-WIDTH*Math.sqrt(2)),(float)(WIDTH*Math.sqrt(2)),0);
        canvas.rotate(flipRotation);
        canvas.translate(-(D_W+WIDTH/2),-(D_W+WIDTH/2));
        canvas.drawBitmap(Utils.getPicture(getResources(),(int) WIDTH),D_W,D_W,paint);
        canvas.restore();

        //绘制下半部分
        canvas.save();
        canvas.translate(D_W+WIDTH/2,D_W+WIDTH/2 );
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect((float)(-WIDTH*Math.sqrt(2)),0,(float)(WIDTH*Math.sqrt(2)),
                (float)(WIDTH*Math.sqrt(2)));
        canvas.rotate(flipRotation);
        canvas.translate(-(D_W+WIDTH/2),-(D_W+WIDTH/2));
        canvas.drawBitmap(Utils.getPicture(getResources(),(int) WIDTH),D_W,D_W,paint);
        canvas.restore();
    }
}
