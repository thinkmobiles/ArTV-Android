package com.artv.android.core.date;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public final void GetClosestDay() {
        final Day sunday = mDayConverter.getDayOfWeek(0);
        final Day wednesday = mDayConverter.getDayOfWeek(3);
        final Day friday = mDayConverter.getDayOfWeek(5);

        Assert.assertEquals(Day.MONDAY, mDayConverter.getClosestDayFrom(sunday, mDayConverter.getDays("0100000")));
        Assert.assertEquals(Day.WEDNESDAY, mDayConverter.getClosestDayFrom(sunday, mDayConverter.getDays("0001000")));
        Assert.assertEquals(Day.SATURDAY, mDayConverter.getClosestDayFrom(sunday, mDayConverter.getDays("0000001")));
        Assert.assertEquals(Day.SATURDAY, mDayConverter.getClosestDayFrom(sunday, mDayConverter.getDays("1000001")));

        Assert.assertEquals(Day.MONDAY, mDayConverter.getClosestDayFrom(wednesday, mDayConverter.getDays("0100000")));
        Assert.assertEquals(Day.FRIDAY, mDayConverter.getClosestDayFrom(wednesday, mDayConverter.getDays("0101010")));
        Assert.assertEquals(Day.SATURDAY, mDayConverter.getClosestDayFrom(wednesday, mDayConverter.getDays("1111001")));

        Assert.assertEquals(Day.MONDAY, mDayConverter.getClosestDayFrom(friday, mDayConverter.getDays("0100000")));
        Assert.assertEquals(Day.MONDAY, mDayConverter.getClosestDayFrom(friday, mDayConverter.getDays("0101010")));
        Assert.assertEquals(Day.SATURDAY, mDayConverter.getClosestDayFrom(friday, mDayConverter.getDays("1111001")));
    }

}
