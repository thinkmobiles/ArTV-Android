package com.artv.android.app.message;

import com.artv.android.core.date.Day;
import com.artv.android.core.date.DayConverter;
import com.artv.android.core.model.Message;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ZOG on 8/26/2015.
 */
final class MessageHelper {

    private DayConverter mDayConverter;

    public MessageHelper() {
        mDayConverter = new DayConverter();
    }

    protected final boolean shouldPlayMessagesToday(final String _playDays) {
        final Day currentDay = mDayConverter.getCurrentDay();
        final List<Day> playDays = mDayConverter.getDays(_playDays);
        return playDays.contains(currentDay);
    }

    /**
     *
     * @param _playDays must contains at least one day.
     * @return
     */
    protected final long getRemainMsToBeginPlay(final String _playDays) {
        final Day currentDay = mDayConverter.getCurrentDay();
        final List<Day> playDays = mDayConverter.getDays(_playDays);
        Assert.assertFalse(playDays.isEmpty());

        final Day playDay = mDayConverter.getPlayDay(currentDay, playDays);
        final long millis = mDayConverter.getMillisBetweenDays(currentDay, playDay);

        return millis;
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

    public final long getMillisToNextDay() {
        return mDayConverter.getMillisToNextDay();
    }

    public final boolean hasPlayDays(final String _playDays) {
        return !(_playDays == null || _playDays.isEmpty() || mDayConverter.getDays(_playDays).isEmpty());
    }

}
