package com.example.administrator.mybezier;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9 0009.
 */

public class MyPathView extends View {
    private Path path;
    private Paint paint;

    private float startX, startY;
    private float pathX, pathY;

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private PathMeasure measure;


    public MyPathView(Context context) {
        super(context);
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jiantou, options);
        mMatrix = new Matrix();
    }

    private long startTime;
    private long endTime;
    private long time;
    private List<Point> points=new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                startTime=System.currentTimeMillis();
                path.moveTo(startX, startY);
                break;
            case MotionEvent.ACTION_MOVE:
                pathX = event.getX();
                pathY = event.getY();
                path.lineTo(pathX,pathY);
                invalidate();

                break;
            case MotionEvent.ACTION_UP:
                endTime=System.currentTimeMillis();
                time=endTime-startTime;
                ValueAnimator valueAnimator=ValueAnimator.ofInt(0,100);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Log.d(TAG, "onAnimationUpdate: "+animation.getAnimatedValue());

                    }
                });
                valueAnimator.setDuration(time+100);
                valueAnimator.start();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }
        measure = new PathMeasure(path, false);     // 创建 PathMeasure

        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, paint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, paint);                     // 绘制箭头
    }

    //对外提供的方法，重新绘制
    public void reset(){
        path.reset();
        invalidate();
    }

    private static final String TAG = "MyPathView";
}
