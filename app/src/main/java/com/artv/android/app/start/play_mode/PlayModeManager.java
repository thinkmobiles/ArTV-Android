package com.artv.android.app.start.play_mode;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date getTimeFromString(final String _date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            return dateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date getCurrentDate() {
        return new Date();
    }


    private boolean hasPlayCampaignInCurentDate(final List<Campaign> _campaigns) {
        long startDate, endDate, currentDate;
        currentDate = getCurrentDate().getTime();
        for (Campaign campaign : _campaigns) {
            startDate = getDateFromString(campaign.startDate).getTime();
            endDate = getDateFromString(campaign.endDate).getTime();
            if (startDate <= currentDate && currentDate <= endDate) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPlayCampaignInCurentDay(final List<Campaign> _campaigns) {
        int currentDay = getCurrentDate().getDay();
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

    private Campaign hasPlayCampaignInCurentTime(final List<Campaign> _campaigns) {
        Date owerrideTime;
        Date currentTime = getCurrentDate();
        long currentTimeInMills = getTimeInMills(currentTime);
        long owerrideTimeInMills;

        for (Campaign campaign : _campaigns) {
            owerrideTime = getTimeFromString(campaign.overrideTime);
            owerrideTimeInMills = getTimeInMills(owerrideTime);
            if (owerrideTimeInMills == currentTimeInMills) {
                return campaign;
            } else if (owerrideTimeInMills > currentTimeInMills) {
                startCheckTimeDelay(_campaigns, owerrideTimeInMills - currentTimeInMills);
                return getDefaultCampaign(_campaigns);
            }
        }
        return null;
    }

    private Campaign getDefaultCampaign(final List<Campaign> _campaigns) {
        return Collections.min(_campaigns, new Comparator<Campaign>() {
            @Override
            public int compare(Campaign lhs, Campaign rhs) {
                return lhs.sequence - rhs.sequence;
            }
        });
    }

    private void startCheckTimeDelay(final List<Campaign> _campaigns, final long _timeDelay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hasPlayCampaignInCurentTime(_campaigns);
            }
        }, _timeDelay);
    }

    private long getTimeInMills(final Date _time) {
        return _time.getHours() * 60 * 60 * 1000 + _time.getMinutes() * 60 * 1000;
    }

    public Campaign getCampainToPlay(final List<Campaign> _campaigns) {
        if (hasPlayCampaignInCurentDate(_campaigns) && hasPlayCampaignInCurentDay(_campaigns)) {
            return hasPlayCampaignInCurentTime(_campaigns);
        }
        return getDefaultCampaign(_campaigns);
    }

}
