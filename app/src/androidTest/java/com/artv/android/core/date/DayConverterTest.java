package com.artv.android.core.date;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

/**
 * Created by ZOG on 8/21/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class DayConverterTest {

    private DayConverter mDayConverter;
    private static final int TOLERANCE = 10;

    @Before
    public final void Init() {
        mDayConverter = new DayConverter();
    }

    @Test
    public final void GetNexPlayingDay() {
        Assert.assertEquals(Day.MONDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("0110000")));
        Assert.assertEquals(Day.MONDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("0100000")));

        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getPlayDay(Day.TUESDAY, mDayConverter.getDays("0001001")));
        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getPlayDay(Day.TUESDAY, mDayConverter.getDays("1101010")));
        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getPlayDay(Day.TUESDAY, mDayConverter.getDays("0101000")));
        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getPlayDay(Day.TUESDAY, mDayConverter.getDays("0001000")));

        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("0110010")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("0000011")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("1110010")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("0100011")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getPlayDay(Day.WEDNESDAY, mDayConverter.getDays("0000010")));
    }

    @Test
    public final void GetMillisToNextDay() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mDayConverter.setCalendar(calendar);
        Assert.assertEquals(mDayConverter.getMillisInDay(),
                mDayConverter.getMillisToNextDay());

        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mDayConverter.setCalendar(calendar);
        Assert.assertEquals(mDayConverter.getMillisInDay() - (18 * 60 + 25) * 60 * 1000,
                mDayConverter.getMillisToNextDay());

        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 32);
        calendar.set(Calendar.SECOND, 16);
        calendar.set(Calendar.MILLISECOND, 589);
        mDayConverter.setCalendar(calendar);
        Assert.assertEquals(mDayConverter.getMillisInDay() - (((14 * 60 + 32) * 60 + 16) * 1000 + 589),
                mDayConverter.getMillisToNextDay());
    }

    @Test
    public final void GetMillisBetweenDays() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        millisBetweenDayWithCalendar(calendar);

        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        millisBetweenDayWithCalendar(calendar);

        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 34);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 435);
        millisBetweenDayWithCalendar(calendar);

        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 32);
        calendar.set(Calendar.SECOND, 16);
        calendar.set(Calendar.MILLISECOND, 589);
        millisBetweenDayWithCalendar(calendar);
    }

    private final void millisBetweenDayWithCalendar(final Calendar _calendar) {
        mDayConverter.setCalendar(_calendar);

        long expectedTime = mDayConverter.getMillisToNextDay() + 3 * mDayConverter.getMillisInDay();
        long actualTime = mDayConverter.getMillisBetweenDays(Day.MONDAY, Day.FRIDAY);
        Assert.assertEquals(expectedTime, actualTime);

        expectedTime = mDayConverter.getMillisToNextDay() + 2 * mDayConverter.getMillisInDay();
        actualTime = mDayConverter.getMillisBetweenDays(Day.FRIDAY, Day.MONDAY);
        Assert.assertEquals(expectedTime, actualTime);

        expectedTime = mDayConverter.getMillisToNextDay() + 5 * mDayConverter.getMillisInDay();
        actualTime = mDayConverter.getMillisBetweenDays(Day.SATURDAY, Day.FRIDAY);
        Assert.assertEquals(expectedTime, actualTime);
    }

}
