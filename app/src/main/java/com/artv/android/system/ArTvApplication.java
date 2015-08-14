package com.artv.android.system;

import android.app.Application;

import com.artv.android.ApplicationLogic;
//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class ArTvApplication extends Application {

    private ApplicationLogic mApplicationLogic;

    @Override
    public final void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
    }

    /**
     * Calls by starting activity.
     */
    public final void createApplicationLogic() {
        mApplicationLogic = new ApplicationLogic(getApplicationContext());
    }

    public final ApplicationLogic getApplicationLogic() {
        return mApplicationLogic;
    }

}
