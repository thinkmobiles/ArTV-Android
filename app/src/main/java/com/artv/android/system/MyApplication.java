package com.artv.android.system;

import android.app.Application;

import com.artv.android.core.ApplicationLogic;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class MyApplication extends Application {

    private static ApplicationLogic mApplicationLogic;

    @Override
    public final void onCreate() {
        super.onCreate();

        mApplicationLogic = new ApplicationLogic(getApplicationContext());
    }

    public static final ApplicationLogic getApplicationLogic() {
        return mApplicationLogic;
    }

}
