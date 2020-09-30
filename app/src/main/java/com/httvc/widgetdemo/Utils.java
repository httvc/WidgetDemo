package com.httvc.widgetdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author: zhpan
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public class Utils {

    /**
     * 此处为Application的context不会造成内存泄漏
     */
    private static Context context;

    /**
     * 是否为debug版
     */
    private static Boolean sDebug;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
        isDebugBuild();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static Boolean isDebug() {
        return sDebug;
    }


    /**
     * @return 是否是debug版本
     */
    private static boolean isDebugBuild() {
        if (sDebug == null) {
            try {
                final Class<?> activityThread = Class.forName("android.app.ActivityThread");
                final Method currentPackage = activityThread.getMethod("currentPackageName");
                final String packageName = (String) currentPackage.invoke(null, (Object[]) null);
                final Class<?> buildConfig = Class.forName(packageName + ".BuildConfig");
                final Field DEBUG = buildConfig.getField("DEBUG");
                DEBUG.setAccessible(true);
                sDebug = DEBUG.getBoolean(null);
            } catch (final Throwable t) {
                final String message = t.getMessage();
                if (message != null && message.contains("BuildConfig")) {
                    // Proguard obfuscated build. Most likely a production build.
                    sDebug = false;
                } else {
                    sDebug = BuildConfig.DEBUG;
                }
            }
        }
        return sDebug;
    }

    public static float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float getZForCamera(){
        return Resources.getSystem().getDisplayMetrics().density;
    }
    public static Bitmap getPicture(Resources resources,int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.mipmap.mes_launcher, options);
        options.inSampleSize = calculateInSampleSize(options, width, width);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;

        options.inTargetDensity = width;

        return BitmapFactory.decodeResource(resources, R.mipmap.mes_launcher, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}