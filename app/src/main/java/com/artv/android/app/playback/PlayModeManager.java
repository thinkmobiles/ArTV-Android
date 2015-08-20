package com.artv.android.app.playback;

import android.os.Handler;

import com.artv.android.core.model.Campaign;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by
 * mRogach on 17.08.2015.
 */
public class PlayModeManager {

    private Date getDateFromString(final String _date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        try {
            return dateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getTimeFromString(final String _date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            return dateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getCurrentDate() {
        return new Date();
    }


    private boolean hasPlayCampaignInCurentDate(final List<Campaign> _campaigns) {
        long startDate = 0, endDate = 0, currentDate;
        currentDate = getCurrentDate().getTime();
        if (!_campaigns.isEmpty()) {
            for (Campaign campaign : _campaigns) {
                if (campaign.startDate != null) {
                    startDate = getDateFromString(campaign.startDate).getTime();
                }
                if (campaign.startDate != null) {
                    endDate = getDateFromString(campaign.endDate).getTime();
                }
                if (startDate != 0 && endDate != 0 && startDate <= currentDate && currentDate <= endDate) {
                    return true;
                } else if (startDate != 0 && startDate <= currentDate) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPlayCampaignInCurentDay(final List<Campaign> _campaigns) {
        Day day = getCurrentDay(getCurrentDate().getDay());
        int currentDay = 0;
        if (day != null) {
            currentDay = day.ordinal();
        }
        String playDays;

        for (Campaign campaign : _campaigns) {
            playDays = campaign.playDay;
            for (int i = 0; i < playDays.length(); i++) {
                if (playDays.charAt(currentDay) == '1') {
                    return true;
                }
            }
        }
        return false;
    }

    public Campaign getDefaultCampaign(final List<Campaign> _campaigns) {
        if (!_campaigns.isEmpty()) {
            return Collections.min(_campaigns, new Comparator<Campaign>() {
                @Override
                public int compare(Campaign lhs, Campaign rhs) {
                    return lhs.sequence - rhs.sequence;
                }
            });
        }
        return null;
    }

    public long getTimeInMills(final Date _time) {
        return _time.getHours() * 60 * 60 * 1000 + _time.getMinutes() * 60 * 1000;
    }

    public int campainToPlay(final List<Campaign> _campaigns) {
        if (hasPlayCampaignInCurentDate(_campaigns) && hasPlayCampaignInCurentDay(_campaigns)) {
            return 1;
        }
        return 0;
    }

    private enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    private Day getCurrentDay(final int _currentDay) {

        switch (_currentDay) {
            case 1:
                return Day.MONDAY;
            case 2:
                return Day.TUESDAY;
            case 3:
                return Day.WEDNESDAY;
            case 4:
                return Day.THURSDAY;
            case 5:
                return Day.FRIDAY;
            case 6:
                return Day.SATURDAY;
            case 7:
                return Day.SUNDAY;
        }
        return null;
    }

}
