package com.artv.android.system;

import android.app.Activity;

/**
 * Created by ZOG on 7/7/2015.
 */
public abstract class BaseActivity extends Activity {

    public final MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

}
