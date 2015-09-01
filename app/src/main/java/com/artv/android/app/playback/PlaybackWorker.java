package com.artv.android.app.playback;

import android.content.Context;
import android.os.Handler;

import com.artv.android.core.Constants;
import com.artv.android.core.UrlHelper;
import com.artv.android.core.date.DayConverter;
import com.artv.android.core.display.AlarmAlertWakeLock;
import com.artv.android.core.display.DeviceAdministrator;
import com.artv.android.core.display.TvStatus;
import com.artv.android.core.log.ArTvLogger;
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

    private Context mContext;
    private DbWorker mDbWorker;
    private GlobalConfig mGlobalConfig;
    private IPlaybackController mPlaybackController;
    private PlayModeManager mPlayModeManager;
    private List<Campaign> mCampaigns;
    private Stack<Asset> mAssetStack;
    private int mCurrentCampaignId;
    private int mCurrentAssetPlayingId;
    private TvStatus mTvStatus;
    private Handler mHandlerPostPlay;
    private Runnable mRunnableThread;

    public final void setPlaybackController(final IPlaybackController _controller) {
        mPlaybackController = _controller;
    }

    public final void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void setGlobalConfig(final GlobalConfig _globalConfig) {
        mGlobalConfig = _globalConfig;
    }

    public void setPlayModeManager(PlayModeManager mPlayModeManager) {
        this.mPlayModeManager = mPlayModeManager;
    }

    public void setTvStatus(TvStatus _tvStatus) {
        this.mTvStatus = _tvStatus;
    }

    public final IVideoCompletionListener getVideoCompletionListener() {
        return this;
    }

    public int getCurrentCampaignId() {
        return mCurrentCampaignId;
    }

    public int getCurrentAssetPlayingId() {
        return mCurrentAssetPlayingId;
    }

    public void setContext(final Context _context) {
        this.mContext = _context;
    }

    public final void startPlayback() {
        ArTvLogger.printMessage("Started playback");
        mCampaigns = mDbWorker.getAllCampaigns();
        mPlayModeManager.setDayConverter(new DayConverter());
        mCampaigns = mDbWorker.getAllCampaigns();
        checkCampaigns(mPlayModeManager, mCampaigns);
    }

    private void checkCampaigns(final PlayModeManager _playModeManager, final List<Campaign> _campaigns) {
        final List<Campaign> campaignsPlayToday = _playModeManager.campainsToPlayToday(_campaigns);
        if (getCampaignsWithOutOwerrideTime(campaignsPlayToday).isEmpty()) {
            turnOff();
        } else {
            prepareStackToPlay(_playModeManager, campaignsPlayToday);
            play();
        }
    }

    private void prepareStackToPlay(final PlayModeManager _playModeManager, final List<Campaign> _campaignsPlayToday) {
            hasPlayCampaignInCurrentTime(_campaignsPlayToday, _playModeManager);
            mAssetStack = getStackAssetsAllCampaigns(_campaignsPlayToday);
    }

    private List<Campaign> getCampaignsWithOutOwerrideTime(final List<Campaign> _campaigns) {
        for (Campaign campaign : _campaigns) {
            if (!campaign.overrideTime.isEmpty()) {
                _campaigns.remove(campaign);
            }
        }
        return _campaigns;
    }

    private void turnOff() {
        setTvStatusOff(true);
        DeviceAdministrator.lockScreen(mContext);
    }

    public final void stopPlayback() {
        mCurrentCampaignId = 0;
        mCurrentAssetPlayingId = 0;
        if (mHandlerPostPlay != null && mRunnableThread != null) {
            mHandlerPostPlay.removeCallbacks(mRunnableThread);
        }
        mPlaybackController.stopPlaying();
        mCampaigns.clear();
        if (mAssetStack != null) {
            mAssetStack.clear();
        }
        ArTvLogger.printMessage("Stopped playback");
    }

    @Override
    public final void onVideoCompleted() {
        play();
    }

    private void play() {
        if (!mAssetStack.isEmpty()) {
            Asset asset = mAssetStack.pop();
            mCurrentAssetPlayingId = asset.getAssetId();
            if (isVideoFormat(asset.url)) {
                mPlaybackController.playLocalVideo(Constants.PATH + asset.url);
            } else if (isPictureFormat(asset.url)) {
                playPicture(asset);
            } else if (UrlHelper.isYoutubeUrl(asset.url)) {
                mPlaybackController.playYoutubeLink(asset.url);
            }
        } else {
            checkCampaigns(mPlayModeManager, mCampaigns);
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

    private void hasPlayCampaignInCurrentTime(final List<Campaign> _campaigns, final PlayModeManager _playModeManager) {
        Date owerrideTime;
        Date currentTime = _playModeManager.getCurrentDate();
        long currentTimeInMills = _playModeManager.getTimeInMills(currentTime);
        long owerrideTimeInMills;

        for (Campaign campaign : _campaigns) {
            if (campaign.overrideTime != null) {
                owerrideTime = _playModeManager.getTimeFromString(campaign.overrideTime);
                owerrideTimeInMills = _playModeManager.getTimeInMills(owerrideTime);
                if (owerrideTimeInMills != 0 && owerrideTimeInMills > currentTimeInMills) {
                    startCheckTimeDelay(campaign, owerrideTimeInMills - currentTimeInMills);
                }
            }
        }
    }

    public void startCheckTimeDelay(final Campaign _campaign, final long _timeDelay) {
        final List<Asset> assets = _campaign.assets;
        if (!assets.isEmpty()) {
            sortAssets(assets);
        }
        mHandlerPostPlay = new Handler();
        mRunnableThread = new Runnable() {
            @Override
            public void run() {
                if (mTvStatus.isTurnStatusOff()) {
                    mTvStatus.setTurnStatusOff(false);
                    AlarmAlertWakeLock.acquire(mContext);
                }
                mAssetStack = getStackAssets(assets);
                mPlaybackController.stopPlaying();
                play();
            }
        };
        mHandlerPostPlay.postDelayed(mRunnableThread, _timeDelay);
    }

    public void sortAssets(List<Asset> _assets) {
        if (!_assets.isEmpty()) {
            Collections.sort(_assets, new Comparator<Asset>() {
                @Override
                public int compare(Asset lhs, Asset rhs) {
                    return rhs.sequence.compareTo(lhs.sequence);
                }
            });
        }
    }

    public void sortCampaigns(List<Campaign> _campaigns) {
        if (!_campaigns.isEmpty()) {
            Collections.sort(_campaigns, new Comparator<Campaign>() {
                @Override
                public int compare(Campaign lhs, Campaign rhs) {
                    return rhs.sequence.compareTo(lhs.sequence);
                }
            });
        }
    }

    private Stack<Asset> getStackAssets(final List<Asset> _assets) {
        if (!_assets.isEmpty()) {
            Stack<Asset> stack = new Stack<>();
            for (Asset asset : _assets) {
                stack.push(asset);
            }
            return stack;
        }
        return null;
    }

    public Stack<Asset> getStackAssetsAllCampaigns(List<Campaign> _campaigns) {
        if (!_campaigns.isEmpty()) {
            List<Asset> assets = new ArrayList<>();
            sortCampaigns(_campaigns);
            for (Campaign campaign : _campaigns) {
                sortAssets(campaign.assets);
                assets.addAll(campaign.assets);
            }
            return getStackAssets(assets);
        }
        return null;
    }

    private void setTvStatusOff(final boolean _off) {
        mTvStatus.setTurnStatusOff(_off);
    }

}
