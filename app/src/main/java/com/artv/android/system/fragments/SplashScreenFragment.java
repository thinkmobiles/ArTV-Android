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
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.database.DBManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        tvLog.setMovementMethod(new ScrollingMovementMethod());

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

}
