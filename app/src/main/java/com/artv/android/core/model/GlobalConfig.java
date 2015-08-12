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
     * Replace "" with single ".
     * ""key"" -> "key".
     */
    public final void prepareEntries() {
        for (final Entry entry : entries) {
            entry.setName(entry.getName().replaceAll("\"", ""));
            entry.setValue(entry.getValue().replaceAll("\"", ""));
        }
    }

    /**
     * Returns default time to play.
     * @return time in seconds.
     */
    public final int getServerDefaultPlayTime() {
        for (final Entry entry : entries) {
            if (KEY_DEF_PLAY_TIME.equals(entry.getName())) {
                int time;
                try {
                    time = Integer.parseInt(entry.getValue());
                } catch (final NumberFormatException _e) {
                    _e.printStackTrace();
                    continue;
                }
                return time;
            }
        }

        return DEF_PLAY_TIME;
    }

}
