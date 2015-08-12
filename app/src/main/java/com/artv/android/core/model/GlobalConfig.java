package com.artv.android.core.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by ZOG on 8/12/2015.
 */
@Root(name = "GlobalConfigXML")
public final class GlobalConfig {

    public static final int DEF_PLAY_TIME = 5;

    public static final String KEY_DEF_PLAY_TIME = "DefaultPlayTime";

    @ElementList(name = "Entry", inline = true)
    public ArrayList<Entry> entries;

    /**
     * Returns default time to play.
     * @return time in seconds.
     */
    public final int getServerDefaultPlayTime() {
        for (final Entry entry : entries) {
            if (KEY_DEF_PLAY_TIME.equals(entry.name)) {
                try {
                    return Integer.parseInt(entry.value);
                } catch (final NumberFormatException _e) {
                    _e.printStackTrace();
                    break;
                }
            }
        }

        return DEF_PLAY_TIME;
    }

}
