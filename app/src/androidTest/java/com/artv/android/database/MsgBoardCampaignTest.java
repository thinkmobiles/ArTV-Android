package com.artv.android.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Message;
import com.artv.android.core.model.MsgBoardCampaign;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.artv.android.database.DbTestHelper.buildMessage;
import static com.artv.android.database.DbTestHelper.buildMsgBoardCampaign1;
import static com.artv.android.database.DbTestHelper.buildMsgBoardCampaign2;

/**
 * Created by ZOG on 8/18/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class MsgBoardCampaignTest {

    private DbManager dbManager;

    @Before
    public void initializeDBManager() {
        dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void dropDB() {
        dbManager.dropDatabase();
    }

    @Test
    public final void WriteMsgBoardCampaign_MsgBoardCampaignWrote_DatabaseContainsMsgBoardCampaign() {
        final MsgBoardCampaign msg1 = buildMsgBoardCampaign1();
        final MsgBoardCampaign msg2 = buildMsgBoardCampaign2();

        Assert.assertFalse(-1 == dbManager.write(msg1));
        Assert.assertFalse(-1 == dbManager.write(msg2));
    }

    @Test
    public final void WriteMsgBoardCampaign_WriteSameMsgBoardCampaignAgain_MsgBoardCampaignReplaced() {
        final MsgBoardCampaign msg1 = buildMsgBoardCampaign1();
        final MsgBoardCampaign msg2 = buildMsgBoardCampaign2();

        Assert.assertTrue(dbManager.write(msg1) == dbManager.write(msg1));
        Assert.assertTrue(dbManager.write(msg2) == dbManager.write(msg2));
    }

    @Test
    public final void WriteNull_NotWrote() {
        Assert.assertEquals(dbManager.write((MsgBoardCampaign) null), -1);
    }

    @Test
    public final void WriteMsgBoardCampaign_OnlyLastMsgBoardCampaignStored() {
        final MsgBoardCampaign msg1 = buildMsgBoardCampaign1();
        final MsgBoardCampaign msg2 = buildMsgBoardCampaign2();

        dbManager.write(msg1);
        Assert.assertEquals(dbManager.getMsgBoardCampaign(), msg1);
        dbManager.write(msg2);
        Assert.assertEquals(dbManager.getMsgBoardCampaign(), msg2);
    }

    @Test
    public final void NoMsgBoardCampaign_GetMsgBoardCampaign_ReturnsNull() {
        Assert.assertNull(dbManager.getMsgBoardCampaign());
    }

    @Test
    public final void WriteMsgBoardCampaign_GetMsgBoardCampaign_MsgBoardCampaignsMatch() {
        final MsgBoardCampaign msgBoardCampaign = buildMsgBoardCampaign1();
        dbManager.write(msgBoardCampaign);
        final MsgBoardCampaign msgBoardCampaignLoaded = dbManager.getMsgBoardCampaign();
        Assert.assertEquals(msgBoardCampaign, msgBoardCampaignLoaded);
    }

    @Test
    public final void WriteMsgBoardCampaignWithMessages_GetMsgBoardCampaign_MessagesMatch() {
        final List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(buildMessage(i));
        }
        final MsgBoardCampaign msgBoardCampaign = buildMsgBoardCampaign1();
        msgBoardCampaign.messages = messages;
        dbManager.write(msgBoardCampaign);

        final MsgBoardCampaign msgBoardCampaignLoaded = dbManager.getMsgBoardCampaign();
        Assert.assertEquals(messages.size(), msgBoardCampaignLoaded.messages.size());

        for (final Message message : messages) msgBoardCampaignLoaded.messages.contains(message);
    }

    @Test
    public final void WriteMsgBoardCampaignWithMessages_WriteWithMoreOrLessMessages_MessagesMatch() {
        final List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(buildMessage(i));
        }
        final MsgBoardCampaign msgBoardCampaign = buildMsgBoardCampaign1();
        msgBoardCampaign.messages = messages;
        dbManager.write(msgBoardCampaign);

        messages.add(buildMessage(6));
        dbManager.write(msgBoardCampaign);

        MsgBoardCampaign msgBoardCampaignLoaded = dbManager.getMsgBoardCampaign();
        Assert.assertEquals(messages.size(), msgBoardCampaignLoaded.messages.size());
        for (final Message message : messages) msgBoardCampaignLoaded.messages.contains(message);

        for (int i = 7; i < 11; i++) {
            messages.add(buildMessage(i));
        }
        dbManager.write(msgBoardCampaign);

        msgBoardCampaignLoaded = dbManager.getMsgBoardCampaign();
        Assert.assertEquals(messages.size(), msgBoardCampaignLoaded.messages.size());
        for (final Message message : messages) msgBoardCampaignLoaded.messages.contains(message);

        for (int i = 0; i < 5; i++) {
            messages.remove(messages.get(i));
        }
        dbManager.write(msgBoardCampaign);

        msgBoardCampaignLoaded = dbManager.getMsgBoardCampaign();
        Assert.assertEquals(messages.size(), msgBoardCampaignLoaded.messages.size());
        for (final Message message : messages) msgBoardCampaignLoaded.messages.contains(message);
    }

    @Test
    public final void WriteMsgBoardCampaignWithMoreOrLessMessages_OnlyWroteMessagesSaved() {
        final List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(buildMessage(i));
        }
        final MsgBoardCampaign msgBoardCampaign = buildMsgBoardCampaign1();
        msgBoardCampaign.messages = messages;
        dbManager.write(msgBoardCampaign);

        Assert.assertEquals(dbManager.getAllMessages().size(), 5);

        messages.add(buildMessage(6));
        dbManager.write(msgBoardCampaign);

        Assert.assertEquals(dbManager.getAllMessages().size(), 6);

        messages.remove(messages.get(0));
        dbManager.write(msgBoardCampaign);

        Assert.assertEquals(dbManager.getAllMessages().size(), 5);
    }

    @Test
    public final void WriteMsgBoardCampaign_WriteNull_HasNoCampaign() {
        final List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(buildMessage(i));
        }
        final MsgBoardCampaign msgBoardCampaign = buildMsgBoardCampaign1();
        msgBoardCampaign.messages = messages;
        dbManager.write(msgBoardCampaign);

        dbManager.write((MsgBoardCampaign) null);

        Assert.assertNull(dbManager.getMsgBoardCampaign());
    }

}
