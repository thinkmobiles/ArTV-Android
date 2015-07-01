package com.artv.android.system.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.artv.android.R;

/**
 * Created by Misha on 6/30/2015.
 */
public class ConfigsFragment extends Fragment implements View.OnClickListener {
    private EditText mDeviceID, mUserName, mMasterDevIP, mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configs, null);

        mDeviceID = (EditText) view.findViewById(R.id.etDeviceID_FC);
        mUserName = (EditText) view.findViewById(R.id.etUserName_FC);
        mMasterDevIP = (EditText) view.findViewById(R.id.etMasterDeviceIP_FC);
        mPassword = (EditText) view.findViewById(R.id.etPassword_FC);

        view.findViewById(R.id.btnSave_FC).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave_FC:
                //todo saving
                break;
        }
    }
}
