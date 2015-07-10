package com.artv.android.system.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artv.android.R;
import com.artv.android.core.init.InitCallback;
import com.artv.android.core.init.InitResult;
import com.artv.android.core.state.IArTvStateChangeListener;

/**
 * Created by Misha on 6/30/2015.
 */
public final class SplashScreenFragment extends BaseFragment implements View.OnClickListener, IArTvStateChangeListener {

    private ProgressBar mLoadingProgressBar;
    private Button btnClearConfigInfo;
    private Button btnShowVideo;
    private TextView tvLog;

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_splash_screen, null);

        mLoadingProgressBar = (ProgressBar) view.findViewById(R.id.pbLoading_FSS);
        btnClearConfigInfo = (Button) view.findViewById(R.id.btnClearConfigInfo_FSS);
        btnShowVideo = (Button) view.findViewById(R.id.btnShowVideo_FSS);
        tvLog = (TextView) view.findViewById(R.id.tvLog_FSS);
        tvLog.setMovementMethod(new ScrollingMovementMethod());

        btnClearConfigInfo.setOnClickListener(this);
        btnShowVideo.setOnClickListener(this);
        btnShowVideo.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        beginInitializing();
    }

    @Override
    public final void onStart() {
        super.onStart();
        getMyApplication().getApplicationLogic().getStateWorker().addStateChangeListener(this);
    }

    @Override
    public final void onStop() {
        super.onStop();
        getMyApplication().getApplicationLogic().getStateWorker().removeStateChangeListener(this);
    }

    private final void beginInitializing() {
        getMyApplication().getApplicationLogic().getInitWorker().setConfigInfo(
                getMyApplication().getApplicationLogic().getConfigInfoWorker().getConfigInfo());

        getMyApplication().getApplicationLogic().getInitWorker().startInitializing(
                new InitCallback() {
                    @Override
                    public final void onInitSuccess(final InitResult _result) {
                        tvLog.append("\n" + _result.getMessage());
                    }

                    @Override
                    public final void onProgress(final InitResult _result) {
                        tvLog.append("\n" + _result.getMessage());
                    }

                    @Override
                    public final void onInitFail(final InitResult _result) {
                        tvLog.append("\n" + _result.getMessage());
                    }
                }
        );
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.btnClearConfigInfo_FSS:
                    getMyApplication().getApplicationLogic().getConfigInfoWorker().getConfigInfoListener().onNeedRemoveConfigInfo();
                break;

            case R.id.btnShowVideo_FSS:
                getFragmentManager().beginTransaction().replace(R.id.flFragmentContainer_AM, new MediaPlayerFragment()).commit();
                break;
        }
    }

    @Override
    public final void onArTvStateChanged() {
        btnShowVideo.setVisibility(View.VISIBLE);
    }
}
