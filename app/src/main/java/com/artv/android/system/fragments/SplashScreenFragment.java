package com.artv.android.system.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.artv.android.R;
import com.artv.android.core.init.InitCallback;
import com.artv.android.core.init.InitResult;
import com.artv.android.core.state.ArTvState;

/**
 * Created by Misha on 6/30/2015.
 */
public final class SplashScreenFragment extends BaseFragment {

    private ProgressBar mLoadingProgressBar;
    private Button btnClearConfigInfo;
    private TextView tvLog;

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_splash_screen, null);

        mLoadingProgressBar = (ProgressBar) view.findViewById(R.id.pbLoading_FSS);
        btnClearConfigInfo = (Button) view.findViewById(R.id.btnClearConfigInfo_FSS);
        tvLog = (TextView) view.findViewById(R.id.tvLog_FSS);

        btnClearConfigInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(final View _v) {
                getMyApplication().getApplicationLogic().getConfigInfoWorker().getConfigInfoListener().onNeedRemoveConfigInfo();
            }
        });

        return view;
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        beginInitializing();
    }

    private final void beginInitializing() {
        getMyApplication().getApplicationLogic().getInitWorker().startInitializing(
                getMyApplication().getApplicationLogic().getConfigInfoWorker().getConfigInfo(),
                new InitCallback() {
                    @Override
                    public final void onInitSuccess(final InitResult _result) {
                        tvLog.append(_result.getMessage());
                    }

                    @Override
                    public final void onProgress(final InitResult _result) {
                        tvLog.append(_result.getMessage());
                    }

                    @Override
                    public final void onInitFail(final InitResult _result) {
                        tvLog.append(_result.getMessage());
                    }
                }
        );
    }

}
