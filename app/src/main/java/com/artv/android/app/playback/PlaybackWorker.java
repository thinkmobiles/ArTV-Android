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
        mCampaigns = mDbWorker.getAllCampaigns();
        mPlayModeManager = new PlayModeManager();
        mCampaigns = mDbWorker.getAllCampaigns();
        prepareStackToPlay(mPlayModeManager, mCampaigns);
    }

    private void prepareStackToPlay(final PlayModeManager _playModeManager, final List<Campaign> _campaigns) {
        Campaign campaignToPlay;
        switch (_playModeManager.campainToPlay(_campaigns)) {
            case 0:
                mAssetStack = getStackAssetsAllCampaigns(_campaigns);
                break;
            case 1:
                campaignToPlay = hasPlayCampaignInCurentTime(_campaigns, _playModeManager);
                if (campaignToPlay != null) {
                    mAssetStack = getStackAssets(campaignToPlay.assets);
                } else
                    mAssetStack = getStackAssetsAllCampaigns(_campaigns);
                break;
        }
        play();
    }

    public final void stopPlayback() {

    }

    @Override
    public final void onVideoCompleted() {
        play();
    }

    private void play() {
        if (!mAssetStack.isEmpty()) {
            Asset asset = mAssetStack.pop();
            if (isVideoFormat(asset.url)) {
                mPlaybackController.playLocalVideo(Constants.PATH + asset.url);
            } else if (isPictureFormat(asset.url)) {
                playPicture(asset);
            } else if (UrlHelper.isYoutubeUrl(asset.url)) {
                mPlaybackController.playYoutubeLink(asset.url);
            }
        } else {
            prepareStackToPlay(mPlayModeManager, mCampaigns);
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

    private void playNextAsset(final Asset _asset) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                play();
            }
        }, playDuration(_asset));
    }

    private int playDuration(final Asset _asset) {
        if (_asset.duration != null && _asset.duration > 0) {
            return _asset.duration * 1000;
        }
        return mGlobalConfig.getServerDefaultPlayTime() * 1000;
    }

    private void playPicture(final Asset _asset) {
        mPlaybackController.playLocalPicture(Constants.PATH + _asset.url);
        playNextAsset(_asset);
    }

    private Campaign hasPlayCampaignInCurentTime(final List<Campaign> _campaigns, final PlayModeManager _playModeManager) {
        Date owerrideTime;
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
            }
        }
        return null;
    }

    public void startCheckTimeDelay(final Campaign _campaign, final long _timeDelay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAssetStack = getStackAssets(_campaign.assets);
                play();
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

    private void sortCampaigns(List<Campaign> _campaigns) {
        Collections.sort(_campaigns, new Comparator<Campaign>() {
            @Override
            public int compare(Campaign lhs, Campaign rhs) {
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

    private Stack<Asset> getStackAssetsAllCampaigns(List<Campaign> _campaigns) {
        List<Asset> assets = new ArrayList<>();
        sortCampaigns(_campaigns);
        for (Campaign campaign : _campaigns) {
            sortAssets(campaign.assets);
            assets.addAll(campaign.assets);
        }
        return getStackAssets(assets);
    }

}
