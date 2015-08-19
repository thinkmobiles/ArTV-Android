package com.artv.android.app.playback;

import android.os.Handler;

import com.artv.android.core.Constants;
import com.artv.android.core.model.Asset;
import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.GlobalConfig;
import com.artv.android.database.DbWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ZOG on 8/18/2015.
 */
public final class PlaybackWorker implements IVideoCompletionListener {

    private DbWorker mDbWorker;
    private GlobalConfig mGlobalConfig;
    private IPlaybackController mPlaybackController;
    private PlayModeManager mPlayModeManager;
    private List<Campaign> mCampaigns;

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
        mCampaigns = getTestCampaigns();
        mPlayModeManager = new PlayModeManager();
        play(mPlayModeManager, mCampaigns);
    }

    private void play(final PlayModeManager _playModeManager, final List<Campaign> _campaigns) {
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
            playAsset(campaignToPlay);
        } else {
            playAsset(_playModeManager.getDefaultCampaign(_campaigns));
        }
    }

    public final void stopPlayback() {

    }

    @Override
    public final void onVideoCompleted() {
//        playNextAsset(_campaign, asset);
    }

    private void playAsset(final Campaign _campaign) {
        if (_campaign != null && !_campaign.assets.isEmpty()) {
            final Asset asset = getFirstAssetToPlay(_campaign.assets);
            if (isVideoFormat(asset.name)) {
                playVideo(_campaign, asset);
            } else if (isPictureFormat(asset.name)) {
                playPicture(_campaign, asset);
            } else if (isYouTubeVideo(asset.name)) {
                playYouTubeVideo(_campaign, asset);
            }
        }
    }

    private void playAssetDeletePrev(final Campaign _campaign, final Asset _asset) {
        List<Asset> assets = _campaign.assets;
        assets.remove(_asset);
        final Asset asset = getFirstAssetToPlay(assets);
        if (asset != null) {
            if (isVideoFormat(asset.name)) {
                playVideo(_campaign, asset);
            } else if (isPictureFormat(asset.name)) {
                playPicture(_campaign, asset);
            } else if (isYouTubeVideo(asset.name)) {
                playYouTubeVideo(_campaign, asset);
            }
        } else {
            playNextCampaign(mCampaigns, _campaign);
        }
    }

    private void playNextCampaign(final List<Campaign> _campaigns, final Campaign _currentCampaign) {
        _campaigns.remove(_currentCampaign);
        play(mPlayModeManager, _campaigns);
    }

    private Asset getFirstAssetToPlay(final List<Asset> _assets) {
        if (!_assets.isEmpty()) {
            return Collections.min(_assets, new Comparator<Asset>() {
                @Override
                public int compare(Asset lhs, Asset rhs) {
                    return lhs.sequence - rhs.sequence;
                }
            });
        }
        return null;
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

    private boolean isYouTubeVideo(final String _fileName) {
        return _fileName.toLowerCase().contains("www.youtube.com");
    }

    private void playNextAsset(final Campaign _campaign, final Asset _asset) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playAssetDeletePrev(_campaign, _asset);
            }
        }, playDuration(_asset));
    }

    private int playDuration(final Asset _asset) {
        if (_asset.duration > 0) {
            return _asset.duration * 1000;
        }
        return mGlobalConfig.getServerDefaultPlayTime() * 1000;
    }

    private void playVideo(final Campaign _campaign, final Asset _asset) {
        mPlaybackController.playLocalVideo(Constants.PATH + "/" + _asset.url);
        if (_asset.duration > 0) {
            playNextAsset(_campaign, _asset);
        }
    }

    private void playYouTubeVideo(final Campaign _campaign, final Asset _asset) {
        mPlaybackController.playYoutubeLink(_asset.url);
        if (_asset.duration > 0) {
            playNextAsset(_campaign, _asset);
        }
    }

    private void playPicture(final Campaign _campaign, final Asset _asset) {
        mPlaybackController.playLocalPicture(Constants.PATH + "/" + _asset.url);
        playNextAsset(_campaign, _asset);
    }

    private Campaign hasPlayCampaignInCurentTime(final List<Campaign> _campaigns, final PlayModeManager _playModeManager) {
        Date owerrideTime;
        Date currentTime = _playModeManager.getCurrentDate();
        long currentTimeInMills = _playModeManager.getTimeInMills(currentTime);
        long owerrideTimeInMills;

        for (Campaign campaign : _campaigns) {
            owerrideTime = _playModeManager.getTimeFromString(campaign.overrideTime);
            owerrideTimeInMills = _playModeManager.getTimeInMills(owerrideTime);
            if (owerrideTimeInMills == currentTimeInMills) {
                return campaign;
            } else if (owerrideTimeInMills > currentTimeInMills) {
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

    private List<Campaign> getTestCampaigns() {
        List<Campaign> campaigns = new ArrayList<>();
        Campaign campaign1 = new Campaign();
        campaign1.campaignId = 1;
        campaign1.crcVersion = "gfgjfjhg";
        campaign1.startDate = "2015-08-19";
        campaign1.endDate = "2015-09-19";
        campaign1.sequence = 5;
        campaign1.playDay = "1001011";
        campaign1.overrideTime = "17:04 AM";
        campaign1.assets = getFirstAssets();

        Campaign campaign2 = new Campaign();
        campaign2.campaignId = 2;
        campaign2.crcVersion = "gfgvv";
        campaign2.startDate = "2015-08-19";
        campaign2.endDate = "2015-09-19";
        campaign2.sequence = 1;
        campaign2.playDay = "1001011";
        campaign2.overrideTime = "17:05 AM";
        campaign2.assets = getSecondAssets();

        campaigns.add(campaign1);
        campaigns.add(campaign2);
        return campaigns;
    }

    private List<Asset> getFirstAssets() {
        List<Asset> assets = new ArrayList<>();
        Asset asset1 = new Asset();
        asset1.name = "1.mp4";
        asset1.url = "1.mp4";
        asset1.duration = 15;
        asset1.sequence = 3;

        Asset asset2 = new Asset();
        asset2.name = "3.3gp";
        asset2.url = "3.3gp";
        asset2.duration = 10;
        asset2.sequence = 1;

        Asset asset3 = new Asset();
        asset3.name = "5.jpg";
        asset3.url = "5.jpg";
        asset3.sequence = 2;

        assets.add(asset1);
        assets.add(asset2);
        assets.add(asset3);
        return assets;
    }

    private List<Asset> getSecondAssets() {
        List<Asset> assets = new ArrayList<>();
        Asset asset1 = new Asset();
        asset1.name = "2.mp4";
        asset1.url = "2.mp4";
        asset1.duration = 10;
        asset1.sequence = 1;

        Asset asset2 = new Asset();
        asset2.name = "4.3gp";
        asset2.url = "4.3gp";
        asset2.duration = 10;
        asset2.sequence = 2;

        Asset asset3 = new Asset();
        asset3.name = "6.jpg";
        asset3.url = "6.jpg";
        asset3.sequence = 3;
        asset3.duration = 5;

        assets.add(asset1);
        assets.add(asset2);
        assets.add(asset3);
        return assets;
    }
}
