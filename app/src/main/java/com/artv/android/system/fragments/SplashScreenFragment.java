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
import com.artv.android.core.ILogger;
import com.artv.android.core.IPercentListener;
import com.artv.android.core.campaign.CampaignsWorker;
import com.artv.android.core.campaign.old.ICampaignPrepareCallback;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.init.IInitCallback;
import com.artv.android.core.init.InitResult;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.state.ArTvState;
import com.artv.android.core.state.StateWorker;

/**
 * Created by Misha on 6/30/2015.
 */
public final class SplashScreenFragment extends BaseFragment implements View.OnClickListener, ILogger, IPercentListener {

    private ProgressBar pbLoading;
    private Button btnClearConfigInfo;
    private TextView tvLog;
    private TextView tvPercent;

    private StateWorker mStateWorker;
    private InitWorker mInitWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private CampaignsWorker mCampaignsWorker;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        initLogic();
    }

    private final void initLogic() {
        mStateWorker = getApplicationLogic().getStateWorker();
        mInitWorker = getApplicationLogic().getInitWorker();
        mConfigInfoWorker = getApplicationLogic().getConfigInfoWorker();
        mCampaignsWorker = getApplicationLogic().getCampaignWorker();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_splash_screen, _container, false);
        prepareViews(view);
        return view;
    }

    private final void prepareViews(final View _view) {
        pbLoading = (ProgressBar) _view.findViewById(R.id.pbLoading_FSS);
        btnClearConfigInfo = (Button) _view.findViewById(R.id.btnClearConfigInfo_FSS);
        tvLog = (TextView) _view.findViewById(R.id.tvLog_FSS);
        tvLog.setMovementMethod(new ScrollingMovementMethod());
        tvPercent = (TextView) _view.findViewById(R.id.tvPercent_FSS);

        btnClearConfigInfo.setOnClickListener(this);
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        if (_savedInstanceState == null) beginInitializing();
    }

    private final void beginInitializing() {
        mInitWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());

        mInitWorker.startInitializing(
                new IInitCallback() {
                    @Override
                    public final void onInitSuccess(final InitResult _result) {
                        printMessage(_result.getMessage());
                        showProgressUi();
                        beginCampaignLogic();
                    }

                    @Override
                    public final void onProgress(final InitResult _result) {
                        printMessage(_result.getMessage());
                    }

                    @Override
                    public final void onInitFail(final InitResult _result) {
                        printMessage(_result.getMessage());
                    }
                }
        );
    }

    private final void showProgressUi() {
        pbLoading.setVisibility(View.VISIBLE);
        tvPercent.setVisibility(View.VISIBLE);
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.btnClearConfigInfo_FSS:
                mConfigInfoWorker.notifyNeedRemoveConfigInfo();
                break;
        }
    }

    private final void beginCampaignLogic() {
        mCampaignsWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
        mCampaignsWorker.setInitData(mInitWorker.getInitData());
        mCampaignsWorker.setUiLogger(this);
        mCampaignsWorker.setPercentListener(this);
        if (mCampaignsWorker.hasCampaignToPlay()) {
            printMessage("Has campaigns to play: not implemented yet");
        } else {
            mCampaignsWorker.doInitialCampaignDownload();
//            mCampaignsWorker.doCampaignLogic(new ICampaignPrepareCallback() {
//                @Override
//                public final void onPrepared() {
//                    mStateWorker.setState(ArTvState.STATE_PLAY_MODE);
//                }
//            });
        }

    }

    @Override
    public final void printMessage(final String _message) {
        printMessage(true, _message);
    }

    @Override
    public final void printMessage(final boolean _fromNewLine, final String _message) {
        tvLog.append((_fromNewLine ? "\n " : "") + _message);
    }

    @Override
    public final void onPercentUpdate(final double _percent) {
        tvPercent.setText(String.format("%.2f%%", _percent / 100));
        pbLoading.setProgress((int) _percent);
    }
}
