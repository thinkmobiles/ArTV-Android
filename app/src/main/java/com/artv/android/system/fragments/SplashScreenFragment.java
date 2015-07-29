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
import com.artv.android.core.campaign.load.CampaignLoadResult;
import com.artv.android.core.campaign.CampaignWorker;
import com.artv.android.core.campaign.load.ICampaignsDownloadListener;
import com.artv.android.core.config_info.ConfigInfoWorker;
import com.artv.android.core.init.IInitCallback;
import com.artv.android.core.init.InitResult;
import com.artv.android.core.init.InitWorker;
import com.artv.android.core.state.IArTvStateChangeListener;
import com.artv.android.core.state.StateWorker;

/**
 * Created by Misha on 6/30/2015.
 */
public final class SplashScreenFragment extends BaseFragment implements View.OnClickListener, IArTvStateChangeListener, ICampaignsDownloadListener {

    private ProgressBar pbLoading;
    private Button btnClearConfigInfo;
    private Button btnShowVideo;
    private TextView tvLog;

    private StateWorker mStateWorker;
    private InitWorker mInitWorker;
    private ConfigInfoWorker mConfigInfoWorker;
    private CampaignWorker mCampaignWorker;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        initLogic();
    }

    private final void initLogic() {
        mStateWorker = getMyApplication().getApplicationLogic().getStateWorker();
        mInitWorker = getMyApplication().getApplicationLogic().getInitWorker();
        mConfigInfoWorker = getMyApplication().getApplicationLogic().getConfigInfoWorker();
        mCampaignWorker = getMyApplication().getApplicationLogic().getCampaignWorker();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_splash_screen, _container, false);

        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading_FSS);
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
        if (_savedInstanceState == null) beginInitializing();
    }

    @Override
    public final void onStart() {
        super.onStart();
        mStateWorker.addStateChangeListener(this);
        mCampaignWorker.addCampaignLoadListener(this);
    }

    @Override
    public final void onStop() {
        super.onStop();
        mStateWorker.removeStateChangeListener(this);
        mCampaignWorker.removeCampaignLoadListener(this);
    }

    private final void beginInitializing() {
        mInitWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());

        mInitWorker.startInitializing(
                new IInitCallback() {
                    @Override
                    public final void onInitSuccess(final InitResult _result) {
                        tvLog.append("\n" + _result.getMessage());
                        pbLoading.setProgress(100);
                        beginCampaignLogic();
                    }

                    @Override
                    public final void onProgress(final InitResult _result) {
                        tvLog.append("\n" + _result.getMessage());
                        pbLoading.incrementProgressBy(17);
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
                    mConfigInfoWorker.notifyNeedRemoveConfigInfo();
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

    private final void beginCampaignLogic() {
        mCampaignWorker.setConfigInfo(mConfigInfoWorker.getConfigInfo());
        mCampaignWorker.setInitData(mInitWorker.getInitData());
        mCampaignWorker.doCampaignLogic();
    }

    @Override
    public final void progressMessage(final String _message) {
        tvLog.append("\n" + _message);
    }

    @Override
    public final void onProgress(final int _percent) {

    }

    @Override
    public final void onCampaignLoaded(final CampaignLoadResult _result) {
        tvLog.append("\n" + _result.getMessage());
    }

    @Override
    public final void onCampaignLoadFailed(final CampaignLoadResult _result) {
        tvLog.append("\n" + _result.getMessage());
    }
}
