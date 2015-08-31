package com.artv.android.system.fragments.splash;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artv.android.R;
import com.artv.android.app.start.StartWorker;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.log.ILogger;
import com.artv.android.system.fragments.BaseFragment;

/**
 * Created by Misha on 6/30/2015.
 */
public final class SplashScreenFragment extends BaseFragment implements ILogger,
        ISplashFragmentListener {

    private ProgressBar pbLoading;
    private TextView tvLog;
    private TextView tvPercent;

    private StartWorker mStartWorker;
    private ConfigInfoWorker mConfigInfoWorker;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        initLogic();
    }

    private final void initLogic() {
        mStartWorker = getApplicationLogic().getStartWorker();
        mConfigInfoWorker = getApplicationLogic().getConfigInfoWorker();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        ArTvLogger.addLogger(this);
        mStartWorker.setSplashFragmentListener(this);

        final View view = _inflater.inflate(R.layout.fragment_splash_screen, _container, false);
        findViews(view);
        prepareViews();
        return view;
    }

    private final void findViews(final View _view) {
        pbLoading = (ProgressBar) _view.findViewById(R.id.pbLoading_FSS);
        tvLog = (TextView) _view.findViewById(R.id.tvLog_FSS);
        tvPercent = (TextView) _view.findViewById(R.id.tvPercent_FSS);
    }

    private final void prepareViews() {
        tvLog.setMovementMethod(new ScrollingMovementMethod());
        tvLog.setVisibility(mConfigInfoWorker.getConfigInfo().getShowDebugInfo()
                ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        if (_savedInstanceState == null) mStartWorker.beginInitializing();
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        ArTvLogger.removeLogger(this);
        mStartWorker.setSplashFragmentListener(null);
    }

    @Override
    public final void showProgressUi() {
        pbLoading.setVisibility(View.VISIBLE);
        tvPercent.setVisibility(View.VISIBLE);
    }

    @Override
    public final void onPercentLoaded(final double _percent) {
        tvPercent.setText(String.format("%.2f%%", _percent));
        pbLoading.setProgress((int) (_percent * 100));
    }

    @Override
    public final void printMessage(final String _message) {
        printMessage(true, _message);
    }

    @Override
    public final void printMessage(final boolean _fromNewLine, final String _message) {
        tvLog.append((_fromNewLine ? "\n " : "") + _message);
    }

}
