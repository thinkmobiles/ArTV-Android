package com.artv.android.system.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Misha on 7/10/2015.
 */
public class VideoViewCustom extends VideoView {

    public VideoViewCustom(Context context) {
        super(context);
    }

    public VideoViewCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoViewCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getHolder().setFixedSize(getMeasuredWidth(), getMeasuredHeight());
    }
}
