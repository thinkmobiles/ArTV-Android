package com.artv.android.system.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.artv.android.R;

/**
 * Created by Misha on 6/30/2015.
 */
public class SplashScreenFragment extends Fragment {
    private ProgressBar mLoadingProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, null);

        mLoadingProgressBar = (ProgressBar) view.findViewById(R.id.pbLoading_FSS);

        return view;
    }
}
