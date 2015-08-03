package com.artv.android.system.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.artv.android.core.ApplicationLogic;
import com.artv.android.system.BaseActivity;
import com.artv.android.system.MyApplication;

/**
 * Created by ZOG on 7/7/2015.
 */
public abstract class BaseFragment extends Fragment {

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
