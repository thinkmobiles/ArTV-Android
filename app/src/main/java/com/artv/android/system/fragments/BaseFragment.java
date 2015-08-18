package com.artv.android.system.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.artv.android.ApplicationLogic;
import com.artv.android.system.BaseActivity;

/**
 * Created by ZOG on 7/7/2015.
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    private BaseActivity mBaseActivity;

    @Override
    public void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mBaseActivity = ((BaseActivity) getActivity());
    }

    public final ApplicationLogic getApplicationLogic() {
        return mBaseActivity.getApplicationLogic();
    }

}
