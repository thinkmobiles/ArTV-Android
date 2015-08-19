package com.artv.android.app.playback;

import android.os.Handler;
import android.widget.Toast;

import com.artv.android.core.Constants;
import com.artv.android.core.UrlHelper;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.GlobalConfig;
import com.artv.android.database.DbWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 8/18/2015.
 */
public final class PlaybackWorker implements IVideoCompletionListener {

    private DbWorker mDbWorker;
    private GlobalConfig mGlobalConfig;
    private IPlaybackController mPlaybackController;

    private List<Campaign> mCampaigns;
    private int mCampaignPos = 0;
    private int mAssetPos = 0;

    public final void setPlaybackController(final IPlaybackController _controller) {
        mPlaybackController = _controller;
    }

    public final void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void setGlobalConfig(final GlobalConfig _globalConfig) {
        mGlobalConfig = _globalConfig;
    }

    public final IVideoCompletionListener getVideoCompletionListener() {
        return this;
    }

    public final void startPlayback() {
//        mPlaybackController.showMsgBoardCampaign(mDbWorker.getMsgBoardCampaign());
        mCampaigns = mDbWorker.getAllCampaigns();
        if (mCampaigns.isEmpty()) {
            ArTvLogger.printMessage("No campaigns to play");
            return;
        }

        play();
    }

    public final void stopPlayback() {
        
    }

    @Override
    public final void onVideoCompleted() {
        nextPosition();
        play();
    }

    private final void play() {
        final Asset asset = mCampaigns.get(mCampaignPos).assets.get(mAssetPos);

        if (UrlHelper.isYoutubeUrl(asset.url)) {
            mPlaybackController.playYoutubeLink(asset.url);

        } else if (asset.url.endsWith("jpg")) {
            mPlaybackController.playLocalPicture(Constants.PATH + asset.url);
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    nextPosition();
                    play();
                }
            }, mGlobalConfig.getServerDefaultPlayTime() * 1000);

        } else {
            mPlaybackController.playLocalVideo(Constants.PATH + asset.url);
        }

        printMessage(asset.url);
    }

    private final void nextPosition() {
        mAssetPos++;
        if (mAssetPos == mCampaigns.get(mCampaignPos).assets.size()) {
            mAssetPos = 0;
            mCampaignPos++;

            if (mCampaignPos == mCampaigns.size()) {
                mCampaignPos = 0;
            }
        }
    }

    private final void printMessage(final String _msg) {
        ArTvLogger.printMessage("Playing: " + _msg);
    }

}
