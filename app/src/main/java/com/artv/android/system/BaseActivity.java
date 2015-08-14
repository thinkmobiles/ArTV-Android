package com.artv.android.system;

import android.app.Activity;

import com.artv.android.ApplicationLogic;

/**
 * Created by ZOG on 7/7/2015.
 */
public abstract class BaseActivity extends Activity {

    public final ArTvApplication getMyApplication() {
        return (ArTvApplication) getApplication();
    }

    public final ApplicationLogic getApplicationLogic() {
        return getMyApplication().getApplicationLogic();
    }

}
