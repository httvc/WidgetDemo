package com.httvc.widgetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class TestActivity extends AppCompatActivity {
  //  CameraView circleView;
  //  ImageView view;
  //  PointView pointView;
  //  ParabolaView parabola;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
      //  parabola=(ParabolaView)findViewById(R.id.parabola);
     //      pointView=(PointView)findViewById(R.id.point);

       // circleView=(CameraView) findViewById(R.id.circle);
        /*ObjectAnimator objectAnimator=ObjectAnimator.
                ofFloat(circleView,"radius",Utils.dp2px(150));
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();*/
/*
        ObjectAnimator bottomFlipAnimator=ObjectAnimator.ofFloat(
                circleView,"bottomFlip",45);
        bottomFlipAnimator.setDuration(1000);

        ObjectAnimator flipRotationAnimator =ObjectAnimator.ofFloat(circleView,"flipRotation",270);
        flipRotationAnimator.setDuration(1000);

        ObjectAnimator topFlipAnimator =ObjectAnimator.ofFloat(
                circleView,"topFlip",-45);
        topFlipAnimator.setDuration(1000);

        ObjectAnimator topFlipAnimator1 =ObjectAnimator.ofFloat(
                circleView,"topFlip",0);
        topFlipAnimator1.setDuration(1000);

        ObjectAnimator flipRotationAnimator1 =ObjectAnimator.ofFloat(circleView,"flipRotation",0);
        flipRotationAnimator1.setDuration(1000);

        ObjectAnimator bottomFlipAnimator1=ObjectAnimator.ofFloat(
                circleView,"bottomFlip",0);
        bottomFlipAnimator1.setDuration(1000);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator,topFlipAnimator1,flipRotationAnimator1,bottomFlipAnimator1);
        animatorSet.setStartDelay(1000);
        animatorSet.start();*/

        //同时动画
     /*   PropertyValuesHolder bottomFlipHolder=PropertyValuesHolder.ofFloat(
                "bottomFlip",45);

        PropertyValuesHolder flipRotationHolder=PropertyValuesHolder.ofFloat(
                "flipRotation",270);

        PropertyValuesHolder topFlipHolder=PropertyValuesHolder.ofFloat(
                "topFlip",-45);

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(circleView,bottomFlipHolder,flipRotationHolder,topFlipHolder);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(2000);
        objectAnimator.start();*/

      /*  float length=Utils.dp2px(300);
        Keyframe keyframe1=Keyframe.ofFloat(0,0);
        Keyframe keyframe2=Keyframe.ofFloat(0.2f,0.4f*length);
        Keyframe keyframe3=Keyframe.ofFloat(0.8f,0.6f*length);
        Keyframe keyframe4=Keyframe.ofFloat(1,1*length);

        PropertyValuesHolder valuesHolder=PropertyValuesHolder.ofKeyframe(
                "translationX",keyframe1,keyframe2,keyframe3,keyframe4);
        ObjectAnimator objectAnimator1=ObjectAnimator.ofPropertyValuesHolder(
                view,valuesHolder);
        objectAnimator1.setStartDelay(1000);
        objectAnimator1.setDuration(2000);
        objectAnimator1.start();
*/
     /*   Point targetPoint=new Point((int)Utils.dp2px(300),(int)Utils.dp2px(200));
      ObjectAnimator animator=ObjectAnimator.ofObject(pointView,
              "point",new PointEvaluator(),targetPoint);
      animator.setStartDelay(1000);
      animator.setDuration(2000);
      animator.start();*/
    /* Point targetPoint=new Point((int)Utils.dp2px(200),(int)Utils.dp2px(400));
     ObjectAnimator animator=ObjectAnimator.ofObject(parabola,"point",new ParabolaEvaluator(),targetPoint);
        animator.setStartDelay(1000);
        animator.setDuration(2000);
        animator.start();*/
    }

    class ParabolaEvaluator implements TypeEvaluator<Point>{

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float x = startValue.x + fraction * (endValue.x - startValue.x);// x方向匀速移动
            float y = startValue.y + fraction * fraction * (endValue.y - startValue.y);// y方向抛物线加速移动
            return new Point((int) x,(int) y);
        }
    }

    class PointEvaluator implements TypeEvaluator<Point>{
        @Override
        public Point evaluate(float fraction, Point startValue,
                              Point endValue) {
            //(1,1) (5,5)  fraction:0.2
            float x=startValue.x+(endValue.x-startValue.x)*fraction;
            float y=startValue.y+(endValue.y-startValue.y)*fraction;

            return new Point((int) x,(int) y);
        }
    }
}