package com.artv.android.system.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.artv.android.R;
import com.artv.android.core.api.ApiConst;
import com.artv.android.core.UrlHelper;
import com.artv.android.core.config_info.ConfigInfo;

/**
 * Created by Misha on 6/30/2015.
 */
public final class ConfigInfoFragment extends BaseFragment implements View.OnClickListener {

    private EditText etDeviceId;
    private EditText etMasterDeviceIp;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etAddress;

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_config_info, _container, false);

        etDeviceId = (EditText) view.findViewById(R.id.etDeviceId_FCI);
        etMasterDeviceIp = (EditText) view.findViewById(R.id.etMasterDeviceIp_FCI);
        etUserName = (EditText) view.findViewById(R.id.etUserName_FCI);
        etPassword = (EditText) view.findViewById(R.id.etPassword_FCI);
        etAddress = (EditText) view.findViewById(R.id.etAddress_FCI);

        view.findViewById(R.id.btnSave_FCI).setOnClickListener(this);

        return view;
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.btnSave_FCI:
                onClickBtnSave();
                break;
        }
    }

    private final void onClickBtnSave() {
        saveState();
    }

    private final void saveState() {
        final String deviceId = etDeviceId.getText().toString();
        final String masterDeviceIp = etMasterDeviceIp.getText().toString();
        final String userName = etUserName.getText().toString();
        final String password = etPassword.getText().toString();
        final String address = etAddress.getText().toString();

        final ConfigInfo configInfo = new ConfigInfo.Builder()
                .setDeviceId(deviceId)
                .setMasterDeviceIp(masterDeviceIp)
                .setUser(userName)
                .setPassword(password)
                .build();

        if (!configInfo.hasConfigInfo()) {
            Toast.makeText(getActivity().getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!address.isEmpty()) {       //user want another server
            if (!UrlHelper.isValidAddress(address)) {
                Toast.makeText(getActivity().getApplicationContext(), "Bad address, enter like \n\"http://site.com\"", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ApiConst.setProtocol(UrlHelper.getProtocolFrom(address));
                ApiConst.setAuthority(UrlHelper.getAuthorityFrom(address));
                ApiConst.addressUpdated();
            }
        }

        getApplicationLogic().getConfigInfoWorker().notifyEnteredConfigInfo(configInfo);
    }
}
