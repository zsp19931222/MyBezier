package com.example.administrator.mybezier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class TaiJi extends View {
    private Path path;
    private Paint paint;
    private int w, h;

    public TaiJi(Context context) {
        super(context);

    }

    public TaiJi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(w / 2, h / 2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        canvas.drawRect(new RectF(-100,-100,100,100),paint);
        path.moveTo(0, -100);
        path.quadTo(-50, -50, 0, 0);
        path.quadTo(50, 50, 0, 100);
        path.quadTo(-200,0,0,-100);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path, paint);
    }
}
