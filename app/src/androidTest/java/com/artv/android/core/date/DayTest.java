package com.artv.android.core.date;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ZOG on 8/25/2015.
 */
@RunWith(AndroidJUnit4.class)
public final class DayTest {

    @Test
    public final void Day_GetNextDay_DayIncrementedAndCyclic() {
        Assert.assertEquals(Day.SATURDAY, Day.FRIDAY.getNext());
        Assert.assertEquals(Day.MONDAY, Day.SUNDAY.getNext());
        Assert.assertEquals(Day.WEDNESDAY, Day.TUESDAY.getNext());
        Assert.assertEquals(Day.SUNDAY, Day.SATURDAY.getNext());

        final List<Day> days = Arrays.asList(Day.values());
        Day day = Day.SUNDAY;
        for (int i = 0; i < 15; i++) {
            Log.d("DayTest", "itetation i: " + i);
            Assert.assertTrue(days.contains(day));
            day = day.getNext();
        }
    }

}
