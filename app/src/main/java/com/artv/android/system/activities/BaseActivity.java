package com.artv.android.system.activities;

import android.support.v4.app.FragmentActivity;

import com.artv.android.ApplicationLogic;
import com.artv.android.system.ArTvApplication;

/**
 * Created by ZOG on 7/7/2015.
 */
public abstract class BaseActivity extends FragmentActivity {

    public final ArTvApplication getMyApplication() {
        return (ArTvApplication) getApplication();
    }

    public final ApplicationLogic getApplicationLogic() {
        return getMyApplication().getApplicationLogic();
    }

}
