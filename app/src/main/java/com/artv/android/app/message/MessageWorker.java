package com.artv.android.app.message;

import android.graphics.Color;

import com.artv.android.core.UrlHelper;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ZOG on 8/21/2015.
 */
public final class MessageWorker {

    private MsgBoardCampaign mMsgBoardCampaign;
    private IMessageController mMessageController;

    private boolean mPlay = false;
    private List<Message> mBottomMessages;
    private List<Message> mRightMessages;

    public final void setMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
        mMsgBoardCampaign = _msgBoardCampaign;
    }

    public final void setMessageController(final IMessageController _controller) {
        mMessageController = _controller;
    }

    public final void playMessages() {
        if (mMsgBoardCampaign == null) return;

        mPlay = true;

        setBackground();
        setTextColor();

        mBottomMessages = getMessagesWithPosition(mMsgBoardCampaign.messages, MessagePosition.BOTTOM);
        mRightMessages = getMessagesWithPosition(mMsgBoardCampaign.messages, MessagePosition.RIGHT);

        sortMessagesBySequence(mBottomMessages);
        sortMessagesBySequence(mRightMessages);

        play();
    }

    public final void stopMessages() {
        mPlay = false;
    }

    private final void setBackground() {
        final String bottomUrl = UrlHelper.buildUrlFrom(mMsgBoardCampaign.bottomBkgURL);
        final String rightUrl = UrlHelper.buildUrlFrom(mMsgBoardCampaign.rightBkgURL);

        if (bottomUrl.isEmpty()) ArTvLogger.printMessage("Bottom bg url empty or malformed");
        else mMessageController.setBottomBg(bottomUrl);

        if (rightUrl.isEmpty()) ArTvLogger.printMessage("Right bg url empty or malformed");
        else mMessageController.setRightBg(rightUrl);
    }

    private final void setTextColor() {
        mMessageController.setTextColor(Color.parseColor(mMsgBoardCampaign.textColor));
    }

    protected final void sortMessagesBySequence(final List<Message> _messages) {
        Collections.sort(_messages, new Comparator<Message>() {
            @Override
            public final int compare(final Message _lhs, final Message _rhs) {
                return _lhs.sequence - _rhs.sequence;
            }
        });
    }

    protected final List<Message> getMessagesWithPosition(final List<Message> _from, final MessagePosition _position) {
        final List<Message> messages = new ArrayList<>();
        for (final Message message : _from) {
            if (message.position() == _position) messages.add(message);
        }
        return messages;
    }

    private final void play() {
    }

}
