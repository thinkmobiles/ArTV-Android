package com.artv.android.system.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.artv.android.R;
import com.artv.android.system.MyApplication;

/**
 * Created by Misha on 6/30/2015.
 */
public final class SplashScreenFragment extends BaseFragment {
    private ProgressBar mLoadingProgressBar;
    private Button btnClearConfigInfo;

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_splash_screen, null);

        mLoadingProgressBar = (ProgressBar) view.findViewById(R.id.pbLoading_FSS);
        btnClearConfigInfo = (Button) view.findViewById(R.id.btnClearConfigInfo_FSS);

        btnClearConfigInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(final View _v) {
                getMyApplication().getApplicationLogic().getConfigInfoWorker().getConfigInfoListener().onNeedRemoveConfigInfo();
            }
        });

        return view;
    }
}
