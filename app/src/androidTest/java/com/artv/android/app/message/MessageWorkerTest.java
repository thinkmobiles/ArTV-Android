package com.artv.android.app.message;

import android.support.test.runner.AndroidJUnit4;

import com.artv.android.core.model.Message;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 8/21/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class MessageWorkerTest {

    private MessageWorker mMessageWorker;
    private MessageHelper mMessageHelper;

    @Before
    public final void Init() {
        mMessageWorker = new MessageWorker();
    }

    @Test
    public final void Messages_SortBySequence() {
        final List<Message> messages = buildMessages();
        mMessageHelper.sortMessagesBySequence(messages);
        for (int i = 0; i < messages.size(); i++) {
            Assert.assertEquals(i, messages.get(i).sequence);
        }
    }

    @Test
    public final void Messages_FillBottomAndRightMessages_PositionRight() {
        final List<Message> messages = buildMessages();

        final List<Message> bottomMessages = mMessageHelper.getMessagesWithPosition(messages, MessagePosition.BOTTOM);
        final List<Message> rightMessages = mMessageHelper.getMessagesWithPosition(messages, MessagePosition.RIGHT);

        for (final Message message : bottomMessages) Assert.assertEquals(MessagePosition.BOTTOM, message.position());
        for (final Message message : rightMessages) Assert.assertEquals(MessagePosition.RIGHT, message.position());
    }

    @Test
    public final void Messages_FillBottomAndRightMessages_SequenceIncrementing() {
        final List<Message> messages = buildMessages();

        final List<Message> bottomMessages = mMessageHelper.getMessagesWithPosition(messages, MessagePosition.BOTTOM);
        final List<Message> rightMessages = mMessageHelper.getMessagesWithPosition(messages, MessagePosition.RIGHT);

        mMessageHelper.sortMessagesBySequence(bottomMessages);
        mMessageHelper.sortMessagesBySequence(rightMessages);

        for (int i = 0; i < bottomMessages.size() - 1; i++) {
            Assert.assertTrue(
                    bottomMessages.get(i).sequence < bottomMessages.get(i + 1).sequence
            );
        }

        for (int i = 0; i < rightMessages.size() - 1; i++) {
            Assert.assertTrue(
                    rightMessages.get(i).sequence < rightMessages.get(i + 1).sequence
            );
        }
    }

    private final List<Message> buildMessages() {
        final List<Message> messages = new ArrayList<>();
        messages.add(buildMessage("R", 4));
        messages.add(buildMessage("R", 11));
        messages.add(buildMessage("R", 9));
        messages.add(buildMessage("R", 7));
        messages.add(buildMessage("R", 2));
        messages.add(buildMessage("R", 3));
        messages.add(buildMessage("U", 14));
        messages.add(buildMessage("U", 12));
        messages.add(buildMessage("U", 0));
        messages.add(buildMessage("B", 1));
        messages.add(buildMessage("B", 6));
        messages.add(buildMessage("B", 10));
        messages.add(buildMessage("B", 5));
        messages.add(buildMessage("B", 13));
        messages.add(buildMessage("B", 8));
        return messages;
    }

    private final Message buildMessage(final String _pos, final int _sequence) {
        final Message message = new Message();
        message.sequence = _sequence;
        message.position = _pos;
        message.text = "Position: " + _pos + ", sequence = " + _sequence;
        return message;
    }

}
