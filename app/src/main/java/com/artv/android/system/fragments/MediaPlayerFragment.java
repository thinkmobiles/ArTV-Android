package com.artv.android.system.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.artv.android.R;

/**
 * Created by Misha on 6/30/2015.
 */
public class MediaPlayerFragment extends Fragment {
    private FrameLayout mVideoContainer;
    private LinearLayout mRightContainer, mBottomContainer;

    private boolean isFullScreenMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_player, null);

        mVideoContainer = (FrameLayout) view.findViewById(R.id.flVideoContainer_FMP);
        mBottomContainer = (LinearLayout) view.findViewById(R.id.llBottomContainer_FMP);
        mRightContainer = (LinearLayout) view.findViewById(R.id.llRightContainer_FMP);


        //todo this is just for sample
        mVideoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullScreenMode) switchToBoxedMode();
                else switchToFullScreenMode();
            }
        });




        return view;
    }

    private void switchToFullScreenMode() {
        isFullScreenMode = true;
        mRightContainer.setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.GONE);
    }

    private void switchToBoxedMode() {
        isFullScreenMode = false;
        mRightContainer.setVisibility(View.VISIBLE);
        mBottomContainer.setVisibility(View.VISIBLE);
    }


}
