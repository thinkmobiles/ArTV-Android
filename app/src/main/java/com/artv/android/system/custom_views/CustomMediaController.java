package com.artv.android.system.custom_views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;

import com.artv.android.R;

/**
 * Created by Misha on 7/10/2015.
 */
public class CustomMediaController extends MediaController implements View.OnClickListener{
    private ImageView mFullScreenBtn;
    private Context mContext;
    private OnFullScreenBtnClickListener listener;
    private boolean isFullScreenMode;


    public void setFullScreenBtnListener(OnFullScreenBtnClickListener listener) {
        this.listener = listener;
    }

    public CustomMediaController(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        frameParams.gravity = Gravity.RIGHT|Gravity.TOP;

        View v = makeCustomBtn();
        addView(v, frameParams);
    }

    private View makeCustomBtn() {
        mFullScreenBtn = new ImageView(mContext);
        mFullScreenBtn.setImageResource(isFullScreenMode ? R.drawable.ic_fullscreen_exit_white_36dp
                : R.drawable.ic_fullscreen_white_36dp);
        mFullScreenBtn.setOnClickListener(this);

        return mFullScreenBtn;
    }

    @Override
    public void onClick(View v) {
        isFullScreenMode = !isFullScreenMode;
        mFullScreenBtn.setImageResource(isFullScreenMode ? R.drawable.ic_fullscreen_exit_white_36dp
                : R.drawable.ic_fullscreen_white_36dp);
        if(listener != null) listener.onFullScreenBtnClicked(isFullScreenMode);
    }

    public interface OnFullScreenBtnClickListener {
        void onFullScreenBtnClicked(boolean isFullScreenNow);
    }
}
