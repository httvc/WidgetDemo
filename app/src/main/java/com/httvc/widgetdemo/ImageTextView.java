package com.httvc.widgetdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

public class ImageTextView extends View {
    private static final float WIDTH = Utils.dp2px(300);
    private static final float PADDING = Utils.dp2px(100);
    private static final float EDGE_WIDTH = Utils.dp2px(20);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    RectF rectF;
    String str = "Mes系统是一款用于工业物联网的执行系统中的一款开发，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发.，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发..，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发...，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发....，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发.....，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发......，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发.......，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发........，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发.........，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发..........，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发...........，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发............，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发.............，" +
            "Mes系统是一款用于工业物联网的执行系统中的一款开发..............";
    private Bitmap bitmap;
    float[] measuredWidth = new float[1];
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    int index = 0;
    int oldIndex = 0;
    int i=0;

    {
        bitmap = getPicture((int) WIDTH);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(PADDING, PADDING, PADDING + WIDTH / 2, PADDING + WIDTH / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(bitmap,getWidth()-WIDTH,WIDTH+PADDING,paint);
        int saveLayer = canvas.saveLayer(rectF, paint, ALL_SAVE_FLAG);
        canvas.save();
        canvas.drawOval(rectF, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveLayer);

        paint.setTextSize(Utils.dp2px(14));
        paint.setColor(Color.parseColor("#000000"));
        paint.setTextAlign(Paint.Align.LEFT);

        while (index!=(str.length())){
            float m_height = EDGE_WIDTH + paint.getFontSpacing() * i;
            oldIndex = index;
            if (PADDING<=m_height && m_height<=(PADDING + WIDTH / 2)){
                index = paint.breakText(str, index, str.length(), true, PADDING, measuredWidth);
                index+=oldIndex;
                canvas.drawText(str, oldIndex, index, 0,m_height , paint);

                oldIndex = index;
                index = paint.breakText(str, index, str.length(), true, getWidth()-(PADDING + WIDTH / 2), measuredWidth);
                index+=oldIndex;
                canvas.drawText(str, oldIndex, index, PADDING+WIDTH/2,m_height , paint);
            }else {
                index = paint.breakText(str, index, str.length(), true, getWidth(), measuredWidth);
                index+=oldIndex;
                canvas.drawText(str, oldIndex, index, 0, m_height , paint);
            }
            i++;
        }
    }

    Bitmap getPicture(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.mes_launcher, options);
        options.inSampleSize = calculateInSampleSize(options, width, width);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;

        options.inTargetDensity = width;

        return BitmapFactory.decodeResource(getResources(), R.mipmap.mes_launcher, options);
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
