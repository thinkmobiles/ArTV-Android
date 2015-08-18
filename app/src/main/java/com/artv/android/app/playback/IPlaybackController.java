package com.artv.android.app.playback;

/**
 * Created by ZOG on 8/18/2015.
 */
public interface IPlaybackController {

    void playLocalVideo(final String _path);
    void playLocalPicture(final String _path);
    void playYoutubeLink(final String _url);

}
