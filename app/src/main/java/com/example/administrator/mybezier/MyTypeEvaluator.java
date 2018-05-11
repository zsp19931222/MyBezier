package com.example.administrator.mybezier;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class MyTypeEvaluator implements TypeEvaluator {
    @Override
    public Point evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + ((endPoint.getX() - startPoint.getX()) * fraction);
        float y = startPoint.getY() + ((endPoint.getY() - startPoint.getY()) * fraction);
        return new Point(x, y);
    }
}
