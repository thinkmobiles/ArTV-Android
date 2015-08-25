package com.artv.android.core.date;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Created by ZOG on 8/21/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class DayConverterTest {

    private DayConverter mDayConverter;

    @Before
    public final void Init() {
        mDayConverter = new DayConverter();
    }

    @Test
    public final void GetNexPlayingDay() {
        Assert.assertEquals(Day.MONDAY,     mDayConverter.getNextPlayingDay(Day.WEDNESDAY,  mDayConverter.getDays("0110000")));

        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getNextPlayingDay(Day.TUESDAY,    mDayConverter.getDays("0001001")));
        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getNextPlayingDay(Day.TUESDAY,    mDayConverter.getDays("1101010")));
        Assert.assertEquals(Day.WEDNESDAY,  mDayConverter.getNextPlayingDay(Day.TUESDAY,    mDayConverter.getDays("0101000")));

        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getNextPlayingDay(Day.WEDNESDAY,  mDayConverter.getDays("0110010")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getNextPlayingDay(Day.WEDNESDAY,  mDayConverter.getDays("0000011")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getNextPlayingDay(Day.WEDNESDAY,  mDayConverter.getDays("1110010")));
        Assert.assertEquals(Day.FRIDAY,     mDayConverter.getNextPlayingDay(Day.WEDNESDAY,  mDayConverter.getDays("0100011")));
    }

    @Test
    public final void GetMillisToNextDay() {
        final long millis = mDayConverter.getMillisToNextDay();

        //+2 hours due to UTC+2
        final long remainMillisInDay =
                (System.currentTimeMillis() % mDayConverter.getMillisInDay()) +
                        2 * mDayConverter.getMillisInHour();
        final long millisToNextDay = mDayConverter.getMillisInDay() - remainMillisInDay;
        Assert.assertTrue();
    }

}
