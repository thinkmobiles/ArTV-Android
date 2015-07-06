package com.artv.android.system;

import android.app.Application;

import com.artv.android.core.ApplicationLogic;
//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

/**
 * Created by ZOG on 6/30/2015.
 */
public final class MyApplication extends Application {

    private static ApplicationLogic mApplicationLogic;

    @Override
    public final void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());

        mApplicationLogic = new ApplicationLogic(getApplicationContext());
    }

    public static final ApplicationLogic getApplicationLogic() {
        return mApplicationLogic;
    }

}
