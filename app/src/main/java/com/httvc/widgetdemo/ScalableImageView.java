package com.httvc.widgetdemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.core.view.GestureDetectorCompat;

//双向滑动的
public class ScalableImageView extends View {
    private static final float IMAGE_WIDTH=Utils.dp2px(300);
    private static final float OVER_SCALE_FACTOR=1.5f;
    Bitmap bitmap;
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    float offsetX;
    float offsetY;
    float originalOffsetX;
    float originalOffsetY;

    float smallScale;
    float bigScale;
    boolean big;//判断当前是否是大图
    float currentScale;//从0到1f的值

    ObjectAnimator scaleAnimator;

    GestureDetectorCompat detector;
    HenGestureListener gestureListener=new HenGestureListener();
    HenFlingRunner henFlingRunner=new HenFlingRunner();
    ScaleGestureDetector scaleDetector;//手指缩放  ScaleGestureDetectorCompat不是其兼容类
    HenScaleListener henScaleListener=new HenScaleListener();
    OverScroller scroller;

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap=Utils.getPicture(getResources(),(int) IMAGE_WIDTH);
       /* detector=new GestureDetectorCompat(context,this);
        detector.setOnDoubleTapListener(this);//双击*/

        detector=new GestureDetectorCompat(context,gestureListener);
        scroller=new OverScroller(context);
        scaleDetector=new ScaleGestureDetector(context,henScaleListener);

        //双击第二种写法 简单写法
       /* detector=new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });*/

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX =((float) getWidth()-bitmap.getWidth())/2;
        originalOffsetY =((float) getHeight()-bitmap.getHeight())/2;

        if ((float)bitmap.getWidth()/bitmap.getHeight()>(float) getWidth()/getHeight()) {
            smallScale=(float) getWidth()/bitmap.getWidth();
            bigScale=(float) getHeight()/bitmap.getHeight()*OVER_SCALE_FACTOR;
        }else {
            smallScale=(float) getHeight()/bitmap.getHeight();
            bigScale=(float) getWidth()/bitmap.getWidth()*OVER_SCALE_FACTOR;
        }
        currentScale=smallScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked()==MotionEvent.ACTION_UP){
            performClick();
        }
        boolean result=scaleDetector.onTouchEvent(event);
        if (!scaleDetector.isInProgress()){//isInProgress 检测是不是双指缩放
            result=detector.onTouchEvent(event);
        }
        return result;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scaleFraction=(currentScale-smallScale)/(bigScale-smallScale);
        canvas.translate(offsetX*scaleFraction,offsetY*scaleFraction);
       // float scale=smallScale+(bigScale-smallScale)*scaleFraction ;
        canvas.scale(currentScale,currentScale,getWidth()/2f,getHeight()/2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY,paint);
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    class HenGestureListener extends GestureDetector.SimpleOnGestureListener{
        /**
         * 收到ACTION_DOWN之后调用
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;//必须返回true后面的才能收到
        }

        /**
         * 预按下
         * @param e
         */
        @Override
        public void onShowPress(MotionEvent e) {

        }

        //每次单下抬起(单击)
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        //手指按下移动
        //
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (big){
                offsetX-=distanceX;
                offsetX=Math.min(offsetX,(bitmap.getWidth()*bigScale-getWidth())/2);
                offsetX=Math.max(offsetX,-(bitmap.getWidth()*bigScale-getWidth())/2);
                offsetY-=distanceY;
                offsetY=Math.min(offsetY,(bitmap.getHeight()*bigScale-getHeight())/2);
                offsetY=Math.max(offsetY,-(bitmap.getHeight()*bigScale-getHeight())/2);
                invalidate();
            }
            return false;
        }

        //长按
        @Override
        public void onLongPress(MotionEvent e) {

        }

        //手指快速滑动(滑动的时候手指抬起了)
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (big){
                scroller.fling((int)offsetX,(int)offsetY,(int)velocityX,(int)velocityY,
                        (int)-(bitmap.getWidth()*bigScale-getWidth())/2,
                        (int)(bitmap.getWidth()*bigScale-getWidth())/2,
                        (int)-(bitmap.getHeight()*bigScale-getWidth())/2,
                        (int)(bitmap.getHeight()*bigScale-getHeight())/2);
           /* for (int i = 10; i <100 ; i+=10) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                },i);
            }*/

                //作用是让 postOnAnimation里面的代码在下一帧到来的时候去主线程执行
              //  postOnAnimation(ScalableImageView.this);
                postOnAnimation(henFlingRunner);
                //立即去主线程执行
                //post(this);
            }
            return false;
        }
        //单击确认（确认想单击还是想双击）用于做双击的监听
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        //双击 300ms 点击四次 这个方法触发两次
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big=!big;
            if (big){
                offsetX=(e.getX()-getWidth()/2f)-(e.getX()-getWidth()/2f)*bigScale/smallScale;
                offsetY=(e.getX()-getHeight()/2f)-(e.getX()-getHeight()/2f)*bigScale/smallScale;
                getScaleAnimator().start();
            }else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        //当手指双击抬起，再按下会触发不太起触发，抬起后消失
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }
    private ObjectAnimator getScaleAnimator(){
        if (scaleAnimator==null){
            scaleAnimator=ObjectAnimator.ofFloat(this,"currentScale",0);
            /*scaleAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    offsetX=0;
                    offsetY=0;
                }
            });*/
        }
        scaleAnimator.setFloatValues(smallScale,bigScale);
        return scaleAnimator;
    }



    private void postOnAnimation(ScalableImageView scalableImageView) {
       if (scroller.computeScrollOffset()){//这个动画是否还在执行中，动画执行完返回false
           offsetX=scroller.getCurrX();
           offsetY=scroller.getCurrY();
           invalidate();
           postOnAnimation(this);
           //兼容
         //  ViewCompat.postOnAnimation(this,this);
       }
    }

    class HenFlingRunner implements Runnable{

        @Override
        public void run() {
            if (scroller.computeScrollOffset()){//这个动画是否还在执行中，动画执行完返回false
                offsetX=scroller.getCurrX();
                offsetY=scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
                //兼容
                //  ViewCompat.postOnAnimation(this,this);
            }
        }
    }

    class HenScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;
        //在scale过程中
        @Override
        public boolean onScale(ScaleGestureDetector detector) {//detector里有倍数和焦点，焦点是两个手指的中心
            detector.getFocusX();//焦点
            detector.getFocusY();
           currentScale =initialScale*detector.getScaleFactor();//缩放倍数从1到0
            invalidate();
            return false;
        }

        //在scale之前
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale=currentScale;
            return true;
        }

        //在scale之后
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }
}
