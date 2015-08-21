package com.artv.android.app.message;

import android.graphics.Color;
import android.os.Handler;

import com.artv.android.core.UrlHelper;
import com.artv.android.core.log.ArTvLogger;
import com.artv.android.core.model.GlobalConfig;
import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;
import com.artv.android.database.DbWorker;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ZOG on 8/21/2015.
 */
public final class MessageWorker {

    private MsgBoardCampaign mMsgBoardCampaign;
    private IMessageController mMessageController;
    private GlobalConfig mGlobalConfig;
    private DbWorker mDbWorker;

    private boolean mPlay = false;
    private Iterator<Message> mBottomMsgCycle;
    private Iterator<Message> mRightMsgCycle;

    private Handler mHandler;

    public MessageWorker() {
    }

    public final void setMessageController(final IMessageController _controller) {
        mMessageController = _controller;
    }

    public final void setGlobalConfig(final GlobalConfig _globalConfig) {
        mGlobalConfig = _globalConfig;
    }

    public final void setDbWorker(final DbWorker _dbWorker) {
        mDbWorker = _dbWorker;
    }

    public final void playMessages() {
        mMsgBoardCampaign = mDbWorker.getMsgBoardCampaign();
        if (mMsgBoardCampaign == null) return;

        mMessageController.showMessageUi();

        mPlay = true;

        setBackground();
        setTextColor();

        final List<Message> bottomMessages = getMessagesWithPosition(mMsgBoardCampaign.messages, MessagePosition.BOTTOM);
        final List<Message> rightMessages = getMessagesWithPosition(mMsgBoardCampaign.messages, MessagePosition.RIGHT);

        sortMessagesBySequence(bottomMessages);
        sortMessagesBySequence(rightMessages);

        mBottomMsgCycle = Iterables.cycle(bottomMessages).iterator();
        mRightMsgCycle = Iterables.cycle(rightMessages).iterator();

        mHandler = new Handler();

        play();
    }

    public final void stopMessages() {
        mPlay = false;
        if (mHandler != null) mHandler.removeCallbacks(nextMsg);
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
        mMessageController.showBottomMessage(mBottomMsgCycle.next().text);
        mMessageController.showRightMessage(mRightMsgCycle.next().text);

        if (mPlay) mHandler.postDelayed(nextMsg, mGlobalConfig.getServerDefaultPlayTime() * 1000);
    }

    private final Runnable nextMsg = new Runnable() {
        @Override
        public final void run() {
            play();
        }
    };

}
