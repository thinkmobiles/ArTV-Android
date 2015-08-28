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

import java.util.Iterator;
import java.util.List;

/**
 * Created by ZOG on 8/21/2015.
 */
public final class MessageWorker {

    private MessageHelper mHelper;

    private MsgBoardCampaign mMsgBoardCampaign;
    private IMessageController mMessageController;
    private GlobalConfig mGlobalConfig;
    private DbWorker mDbWorker;

    private boolean mPlay = false;
    private Iterator<Message> mBottomMsgCycle;
    private Iterator<Message> mRightMsgCycle;

    private Handler mHandler;

    public MessageWorker() {
        mHelper = new MessageHelper();
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

    private final void createHandler() {
        if (mHandler == null) mHandler = new Handler();
    }

    public final void playMessages() {
        mMsgBoardCampaign = mDbWorker.getMsgBoardCampaign();
        if (mMsgBoardCampaign == null || !mHelper.hasPlayDays(mMsgBoardCampaign.playDay)) {
            mMessageController.hideMessageUi();
            return;
        }

        ArTvLogger.printMessage("Started messages");

        createHandler();

        if (!mHelper.shouldPlayMessagesToday(mMsgBoardCampaign.playDay)) {
            mMessageController.hideMessageUi();
            final long ms = mHelper.getRemainMsToBeginPlay(mMsgBoardCampaign.playDay);
            mHandler.postDelayed(playMessagesLater, ms);
            ArTvLogger.printMessage(String.format("Set play messages after %d ms", ms));
            return;
        }

        mMessageController.showMessageUi();
        ArTvLogger.printMessage("Start playing messages now");
        final long ms = mHelper.getMillisToNextDay();
        mHandler.postDelayed(playMessagesLater, ms);
        ArTvLogger.printMessage(String.format("Set to check play messages again after %d ms", ms));

        mPlay = true;
        prepareUiAndMsgCycle();
        playNextMessage();
    }

    public final void stopMessages() {
        ArTvLogger.printMessage("Stopped messages");
        mPlay = false;
        mMessageController.hideMessageUi();
        if (mHandler != null) {
            mHandler.removeCallbacks(nextMsg);
            mHandler.removeCallbacks(playMessagesLater);
        }
    }

    private final void playNextMessage() {
        mMessageController.showBottomMessage(mBottomMsgCycle.next().text);
        mMessageController.showRightMessage(mRightMsgCycle.next().text);

        if (mPlay) mHandler.postDelayed(nextMsg, mGlobalConfig.getServerDefaultPlayTime() * 1000);
    }

    private final Runnable nextMsg = new Runnable() {
        @Override
        public final void run() {
            playNextMessage();
        }
    };

    private final Runnable playMessagesLater = new Runnable() {
        @Override
        public final void run() {
            playMessages();
        }
    };

    private final void prepareUiAndMsgCycle() {
        setBackground();
        setTextColor();

        final List<Message> bottomMessages = mHelper.getMessagesWithPosition(mMsgBoardCampaign.messages, MessagePosition.BOTTOM);
        final List<Message> rightMessages = mHelper.getMessagesWithPosition(mMsgBoardCampaign.messages, MessagePosition.RIGHT);

        mHelper.sortMessagesBySequence(bottomMessages);
        mHelper.sortMessagesBySequence(rightMessages);

        mBottomMsgCycle = Iterables.cycle(bottomMessages).iterator();
        mRightMsgCycle = Iterables.cycle(rightMessages).iterator();
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

}
