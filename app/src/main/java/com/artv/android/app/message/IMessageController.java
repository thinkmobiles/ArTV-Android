package com.artv.android.app.message;

/**
 * Created by ZOG on 8/21/2015.
 */
public interface IMessageController {

    void showMessageUi();
    void hideMessageUi();
    void setBottomBg(final String _url);
    void setRightBg(final String _url);
    void setTextColor(final int _color);
    void showRightMessage(final String _message);
    void showBottomMessage(final String _message);

}
