package com.example.administrator.mybezier;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2018/5/9 0009.
 */

public class MyPathTypeEvaluator implements TypeEvaluator<Long> {
    @Override
    public Long evaluate(float fraction, Long startValue, Long endValue) {
        return endValue-startValue;
    }
}
