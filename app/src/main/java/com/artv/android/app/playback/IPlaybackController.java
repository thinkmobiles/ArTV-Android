package com.artv.android.app.playback;

import com.artv.android.core.model.MsgBoardCampaign;

/**
 * Used to control playback.
 *
 * Created by ZOG on 8/18/2015.
 */
public interface IPlaybackController {

    void playLocalVideo(final String _path);
    void playLocalPicture(final String _path);
    void playYoutubeLink(final String _url);
    void stopPlaying();

}
