package com.artv.android.app.playback;

import android.os.Handler;

import com.artv.android.core.Constants;
import com.artv.android.core.UrlHelper;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.GlobalConfig;
import com.artv.android.database.DbWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * Created by ZOG on 8/18/2015.
 */
public final class PlaybackWorker implements IVideoCompletionListener {

    private DbWorker mDbWorker;
    private GlobalConfig mGlobalConfig;
    private IPlaybackController mPlaybackController;
    private PlayModeManager mPlayModeManager;
    private List<Campaign> mCampaigns;
    private Stack<Asset> mAssetStack;

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
        mPlaybackController.showMsgBoardCampaign(mDbWorker.getMsgBoardCampaign());
        mPlayModeManager = new PlayModeManager();
        mCampaigns = mDbWorker.getAllCampaigns();
        prepareStackToPlay(mPlayModeManager, mCampaigns);
        play(mAssetStack);
    }

    private void prepareStackToPlay(final PlayModeManager _playModeManager, final List<Campaign> _campaigns) {
        Campaign campaignToPlay = null;
        switch (_playModeManager.campainToPlay(_campaigns)) {
            case 0:
                campaignToPlay = _playModeManager.getDefaultCampaign(_campaigns);
                break;
            case 1:
                campaignToPlay = hasPlayCampaignInCurentTime(_campaigns, _playModeManager);
                break;
        }

        if (campaignToPlay != null) {
            mAssetStack = getStackAssets(campaignToPlay.assets);
        } else {
            mAssetStack = getStackAssets(_playModeManager.getDefaultCampaign(_campaigns).assets);
        }
    }

    public final void stopPlayback() {

    }

    @Override
    public final void onVideoCompleted() {

    }

    private void play() {
        if (isVideoFormat(_assets.pop().url)) {
            playVideo(_assets);
        } else if (isPictureFormat(_assets.pop().url)) {
            playPicture(_assets);
        } else if (UrlHelper.isYoutubeUrl(_assets.pop().url)) {
            playYouTubeVideo(_assets);
        }
    }

    private boolean isVideoFormat(final String _fileName) {
        return _fileName.endsWith(".mp4") || _fileName.endsWith(".mov") ||
                _fileName.endsWith(".3gp") || _fileName.endsWith(".mkv") ||
                _fileName.endsWith(".avi") || _fileName.endsWith(".mpg") ||
                _fileName.endsWith(".flv");
    }

    private boolean isPictureFormat(final String _fileName) {
        return _fileName.endsWith(".jpg") || _fileName.endsWith(".png");
    }

    private void playNextAsset(final Stack<Asset> _assets) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playAsset(_assets);
            }
        }, playDuration(_asset));
    }

    private int playDuration(final Asset _asset) {
        if (_asset.duration != null && _asset.duration > 0) {
            return _asset.duration * 1000;
        }
        return mGlobalConfig.getServerDefaultPlayTime() * 1000;
    }

    private void playVideo(final Stack<Asset> _assets) {
        mPlaybackController.playLocalVideo(Constants.PATH + _assets.pop().url);
        if (_assets.pop().duration > 0) {
            playNextAsset(_assets);
        }
    }

    private void playYouTubeVideo(final Stack<Asset> _assets) {
        mPlaybackController.playYoutubeLink(_assets.pop().url);
        if (_assets.pop().duration > 0) {
            playNextAsset(_assets);
        }
    }

    private void playPicture(final Stack<Asset> _assets) {
        mPlaybackController.playLocalPicture(Constants.PATH + _assets.pop().url);
        playNextAsset(_assets);
    }

    private Campaign hasPlayCampaignInCurentTime(final List<Campaign> _campaigns, final PlayModeManager _playModeManager) {
        Date owerrideTime = null;
        Date currentTime = _playModeManager.getCurrentDate();
        long currentTimeInMills = _playModeManager.getTimeInMills(currentTime);
        long owerrideTimeInMills = 0;

        for (Campaign campaign : _campaigns) {
            if (campaign.overrideTime != null) {
                owerrideTime = _playModeManager.getTimeFromString(campaign.overrideTime);
                owerrideTimeInMills = _playModeManager.getTimeInMills(owerrideTime);
            }
            if (owerrideTimeInMills != 0 && owerrideTimeInMills == currentTimeInMills) {
                return campaign;
            } else if (owerrideTimeInMills != 0 && owerrideTimeInMills > currentTimeInMills) {
                startCheckTimeDelay(campaign, owerrideTimeInMills - currentTimeInMills);
                return _playModeManager.getDefaultCampaign(_campaigns);
            }
        }
        return null;
    }

    public void startCheckTimeDelay(final Campaign _campaign, final long _timeDelay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playAsset(_campaign);
            }
        }, _timeDelay);
    }

    private void sortAssets(List<Asset> _assets) {
        Collections.sort(_assets, new Comparator<Asset>() {
            @Override
            public int compare(Asset lhs, Asset rhs) {
                return rhs.sequence.compareTo(lhs.sequence);
            }
        });
    }

    private Stack<Asset> getStackAssets(final List<Asset> _assets) {
        sortAssets(_assets);
        Stack<Asset> stack = new Stack<>();
        for (Asset asset : _assets) {
            stack.push(asset);
        }
        return stack;
    }

}
